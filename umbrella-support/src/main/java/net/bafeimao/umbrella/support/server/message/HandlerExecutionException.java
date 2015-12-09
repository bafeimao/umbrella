package net.bafeimao.umbrella.support.server.message;

/**
 * Created by ktgu on 15/11/22.
 */
public class HandlerExecutionException extends Exception {
    public HandlerExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandlerExecutionException(Throwable cause) {
        super(cause);
    }

    public HandlerExecutionException() {
        super();
    }
}
