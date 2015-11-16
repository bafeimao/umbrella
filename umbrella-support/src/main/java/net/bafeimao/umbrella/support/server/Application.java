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
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.bafeimao.umbrella.support.generated.CommonProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

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
        } catch (Exception e) {
            LOGGER.error("{}", e);
            System.exit(0);
        }
    }


    private void printConfig() {
        LOGGER.info("Application configurations:\n{}", config);
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
        LOGGER.info("Starting RPC server ...");

        LOGGER.info("The rpc server was started successfully!");
    }

    public void startSocketServer() throws InterruptedException {
        LOGGER.info("Starting socket server");

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
                            p.addLast(new ProtobufDecoder(CommonProto.Packet.getDefaultInstance()));

                            p.addLast(new ProtobufVarint32LengthFieldPrepender());
                            p.addLast(new ProtobufEncoder());

                            p.addLast(new DefaultServerHandler());
                        }
                    });

            int port = getServerInfo().getPort();
            ChannelFuture future = b.bind(port);

            LOGGER.info("Socket server was started successfully, Listening on :{}", port);

//            future.sync().channel().closeFuture().sync();
            future.sync().channel().closeFuture();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

        serverManager.register(this.getServerInfo());

        LOGGER.info("Adding shutdown hook");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                serverManager.unregister(Application.this.getServerInfo());
            }
        });
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
