package net.bafeimao.umbrella.support.server.message;

/**
 * Created by ktgu on 15/11/22.
 */
public class MessageHandlerInvocationException extends  Exception {
    public MessageHandlerInvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageHandlerInvocationException(Throwable cause) {
        super(cause);
    }

    public MessageHandlerInvocationException() {
        super();
    }
}
