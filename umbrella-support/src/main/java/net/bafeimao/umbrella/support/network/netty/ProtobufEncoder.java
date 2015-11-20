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

package net.bafeimao.umbrella.support.network.netty;

import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.bafeimao.umbrella.support.generated.CommonProto.Packet;

import java.util.List;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/19.
 *
 * @author gukaitong
 * @since 1.0
 */
public class ProtobufEncoder extends MessageToMessageEncoder<MessageLiteOrBuilder> {
    @Override
    protected void encode(
            ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
//        if (msg instanceof MessageLite) {
//            out.add(wrappedBuffer(((MessageLite) msg).toByteArray()));
//            return;
//        }
//        if (msg instanceof MessageLite.Builder) {
//            out.add(wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray()));
//        }

        Packet packet = PacketWrapper.wrap(msg);
        if (packet != null) {
            out.add(wrappedBuffer(packet.toByteArray()));
        }
    }
}