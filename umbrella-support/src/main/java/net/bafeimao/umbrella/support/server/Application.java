/*
 * Copyright 2002-2015 by bafeimao.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.bafeimao.umbrella.support.server;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import net.bafeimao.umbrella.support.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.network.netty.ProtobufEncoder;
import net.bafeimao.umbrella.support.server.handler.DefaultServerHandler;
import net.bafeimao.umbrella.support.server.handler.ProtocolStatsHandler;
import net.bafeimao.umbrella.support.server.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * Created by bafeimao on 2015/11/2.
 *
 * @author bafeimao
 * @since 1.0
 */
public class Application {
    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private ApplicationContext context;
    private ApplicationConfig config;
    private ServerInfo serverInfo;
    private String configPath;
    private volatile Integer state = 0;
    private ServerManager serverManager = ServerManager.getInstance();
    private EventBus syncEventBus;

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public void start() {
        LOGGER.info("Starting application");

        try {
            this.loadConfig();

            this.printConfig();

            this.startSocketServer();

            if (config.isRpcServerEnabled()) {
                this.startRpcServer();
            }

            serverManager.register(this.getServerInfo());

            LOGGER.info("Adding shutdown hook");
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    System.err.println("*** shutting down application since JVM is shutting down");
                    Application.this.stop();
                    System.err.println("*** Application was shut down");
                }
            });
        } catch (Exception e) {
            LOGGER.error("{}", e);
            System.exit(0);
        }
    }

    private void printConfig() {
        LOGGER.info("Application configurations:");

        for (Iterator<String> iterator = config.getKeys(); iterator.hasNext(); ) {
            String key = iterator.next();
            LOGGER.debug("{}：{}", key, config.getProperty(key));
        }
    }

    public ApplicationConfig getConfig() {
        return config;
    }

    private void loadConfig() {
        Preconditions.checkNotNull(configPath, "configPath is null");

        LOGGER.info("Loading application configurations");

        try {
            config = new ApplicationConfig(configPath);
        } catch (Exception e) {
            LOGGER.error("{}", e);
            System.exit(0);
        }
    }

    public void stop() {
        LOGGER.info("Stopping application ...");

        // Post ServerClosingEvent
        this.syncEventBus.post(new ServerClosingEvent());

        serverManager.unregister(getServerInfo());
    }

    private void startRpcServer() {
        LOGGER.info("RPC server is starting ...");

        LOGGER.info("RPC server started!");
    }

    private void startSocketServer() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Socket server is starting ...");

                // 声明一个MessageDispatcher对象,该对象用于分发消息到具体的handler
                PacketMessageDispatcher dispatcher = new PacketMessageDispatcher();

                // 为dispatcher注册handler
                MiscService miscService = new MiscService();
                Method[] methods = MiscService.class.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Accept.class)) {
                        Accept annotation = method.getAnnotation(Accept.class);
                        MessageHandler handler = new MessageHandlerAdapter<Packet>(miscService, method);
                         dispatcher.registerHandler(annotation.value(), handler);
                    }
                }

                // TODO 搜索实现了MessageHandler接口并且带有@Accept注解的的所有的类

                // TODO 将以下代码换成自动搜索并自动添加到handler查找表中
                Accept annotation = KeepAliveMessageHandler.class.getAnnotation(Accept.class);
                if (annotation != null) {
                    dispatcher.registerHandler(annotation.value(), new KeepAliveMessageHandler());
                }

                final DefaultServerHandler defaultServerHandler = new DefaultServerHandler(dispatcher);

                EventLoopGroup bossGroup = new NioEventLoopGroup(1);
                EventLoopGroup workerGroup = new NioEventLoopGroup();
                try {
                    ServerBootstrap b = new ServerBootstrap();
                    b.group(bossGroup, workerGroup)
                            .channel(NioServerSocketChannel.class)
                            .handler(new LoggingHandler(LogLevel.INFO))
                            .childHandler(new ChannelInitializer() {
                                @Override
                                protected void initChannel(Channel ch) throws Exception {
                                    ChannelPipeline p = ch.pipeline();

                                    p.addLast(new ProtobufVarint32FrameDecoder());
                                    p.addLast(new ProtobufDecoder(Packet.getDefaultInstance()));

                                    p.addLast(new ProtobufVarint32LengthFieldPrepender());
                                    p.addLast(new ProtobufEncoder());

                                    p.addLast(new IdleStateHandler(10, 4, 20));
//                                    p.addLast( new ChannelTrafficShapingHandler(new HashedWheelTimer()));
                                    p.addLast(defaultServerHandler);
                                    p.addLast(new ProtocolStatsHandler());
                                    p.addLast(defaultServerHandler);
                                }
                            });

                    int port = getServerInfo().getPort();
                    ChannelFuture future = b.bind(port).sync();

                    LOGGER.info("Socket server started, Listening on {}", port);

                    // 阻塞直到关闭
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    bossGroup.shutdownGracefully();
                    workerGroup.shutdownGracefully();
                }

            }
        }, "socket-server-bootstrap").start();
    }

    public ServerInfo getServerInfo() {
        if (serverInfo == null) {
            serverInfo = new ServerInfo(this.config);
        }
        return serverInfo;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
