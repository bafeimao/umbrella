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
package net.bafeimao.umbrella.support.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import net.bafeimao.umbrella.support.generated.CommonProto.ErrorCode;
import net.bafeimao.umbrella.support.generated.CommonProto.Notification;
import net.bafeimao.umbrella.support.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.server.Application;
import net.bafeimao.umbrella.support.server.message.HandlerContext;
import net.bafeimao.umbrella.support.server.message.MessageDispatcher;
import net.bafeimao.umbrella.support.server.message.NettyBasedChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DefaultServerHandler extends SimpleChannelInboundHandler<Packet> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServerHandler.class);
    private MessageDispatcher<Packet> messageDispatcher;
    private AttributeKey<HandlerContext> key = AttributeKey.newInstance("neutral_context");

    public DefaultServerHandler(MessageDispatcher<Packet> dispatcher) {
        messageDispatcher = dispatcher;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        if (Application.getInstance().getState() != Application.STARTED) {
            Packet.Builder builder = Packet.newBuilder();
            builder.setContent(Notification.newBuilder().setText("服务器还没有启动好").build().toByteString());
            ctx.write(builder);
        } else {
            super.channelRegistered(ctx);
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        LOGGER.info("RECEIVED: {} [type:{}]", packet, packet.getType());

        try {
            ctx.attr(key).setIfAbsent(new NettyBasedChannelHandlerContext(ctx));
            HandlerContext context = ctx.attr(key).get();

            messageDispatcher.dispatch(context, packet);
        } catch (Exception e) {
            LOGGER.error("消息处理时发生未捕获的异常:{}", e);

            ctx.write(packet.toBuilder().setError(ErrorCode.SERVER_INTERNAL_ERROR_VALUE));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof IOException) {
            LOGGER.error("Client disconnected, id: {}, remote: {}", ctx.channel().id(), ctx.channel().remoteAddress());
        } else {
            LOGGER.error("{}", cause);
        }

        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                LOGGER.info("READER_IDLE");
//                ctx.close(); // 断开客户端的连接
            } else if (e.state() == IdleState.WRITER_IDLE) {
                LOGGER.info("WRITER_IDLE");
            } else if (e.state() == IdleState.ALL_IDLE) {
                LOGGER.info("ALL_IDLE");
                //                ctx.close(); // 断开客户端的连接
            }
        }

        super.userEventTriggered(ctx, evt);
    }
}
