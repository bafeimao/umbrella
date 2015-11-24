package net.bafeimao.umbrella.support.server.message;

/**
 * Created by ktgu on 15/11/22.
 */
public interface MessageDispatcher<T> {
    void dispatch(HandlerContext ctx, T message);

    void registerHandler(Object key, MessageHandler<T> handler);
}
