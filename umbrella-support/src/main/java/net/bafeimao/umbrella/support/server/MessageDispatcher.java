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

import com.google.common.base.Preconditions;
import com.google.protobuf.InvalidProtocolBufferException;
import net.bafeimao.umbrella.support.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.support.generated.CommonProto.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/10.
 *
 * @author gukaitong
 * @since 1.0
 */
public class MessageDispatcher<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServerHandler.class);

    private Map<MessageType, MessageHandler> messageTypeHandlerMap = new HashMap<MessageType, MessageHandler>();
    private MiscService miscService = new MiscService();

    {
        Method[] methods = MiscService.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Accept.class)) {
                Accept annotation = method.getAnnotation(Accept.class);
                MessageHandler handler = new MessageHandlerAdapter(miscService, method);
                messageTypeHandlerMap.put(annotation.value(), handler);
            }
        }
    }

    public MessageHandler getHandler(MessageType messageType) {
        Preconditions.checkNotNull(messageType, "messageType");

        return messageTypeHandlerMap.get(messageType);
    }

    public void dispatch(Packet message) {
        if (message != null) {
            try {
                MessageHandler handler = getHandler(message.getType());
                handler.handle(message);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("{}", e);
            }
        }
    }
}
