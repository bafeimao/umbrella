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

import net.bafeimao.umbrella.support.server.handler.DefaultServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/19.
 *
 * @author gukaitong
 * @since 1.0
 */
public class MessageHandlerAdapter<T> implements MessageHandler<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServerHandler.class);

    private Object delegate;
    private Method handleMethod;

    public MessageHandlerAdapter(Object delegate, Method method) {
        this.delegate = delegate;
        this.handleMethod = method;
    }

    /**
     * 执行对指定消息的处理
     *
     * @param ctx     消息执行上下文
     * @param message 要处理的消息
     * @throws HandlerExecutionException 消息处理器执行异常
     */
    @Override
    public void handle(HandlerContext ctx, T message) throws HandlerExecutionException {
        try {
            handleMethod.invoke(delegate, ctx, message);
        } catch (Exception e) {
            throw new HandlerExecutionException(e); // Wrap exception
        }
    }
}
