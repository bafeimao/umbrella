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

import com.google.protobuf.MessageOrBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import net.bafeimao.umbrella.support.generated.CommonProtocol.Packet;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/12.
 *
 * @author gukaitong
 * @since 1.0
 */
public class SimpleSocketClient {

    public SimpleSocketClient() {
    }

    public void connect(int port) {
        connect("localhost", port);
    }

    public void connect(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new ClientChannelInitializer());

            // Make a new connection.
            Channel ch = b.connect(host, port).sync().channel();

            // Get the handler instance to initiate the request.
            ClientHandler handler = ch.pipeline().get(ClientHandler.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void write(MessageOrBuilder message) {
        // do nothing
    }

    class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
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

    class ClientHandler extends SimpleChannelInboundHandler<Packet> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {

        }
    }
}
