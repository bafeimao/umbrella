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

package net.bafeimao.umbrella.servers.world;

import io.netty.handler.logging.LogLevel;
import net.bafeimao.umbrella.support.server.Application;
import net.bafeimao.umbrella.support.server.Server;
import net.bafeimao.umbrella.support.server.ServerInfo;
import net.bafeimao.umbrella.support.server.SocketServerBuilder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/3.
 *
 * @author gukaitong
 * @since 1.0
 */
public class WorldServer extends Application implements Server{

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("world/context.xml");
    }

    @Override
    public void onStarting() {
        ServerInfo serverInfo = getServerInfo();

        // 启动Socket服务
        SocketServerBuilder.newBuilder()
                .forAddress(serverInfo.getHost(), serverInfo.getPort())
                .handlerPackageScan("net.bafeimao.umbrella")
                .setEventLoopThreads(1, Runtime.getRuntime().availableProcessors() * 2)
                .setLogLevel(LogLevel.INFO)
                .build().start();

        // 启动RPC服务
    }
}
