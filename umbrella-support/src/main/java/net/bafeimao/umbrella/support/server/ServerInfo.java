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

import com.google.common.base.MoreObjects;

/**
 * Created by bafeimao on 2015/11/2.
 *
 * @author bafeimao
 * @since 1.0
 */
public class ServerInfo {
    private int id;
    private int type;
    private String host;
    private String innerHost;
    private int port;
    private int innerPort;
    private int rpcPort;

    public ServerInfo(ApplicationConfig config) {
        id = config.getInt("server.id");
        type = config.getInt("server.type", -1);
        host = config.getString("server.host");
        port = config.getInt("server.port");
        innerHost = config.getString("server.innerHost");
        innerPort = config.getInt("server.innerPort");
        rpcPort = config.getInt("server.rpcPort", -1);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInnerHost() {
        return innerHost;
    }

    public void setInnerHost(String innerHost) {
        this.innerHost = innerHost;
    }

    public int getInnerPort() {
        return innerPort;
    }

    public void setInnerPort(int innerPort) {
        this.innerPort = innerPort;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getRpcPort() {
        return rpcPort;
    }

    public void setRpcPort(int rpcPort) {
        this.rpcPort = rpcPort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("host", host)
                .add("port", port)
                .toString();
    }
}
