package net.bafeimao.umbrella.servers.world.handler;

import net.bafeimao.umbrella.support.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.server.message.HandlerContext;
import net.bafeimao.umbrella.support.server.message.HandlerExecutionException;
import net.bafeimao.umbrella.support.server.message.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ktgu on 15/11/28.
 */
public class KeepAliveMessageHandler implements MessageHandler<Packet> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeepAliveMessageHandler.class);

    @Override
    public void handle(HandlerContext ctx, Packet message) throws HandlerExecutionException {
        // NOOP
    }
}
