package net.bafeimao.umbrella.support.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ktgu on 15/11/28.
 */
public class SocketServer implements Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);
    private String host;
    private int port;
    private ServerBootstrap serverBootStrap;

    public SocketServer(int port) {
        this(LOCAL_ADDRESS, port);
    }

    public SocketServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Socket server is starting ...");

                try {
                    ChannelFuture future = serverBootStrap.bind(port);

                    LOGGER.info("Socket server started, Listening on {}", port);

                    // 保持一直等到Channel关闭时该线程才会退出
                    future.sync().channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    LOGGER.error("Socket服务器线程被异常中断!");
                } finally {
                    LOGGER.info("Socket服务即将关闭 ...");

                    serverBootStrap.group().shutdownGracefully();
                    serverBootStrap.childGroup().shutdownGracefully();
                }

            }
        }, "socket-server-bootstrap").start();
    }

    @Override
    public void stop() {

    }

    public void setBootstrap(ServerBootstrap bootstrap) {
        this.serverBootStrap = bootstrap;
    }
}
