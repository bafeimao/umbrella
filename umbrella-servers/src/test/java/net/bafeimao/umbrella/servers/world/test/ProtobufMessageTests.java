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

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import net.bafeimao.umbrella.servers.generated.TestProto.MyMap;
import net.bafeimao.umbrella.servers.generated.TestProto.Test1;
import net.bafeimao.umbrella.generated.CommonProto.KeepAlive;
import net.bafeimao.umbrella.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.generated.CommonProto.Packet;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/11.
 *
 * @author gukaitong
 * @since 1.0
 */
public class ProtobufMessageTests {
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

    @Test
    public void testMessageMerge() {
        Test1.Builder builder1 = Test1.newBuilder();
        builder1.setA(1);
        System.out.println(builder1);

        Test1.Builder builder2 = Test1.newBuilder();
        builder2.setB("hello");
        System.out.println(builder2);

        Test1.Builder builder3 = builder1.mergeFrom(builder2.build());
        System.out.println(builder3);
        Assert.assertEquals(builder1, builder3);

        Test1.Builder builder4 = builder3.clear();
        System.out.println(builder4);
        Assert.assertEquals(builder4, builder3);
    }

    @Test
    public void testMessageMerge1() {
        Test1.Builder builder1 = Test1.newBuilder();
        builder1.setA(1);
        System.out.println(builder1);

        Test1.Builder builder2 = Test1.newBuilder();
        builder2.setB("hello");
        System.out.println(builder2);

        Test1.Builder builder3 = builder1.mergeFrom(builder2.build());
        System.out.println(builder3);
        Assert.assertEquals(builder1, builder3);
    }

    @Test
    public void test() {
        Packet.Builder builder = Packet.newBuilder();
    }

    @Test
    public void testMapTypeMessage() {
        Map<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("project1", 2015);
        MyMap myMap = MyMap.newBuilder().putAllProjects(map1).build();
        Map<String, Integer> map2 = myMap.getProjects();
        System.out.println(map2);
    }

    @Test
    public void test11() {
        Packet packet;
        Packet.Builder builder;
    }

    @Test
    public void test33() {
//        Any.Builder builder = Any.newBuilder().setValue(Test1.newBuilder().setA(1).build().getBBytes());
//        CommonProto.ErrorStatus.newBuilder().addDetails(builder);
    }

    private AtomicLong initialSequence = new AtomicLong(0);

    @Test
    public void test2() throws InvalidProtocolBufferException {
        Packet.Builder packetBuilder = Packet.newBuilder();
        packetBuilder.setSequence(initialSequence.incrementAndGet());
        packetBuilder.setType(MessageType.KEEP_ALIVE);

        KeepAlive.Builder keepAliveBuilder = KeepAlive.newBuilder();
        packetBuilder.setData(keepAliveBuilder.build().toByteString());

        ByteString data = packetBuilder.build().getData();
        packetBuilder.setData(data);
        Packet packet = packetBuilder.build();
        System.out.println(packet);

//        Notification.Builder builder2 = Notification.newBuilder().setData(builder1);
    }
}
