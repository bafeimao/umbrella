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

package net.bafeimao.umbrella.support.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import net.bafeimao.umbrella.support.generated.CommonProtocol.KeepAlive;
import net.bafeimao.umbrella.support.generated.CommonProtocol.MessageType;
import net.bafeimao.umbrella.support.generated.CommonProtocol.Packet;
import net.bafeimao.umbrella.support.generated.CommonProtocol.Test1;
import net.bafeimao.umbrella.support.util.JsonUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/11.
 *
 * @author gukaitong
 * @since 1.0
 */
public class MiscTests {
    @Test
    public void test1() {

        Test1.Builder builder = Test1.newBuilder();
        builder.setA(300);
        builder.setB("hello");
        builder.setC(ByteString.copyFrom("world".getBytes()));
        builder.setD(Test1.Test2.newBuilder().setA(1).build());
//        builder.addE(1);
//        builder.addE(2);
//        builder.addE(3);

        ByteString byteString = builder.build().toByteString();
        byte[] bytes = builder.build().toByteArray();
        System.out.println(bytes.toString());

//        Foo;

//        Test1.Builder builder = Test1.newBuilder();
//        builder.setId(150);
//
//        ByteString byteString = builder.build().toByteString();
//        byte[] bytes = builder.build().toByteArray();
//        System.out.println(builder.build().toByteString());
    }

    private AtomicLong initialSequence = new AtomicLong(0);

    @Test
    public void test2() throws InvalidProtocolBufferException {
        Packet.Builder packetBuilder = Packet.newBuilder();
        packetBuilder.setSequence(initialSequence.incrementAndGet());
        packetBuilder.setType(MessageType.KEEP_ALIVE);

        KeepAlive.Builder keepAliveBuilder = KeepAlive.newBuilder();
        packetBuilder.setMessage(keepAliveBuilder.build().toByteString());

        ByteString data = packetBuilder.build().getMessage();
        packetBuilder.setMessage(data);
        Packet packet = packetBuilder.build();
        System.out.println(packet);

//        Notification.Builder builder2 = Notification.newBuilder().setData(builder1);
    }

    @Test
    public void test12() {
        String json = "[[1000002,500,100],[1000003,10,101],[1000004,100,-1]]";

        List<List<Integer>> elements = JsonUtil.toBean(json, new TypeReference<List<List<Integer>>>() {
        });
        for (List<Integer> element : elements) {
            System.out.println(element);
        }

        List<List<Integer>> elements1 = JsonUtil.toBean(json, ArrayList.class);
        for (List<Integer> element : elements1) {
            System.out.println(element);
        }

        System.out.println(elements);
    }
}
