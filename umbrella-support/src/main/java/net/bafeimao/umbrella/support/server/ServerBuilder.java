package net.bafeimao.umbrella.support.server;

import com.google.common.base.Preconditions;

/**
 * Created by ktgu on 15/11/20.
 */
public class ServerBuilder {
    private ServerBuilder() {}

    public static ServerBuilder newBuilder() {
        return new ServerBuilder();
    }

    private String configLocation;

    private ApplicationConfig config;

    public ServerBuilder configLocation(String configLocation) {
        this.configLocation = configLocation;

        // TODO load config and parse to ApplicationConfig

        return this;
    }

    public ApplicationConfig getConfig() {
        if (config == null) {
            config = new ApplicationConfig(configLocation);
        }
        return config;
    }

    private SocketServerBuilder socketServerBuilder;

    public ServerBuilder socketServerBuilder(SocketServerBuilder builder) {
        socketServerBuilder = builder;
        return this;
    }

    private boolean isRpcServerSupported=false;
    public void build() {
        if (this.configLocation != null) {
            loadConfig(this.configLocation).print();
        }

        if(socketServerBuilder !=null) {
            socketServerBuilder.build().start();
        }

        if(isRpcServerSupported) {
            // TODO 啟動RPC
        }

        // TODO 註冊服務器

    }

    private ApplicationConfig loadConfig(String configLocation) {
        Preconditions.checkNotNull("configLocation");
        config = new ApplicationConfig(configLocation);
        return config;
    }

    public static void main(String[] args) {
        ServerBuilder.newBuilder()
                .configLocation("server.properties");

    }
}
