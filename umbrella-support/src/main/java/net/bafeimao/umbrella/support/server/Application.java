/*
 * Copyright 2002-2015 by bafeimao.net
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

package net.bafeimao.umbrella.support.server;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Created by bafeimao on 2015/11/2.
 *
 * @author bafeimao
 * @since 1.0
 */
public class Application {
    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private   static Application instance;
    private ApplicationContext context;
    private ApplicationConfig config;
    private ServerInfo serverInfo;
    private String configPath;
    volatile private int state = 0;
    private ServerManager serverManager = ServerManager.getInstance();
    private EventBus syncEventBus;

    public final static int STARTING = 1;
    public final static int STARTED = 2;
    public final static int STOPPING = 3;
    public final static int STOPPED = 4;

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public synchronized void start() {
        LOGGER.info("Starting application");

        instance = this;

        try {
            state = STARTING;

            this.loadConfig().print();

            this.onStarting(); // for extensions hook

            serverManager.register(this.getServerInfo());

            LOGGER.info("Adding shutdown hook");
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    state = STOPPING;

                    System.err.println("*** shutting down application since JVM is shutting down");
                    Application.this.stop();
                    System.err.println("*** Application was shut down");

                    state = STOPPED;
                }
            });

            state = STARTED;
        } catch (Exception e) {
            LOGGER.error("{}", e);
            System.exit(0);
        }
    }

    public static Application getInstance() {
        return instance;
    }

    public int getState() {
        return state;
    }

    private ApplicationConfig loadConfig() {
        Preconditions.checkNotNull(configPath, "configPath is null");

        LOGGER.info("Loading application configurations");

        try {
            config = new ApplicationConfig(configPath);
        } catch (Exception e) {
            LOGGER.error("{}", e);
            System.exit(0);
        }

        return config;
    }

    protected void onStarting() { }

    public void stop() {
        LOGGER.info("Stopping application ...");

        this.syncEventBus.post(new ServerClosingEvent());

        serverManager.unregister(getServerInfo());
    }

    public ServerInfo getServerInfo() {
        if (serverInfo == null) {
            serverInfo = new ServerInfo(this.config);
        }
        return serverInfo;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
