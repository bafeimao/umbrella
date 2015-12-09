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

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/19.
 *
 * @author gukaitong
 * @since 1.0
 */
public class MessageHandlerAdapter<T> implements MessageHandler<T> {
    // private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandlerAdapter.class);

    private Object delegate;
    private Method method;

    public MessageHandlerAdapter(Object delegate, Method method) {
        this.delegate = delegate;
        this.method = method;
        method.setAccessible(true);
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
            method.invoke(delegate, ctx, message);
        } catch (IllegalAccessException e) {
            throw new Error("方法调用被拒绝，因为方法不可访问:" + delegate, e);
        } catch (IllegalArgumentException e) {
            throw new Error("方法调用被拒绝，因为发生了参数异常:" + delegate, e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw (Error) e.getCause();
            }
            throw new HandlerExecutionException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageHandlerAdapter<?> that = (MessageHandlerAdapter<?>) o;
        return Objects.equal(delegate, that.delegate) &&
                Objects.equal(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(delegate, method);
    }

    @Override
    public String toString() {
        return "MessageHandlerAdapter{" +
                "delegate=" + delegate +
                ", method=" + method +
                '}';
    }
}
