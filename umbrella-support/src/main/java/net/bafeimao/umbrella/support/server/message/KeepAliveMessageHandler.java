package net.bafeimao.umbrella.support.server.message;

import net.bafeimao.umbrella.support.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.support.generated.CommonProto.Packet;

/**
 * Created by ktgu on 15/11/22.
 */
@Accept(MessageType.KEEP_ALIVE)
public class KeepAliveMessageHandler implements MessageHandler<Packet> {
    @Override
    public void handle(HandlerContext ctx, Packet message) {

    }
}
