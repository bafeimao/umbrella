package net.bafeimao.umbrella.support.server;

/**
 * Created by ktgu on 15/11/20.
 */
public class ServerBuilder {

    public ServerBuilder newBuilder() {
        return new ServerBuilder();
    }

    private boolean rpcServerEnabled = false;

    private boolean socketServerEnabled = false;
}
