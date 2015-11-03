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

package net.bafeimao.umbrella.support;

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

/**
 * Created by bafeimao on 2015/11/2.
 *
 * @author bafeimao
 * @since 1.0
 */
public class ServerManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerManager.class);

    /**
     * 向中心服务器上注册服务器
     *
     * @param server 服务器信息
     */
    public void register(ServerInfo server) {
        LOGGER.info("Registering server: {}", server);

    }

    /**
     * 从中心服务器上取消注册服务器
     *
     * @param server 服务器信息
     */
    public void unregister(ServerInfo server) {
        LOGGER.info("Unregistering server: {}", server);
    }
}
