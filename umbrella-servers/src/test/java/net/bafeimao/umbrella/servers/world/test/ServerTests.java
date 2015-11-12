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

import net.bafeimao.umbrella.support.generated.CommonProtocol.KeepAlive;
import net.bafeimao.umbrella.support.generated.CommonProtocol.MessageType;
import net.bafeimao.umbrella.support.generated.CommonProtocol.Packet;
import net.bafeimao.umbrella.support.network.netty.SimpleSocketClient;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/12.
 *
 * @author gukaitong
 * @since 1.0
 */
public class ServerTests {
    SimpleSocketClient socketClient;

    @Before
    public void setup() {
        socketClient = new SimpleSocketClient();
        socketClient.connect(3301);
    }

    @Test
    public void testSendKeepAlive() {
        Packet.Builder builder = Packet.newBuilder();
        builder.setSequence(888);
        builder.setType(MessageType.KEEP_ALIVE);
        builder.setMessage(KeepAlive.getDefaultInstance().toByteString());

        socketClient.write(builder);
    }
}
