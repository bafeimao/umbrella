/*
 * Copyright 2002-2015 by bafeimao.net, The umbrella Project
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

package net.bafeimao.umbrella.support.network.netty;

import com.google.common.math.IntMath;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import net.bafeimao.umbrella.support.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.network.netty.codec.ProtobufEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/12.
 *
 * @author gukaitong
 * @since 1.0
 */
public class SimpleSocketClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSocketClient.class);

    private String host;
    private int port;
    private Channel channel;
    private EventLoopGroup group;
    private boolean connected = false;
    private int retryTimes = 0;
    private int maxRetryTimes = 10;
    private List<Packet> messages = new ArrayList<Packet>();

    public SimpleSocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public SimpleSocketClient(int port) {
        this("localhost", port);
    }

    public SimpleSocketClient connect() {
        group = new NioEventLoopGroup();

        do {
            try {
                Bootstrap b = new Bootstrap();
                b.group(group).channel(NioSocketChannel.class).handler(new ClientChannelInitializer());
                this.channel = b.connect(host, port).sync().channel();

                this.setConnected();
            } catch (Exception e) {
                LOGGER.error("{}", e.getMessage());
            }

            if (!connected) {
                try {
                    TimeUnit.SECONDS.sleep(Math.min(60, IntMath.pow(2, retryTimes)));
                } catch (InterruptedException ignored) {
                }

                LOGGER.info("Retrying to connect to server ... [{}]", retryTimes);
            }

        } while (!connected && retryTimes++ <= maxRetryTimes);

        if (!connected) {
            LOGGER.info("Failed to connect.");
        }

        return this;
    }

    private void setConnected() {
        this.connected = true;
        retryTimes = 0;
    }

    public Channel getChannel() {
        return channel;
    }

    public void disconnect() {
        group.shutdownGracefully();
        this.connected = false;
        retryTimes = 0;
    }

    public void write(MessageLiteOrBuilder message) {
        if (!connected) {
            this.connect(); // 发送数据时自动连接
        }
        channel.writeAndFlush(message);
    }

    public List<Packet> getMessages() {
        return messages;
    }

    private final class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline p = ch.pipeline();

            p.addLast(new ProtobufVarint32FrameDecoder());
            p.addLast(new ProtobufDecoder(Packet.getDefaultInstance()));

            p.addLast(new ProtobufVarint32LengthFieldPrepender());
            p.addLast(new ProtobufEncoder());

            p.addLast(new ClientHandler());
        }
    }

    private final class ClientHandler extends SimpleChannelInboundHandler<Packet> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
            messages.add(msg);
        }
    }
}
