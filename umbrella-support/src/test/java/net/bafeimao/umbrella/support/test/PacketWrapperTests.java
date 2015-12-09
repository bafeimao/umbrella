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

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import net.bafeimao.umbrella.support.generated.CommonProto;
import net.bafeimao.umbrella.support.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.support.generated.CommonProto.Notification;
import net.bafeimao.umbrella.support.generated.CommonProto.PacketOrBuilder;
import net.bafeimao.umbrella.support.network.netty.message.PacketWrapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/19.
 *
 * @author gukaitong
 * @since 1.0
 */
public class PacketWrapperTests {
    @Test
    public void test1() {
        Notification notification = Notification.newBuilder().setText("hello").build();
        PacketOrBuilder packet = PacketWrapper.wrap(notification);
        System.out.println(packet);
    }

    @Test
    public void test2() {
        Map<String, MessageType> valueTypeMap = new HashMap<String, MessageType>();
        List<EnumValueDescriptor> valueDescriptors = MessageType.getDescriptor().getValues();
        for (EnumValueDescriptor descriptor : valueDescriptors) {
            valueTypeMap.put(descriptor.getName(), MessageType.valueOf(descriptor));
        }
    }

    @Test
    public void test3() {
        ByteString byteString = Notification.newBuilder().setText("abc").build().toByteString();

        Map<String, Descriptor> nameDescriptorMap = new HashMap<String, Descriptor>();
        List<Descriptor> descriptors = CommonProto.getDescriptor().getMessageTypes();
        for (Descriptor descriptor : descriptors) {
            nameDescriptorMap.put(descriptor.getFullName(), descriptor);
        }
        System.out.println(nameDescriptorMap);

        System.out.println(Notification.class.getName());
        System.out.println(Notification.class.getCanonicalName());
        System.out.println(Notification.class.getName());

//        FileDescriptor fileDescriptor = message.getDescriptorForType().getFile();
//        Descriptor descriptor = fileDescriptor.findMessageTypeByName("fileDescriptor");
//        descriptor.toProto().toByteString();
    }
}
