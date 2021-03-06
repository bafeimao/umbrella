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

package net.bafeimao.umbrella.servers.world.handler;

import net.bafeimao.umbrella.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.generated.CommonProto.Notification;
import net.bafeimao.umbrella.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.server.message.Listen;
import net.bafeimao.umbrella.support.server.message.HandlerContext;
import net.bafeimao.umbrella.support.server.message.HandlerExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/19.
 *
 * @author gukaitong
 * @since 1.0
 */
public class MiscMessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MiscMessageService.class);

    @Listen(MessageType.NOTIFICATION)
    public void notification(HandlerContext ctx, Packet packet) throws HandlerExecutionException {
        LOGGER.info("handle notification from client...");

        MessageType type;
        // type.getNumber()

        ctx.write(Notification.newBuilder().setText("pong"));
    }
}
