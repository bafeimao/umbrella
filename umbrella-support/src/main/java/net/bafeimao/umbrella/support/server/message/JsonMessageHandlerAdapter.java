package net.bafeimao.umbrella.support.server.message;

import java.lang.reflect.Method;

/**
 * Created by ktgu on 15/11/22.
 */
public class JsonMessageHandlerAdapter extends MessageHandlerAdapter<Object[]> {
    public JsonMessageHandlerAdapter(Object delegate, Method method) {
        super(delegate, method);
    }

    @Override
    public void handle(HandlerContext ctx, Object[] message)
            throws MessageHandlerInvocationException, MessageHandlerExecutionException {
        int messageType = (Integer) message[0];
        Object[] content = (Object[]) message[1];
        super.handle(ctx, content);
    }
}
