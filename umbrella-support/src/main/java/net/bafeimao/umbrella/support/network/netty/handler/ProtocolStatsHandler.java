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
package net.bafeimao.umbrella.support.network.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.bafeimao.umbrella.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.generated.CommonProto.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 该Handler用于记录每个协议执行的次数,以此可以计算出当前每个协议的调用频率
 * 我们可以设置某些协议的调hi用频率,一旦该协议调用频率超过我们预期的频率,可视为可能存在的攻击行为
 *
 * 设定某些禁用的协议
 */
public class ProtocolStatsHandler extends SimpleChannelInboundHandler<Packet> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolStatsHandler.class);

    private Map<MessageType, AtomicLong> protocolStatsMap = new HashMap<MessageType, AtomicLong>();

    {
        for (MessageType type : MessageType.values()) {
            protocolStatsMap.put(type, new AtomicLong(0));
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        try {
            MessageType messageType = packet.getType();

            // 增加统计数
            AtomicLong counter = protocolStatsMap.get(messageType);
            if(counter != null) {
                long n = counter.incrementAndGet();
                LOGGER.info("type:{} -> {}", messageType, n);

                ctx.fireChannelRead(packet);
            }else {
                LOGGER.error("MessageType:{} was not found!", messageType);
            }

         } catch (Exception e) {
            LOGGER.error("{}", e);
            // TODO 处理未处理的异常
        }
    }
}
