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

package net.bafeimao.umbrella.support.server.message;

import com.google.common.base.Preconditions;
import com.google.protobuf.InvalidProtocolBufferException;
import net.bafeimao.umbrella.support.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.support.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.server.handler.DefaultServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/10.
 *
 * @author gukaitong
 * @since 1.0
 */
public class PacketMessageDispatcher implements MessageDispatcher<Packet> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServerHandler.class);
    private Map<Object, MessageHandler> handlerMap = new HashMap<Object, MessageHandler>();

    private MessageHandler getHandler(MessageType messageType) {
        Preconditions.checkNotNull(messageType, "messageType");
        return handlerMap.get(messageType);
    }

    @Override
    public void dispatch(HandlerContext ctx, Packet message) {
        if (message != null) {
            MessageHandler handler = getHandler(message.getType());

            if (handler != null) {
                try {
                     handler.handle(ctx, message);
                }
                catch (MessageHandlerInvocationException e) {
                    LOGGER.error("{}", e);

                    // TODO 统一告诉客户端,服务器内部错误(SERVER_INTERNAL_ERROR),屏蔽服务器内部错误细节
                }
                catch (MessageHandlerExecutionException e) {
                    LOGGER.error("{}", e);

                    Throwable cause = e.getCause();

                    if(cause instanceof InvalidProtocolBufferException) {
                        // TODO 告诉客户端协议解析错误
                    } else {
                        // TODO 发送统一的"消息处理异常"
                    }
                }
            } else {
                LOGGER.error("*** No handler for {}", message.getType());
            }
        }
    }

    @Override
    public void registerHandler(Object key, MessageHandler<Packet> handler) {
        this.handlerMap.put(key, handler);
    }
}
