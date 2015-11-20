/*
 * Copyright 2002-2015 by bafeimao.net, The umbrella Project
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

package net.bafeimao.umbrella.servers.world.test;

import net.bafeimao.umbrella.servers.generated.AccountProto.LoginRequest;
import net.bafeimao.umbrella.support.generated.CommonProto.KeepAlive;
import net.bafeimao.umbrella.support.generated.CommonProto.Notification;
import net.bafeimao.umbrella.support.network.netty.SimpleSocketClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/18.
 *
 * @author gukaitong
 * @since 1.0
 */
public class ClientTests {
    private SimpleSocketClient socketClient;

    @Before
    public void setup() {
        socketClient = new SimpleSocketClient(3301);
    }

    @Test
    public void testConnect() {
        socketClient.connect();
    }

    @Test
    public void testLogin() throws InterruptedException {
        LoginRequest.Builder builder = LoginRequest.newBuilder();
        builder.setUid(1L);
        socketClient.write(builder);
    }

    @Test
    public void testKeepAlive() throws InterruptedException {
        socketClient.write(KeepAlive.getDefaultInstance());
    }

    @Test
    public void testNotification() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            socketClient.write(Notification.newBuilder().setText("ping"));
        }
        System.out.println(socketClient.getMessages());
    }

    @After
    public void destroy() throws InterruptedException {
        TimeUnit.SECONDS.sleep(100);
        socketClient.disconnect();
    }
}
