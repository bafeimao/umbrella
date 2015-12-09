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

package net.bafeimao.umbrella.support.network.netty.message;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import net.bafeimao.umbrella.support.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.support.generated.CommonProto.Packet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/19.
 *
 * @author gukaitong
 * @since 1.0
 */
public class PacketWrapper {
    private static Map<String, MessageType> messageTypesByName = new HashMap<String, MessageType>();
    private static AtomicLong SEQUENCE = new AtomicLong(1);

    static {
        List<EnumValueDescriptor> valueDescriptors = MessageType.getDescriptor().getValues();
        for (EnumValueDescriptor descriptor : valueDescriptors) {
            messageTypesByName.put(descriptor.getName().replace("_", ""), MessageType.valueOf(descriptor));
        }
    }

    public static Packet wrap(MessageLiteOrBuilder message) {
        if (message instanceof Packet) {
            return ((Packet) message);
        }

        if (message instanceof Packet.Builder) {
            return ((Packet.Builder) message).build();
        }

        ByteString bytes = toByteString(message);

        Packet.Builder builder = Packet.newBuilder();
        builder.setSequence(SEQUENCE.incrementAndGet());
        MessageType messageType = getMessageType(message);
        builder.setType(messageType);
        builder.setContent(bytes);

        return builder.build();
    }

    public static Packet.Builder wrapBuilder(MessageLiteOrBuilder message) {
        if (message instanceof Packet) {
            return ((Packet) message).toBuilder();
        }

        if (message instanceof Packet.Builder) {
            return (Packet.Builder) message;
        }

        ByteString bytes = toByteString(message);

        Packet.Builder builder = Packet.newBuilder();
        builder.setSequence(SEQUENCE.incrementAndGet());
        MessageType messageType = getMessageType(message);
        builder.setType(messageType);
        builder.setContent(bytes);

        return builder;
    }

    private static ByteString toByteString(MessageLiteOrBuilder message) {
        ByteString bytes = null;

        if (message instanceof MessageLite) {
            bytes = ((MessageLite) message).toByteString();
        } else if (message instanceof MessageLite.Builder) {
            bytes = ((MessageLite.Builder) message).build().toByteString();
        }

        return bytes;
    }

    private static MessageType getMessageType(MessageLiteOrBuilder message) {
        String key = null;
        if (message instanceof MessageLite) {
            key = message.getClass().getSimpleName();
        }

        if (message instanceof MessageLite.Builder) {
            key = ((MessageLite.Builder) message).build().getClass().getSimpleName();
        }

        return messageTypesByName.get(key.toUpperCase());
    }
}
