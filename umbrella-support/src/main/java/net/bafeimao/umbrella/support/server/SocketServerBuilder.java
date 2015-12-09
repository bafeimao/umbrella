package net.bafeimao.umbrella.support.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.bafeimao.umbrella.support.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.network.netty.codec.ProtobufEncoder;
import net.bafeimao.umbrella.support.network.netty.handler.DefaultServerHandler;
import net.bafeimao.umbrella.support.network.netty.handler.ProtocolStatsHandler;
import net.bafeimao.umbrella.support.server.message.*;
import net.bafeimao.umbrella.support.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ktgu on 15/11/28.
 */
public class SocketServerBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServerBuilder.class);

    private String host = "localhost";
    private int port;
    private String handlerPackageScan;
    private int bossEventLoopThreads = 1;
    private int workerEventLoopThreads = 0;
    private LogLevel logLevel = LogLevel.DEBUG;

    private SocketServerBuilder() {}

    public static SocketServerBuilder newBuilder() {
        return new SocketServerBuilder();
    }

    public SocketServerBuilder forPort(int port) {
        return forAddress("localhost", port);
    }

    public SocketServerBuilder forAddress(String host, int port) {
        this.host = host;
        this.port = port;
        return this;
    }

    public SocketServerBuilder handlerPackageScan(String handlerPackageScan) {
        this.handlerPackageScan = handlerPackageScan;
        return this;
    }

    public SocketServerBuilder setEventLoopThreads(int bossEventLoopThreads, int workerEventLoopThreads) {
        this.bossEventLoopThreads = bossEventLoopThreads;
        this.workerEventLoopThreads = workerEventLoopThreads;
        return this;
    }

    public SocketServerBuilder setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public SocketServer build() {
        SocketServer server = new SocketServer(host, port);

        EventLoopGroup bossGroup = new NioEventLoopGroup(bossEventLoopThreads);
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerEventLoopThreads);
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(logLevel))
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();

                        p.addLast(new ProtobufVarint32FrameDecoder());
                        p.addLast(new ProtobufDecoder(Packet.getDefaultInstance()));

                        p.addLast(new ProtobufVarint32LengthFieldPrepender());
                        p.addLast(new ProtobufEncoder());

                        p.addLast(new ProtocolStatsHandler());
                        p.addLast(getMessageDispatchHandler());
                    }
                });

        server.setBootstrap(b);

        return server;
    }

    private ChannelHandler getMessageDispatchHandler() {
        // 该对象用于分发消息到具体的handler
        MessageDispatcher dispatcher = new PacketMessageDispatcher();

        // 注册支持的MessageHandlers
        Map<Object, MessageHandler<?>> handlersMap = getMessageHandlersMap(handlerPackageScan);
        for (Map.Entry<Object, MessageHandler<?>> entry : handlersMap.entrySet()) {
            LOGGER.trace("Registering handler: {} --> {}", entry.getKey(), entry.getValue());
            dispatcher.registerHandler(entry.getKey(), entry.getValue());
        }

        return new DefaultServerHandler(dispatcher);
    }

    private final Map<Object, MessageHandler<?>> getMessageHandlersMap(String handlerPackageScan) {
        if (handlerPackageScan == null) {
            throw new NullPointerException("handlerPackageScan");
        }

        Map<Object, MessageHandler<?>> handlersMap = new HashMap<Object, MessageHandler<?>>();

        Set<Class<?>> candidates = ClassUtils.getClasses(handlerPackageScan);
        for (Class<?> clazz : candidates) {
            Object delegate = null;
            try {
                int mod = clazz.getModifiers();
                if (Modifier.isPublic(mod) && !Modifier.isInterface(mod) && !Modifier.isAbstract(mod)) {
                    Constructor<?> constructor = null;
                    try {
                        constructor = clazz.getConstructor();
                    } catch (NoSuchMethodException ignored) {
                    }

                    if (constructor != null && Modifier.isPublic(constructor.getModifiers())) {
                        delegate = clazz.newInstance();
                    }
                }
            } catch (Exception ignore) {
                LOGGER.error("Instantiating error: [{}], {}", clazz.getSimpleName(), ignore.getMessage());
            }

            if (delegate != null) {
                /** 找出有{@link Accept}注解的并且继承自{@link MessageHandler}的类 */
                if (clazz.isAnnotationPresent(Accept.class) && clazz.isAssignableFrom(MessageHandler.class)) {
                    handlersMap.put(clazz.getAnnotation(Accept.class).value(), (MessageHandler<?>) delegate);
                } else {
                    Method[] methods = clazz.getDeclaredMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Accept.class)) {
                            MessageHandler handler = new MessageHandlerAdapter<Packet>(delegate, method);
                            handlersMap.put(method.getAnnotation(Accept.class).value(), handler);
                        }
                    }
                }
            }
        }

        return handlersMap;
    }
}
