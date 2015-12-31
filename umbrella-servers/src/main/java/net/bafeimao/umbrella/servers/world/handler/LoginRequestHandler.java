package net.bafeimao.umbrella.servers.world.handler;

import net.bafeimao.umbrella.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.server.message.DelegatedMessageHandler;
import net.bafeimao.umbrella.support.server.message.HandlerContext;
import net.bafeimao.umbrella.support.server.message.HandlerExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginRequestHandler extends DelegatedMessageHandler<RoleMessageService, Packet> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRequestHandler.class);

    @Override
    public void handle(HandlerContext ctx, Packet message) throws HandlerExecutionException {
        delegate.login(ctx, message);
    }

    public int getMessageType() {
        return MessageType.LOGIN_REQUEST.getNumber();
    }
}