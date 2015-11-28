package net.bafeimao.umbrella.support.server.message;

/**
 * Created by ktgu on 15/11/22.
 */
public class HandlerInvocationException extends  Exception {
    public HandlerInvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandlerInvocationException(Throwable cause) {
        super(cause);
    }

    public HandlerInvocationException() {
        super();
    }
}
