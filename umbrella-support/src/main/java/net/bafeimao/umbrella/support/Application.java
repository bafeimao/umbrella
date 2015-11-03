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

package net.bafeimao.umbrella.support;

import com.google.api.client.repackaged.com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
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
    private ServerManager serverManager;
    private EventBus syncEventBus;

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }


    public void start() {
        LOGGER.info("Application is starting...");

        try {
            this.loadConfig();

            this.printConfig();

            this.startSocketServer();

            if (config.isRpcServerEnabled()) {
                this.startRpcServer();
            }
        } catch (Exception e) {
            LOGGER.error("启动应用程序出错，系统终止！ {}", e);
            System.exit(0);
        }
    }

    private void startSocketServer() throws InterruptedException {
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

//                            if (sslCtx != null) {
//                                p.addLast(sslCtx.newHandler(ch.alloc()));
//                            }

                            p.addLast(new ProtobufVarint32FrameDecoder());
                            p.addLast(new ProtobufDecoder(WorldClockProtocol.Locations.getDefaultInstance()));

                            p.addLast(new ProtobufVarint32LengthFieldPrepender());
                            p.addLast(new ProtobufEncoder());

                            p.addLast(new WorldClockServerHandler());
                        }
                    });


            b.bind(getConfig().getInt("server.port")).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private void printConfig() {
        LOGGER.info("Configurations:\n {}", config);
    }

    public ApplicationConfig getConfig() {
        return config;
    }

    private void loadConfig() {
        Preconditions.checkNotNull(configPath, "configPath is null");

        try {
            config = new ApplicationConfig(configPath);
        } catch (Exception e) {
            LOGGER.error("加载系统配置文件出错，系统终止!");
            System.exit(0);
        }
    }

    public void stop() {
        LOGGER.info("Application is stopping...");

        // Post ServerClosingEvent
        this.syncEventBus.post(new ServerClosingEvent());

        serverManager.unregister(getServerInfo());
    }

    private void startRpcServer() {
        LOGGER.info("The rpc server is starting...");

        LOGGER.info("The rpc server was started successfully!");
    }


    public void startServer() {
        // 向网络上注册本服务器实例
        serverManager.register(this.getServerInfo());

        // Post event
        this.syncEventBus.post(new ServerClosingEvent());

        LOGGER.info("添加JVM关闭hook!");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                serverManager.register(Application.this.getServerInfo());
            }
        });
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }


    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
