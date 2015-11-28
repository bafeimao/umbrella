package net.bafeimao.umbrella.support.server;

/**
 * Created by ktgu on 15/11/28.
 */
public interface Server {
    String LOCAL_ADDRESS = "localhost";

    void start();

    void stop();
}
