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

package net.bafeimao.umbrella.support.server;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import net.bafeimao.umbrella.support.generated.CommonProto.KeepAlive;
import net.bafeimao.umbrella.support.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.support.generated.CommonProto.Notification;
import net.bafeimao.umbrella.support.generated.CommonProto.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/19.
 *
 * @author gukaitong
 * @since 1.0
 */
public class MiscService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServerHandler.class);

    @Accept(MessageType.KEEP_ALIVE)
    public void keepAlive(Packet packet) throws InvalidProtocolBufferException {
        LOGGER.info("handle keep alive...");

        KeepAlive keepAlive = KeepAlive.parseFrom(packet.getContent().toByteArray());

        // TODO keep alive logic goes here
    }

    @Accept(MessageType.NOTIFICATION)
    public void notification(ChannelHandlerContext ctx, Packet packet) throws InvalidProtocolBufferException {
        LOGGER.info("handle notification from client...");

        ctx.write(Notification.newBuilder().setText("pong"));
    }
}
