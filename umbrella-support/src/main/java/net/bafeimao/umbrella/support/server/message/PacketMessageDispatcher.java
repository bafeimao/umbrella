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
import net.bafeimao.umbrella.support.generated.CommonProto;
import net.bafeimao.umbrella.support.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.support.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.server.handler.DefaultServerHandler;
import net.bafeimao.umbrella.support.util.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
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
    public void dispatch(HandlerContext ctx, Packet packet) {
        if (packet != null) {
            MessageHandler handler = getHandler(packet.getType());

            if (handler != null) {
                try {
                    handler.handle(ctx, packet);
                } catch (HandlerExecutionException e) {
                    Throwable cause = e.getCause();

                    if (cause instanceof InvalidProtocolBufferException) {
                        LOGGER.error("Handler执行异常 -> 协议解析错误:{}", cause);
                    } else if (cause instanceof IllegalAccessException
                            || cause instanceof InvocationTargetException) {
                        LOGGER.error("Handler执行异常 -> 调用Method时发生异常:{}", cause);
                    } else {
                        LOGGER.error("Handler执行异常 -> {}", cause);
                    }

                    // 告诉客户端,服务器发送了内部错误(这里给出适当的错误描述,方便双方联调)
                    Errors.sendError(ctx, packet, CommonProto.ErrorCode.SERVER_INTERNAL_ERROR_VALUE, e.getMessage());
                }
            } else {
                LOGGER.error("*** No handler for {} ***", packet.getType());
            }
        }
    }

    @Override
    public void registerHandler(Object key, MessageHandler<Packet> handler) {
        this.handlerMap.put(key, handler);
    }
}
