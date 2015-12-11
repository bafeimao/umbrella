package net.bafeimao.umbrella.servers.world.handler;

import net.bafeimao.umbrella.support.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.support.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.server.message.Accept;
import net.bafeimao.umbrella.support.server.message.HandlerContext;
import net.bafeimao.umbrella.support.server.message.HandlerExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ktgu on 15/11/28.
 */
public class RoleMessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleMessageService.class);

    @Accept(MessageType.LOGIN_REQUEST)
    public void login(HandlerContext ctx, Packet packet) throws HandlerExecutionException {
        LOGGER.info("handling LOGIN_REQUEST ...");
    }
}
