package net.bafeimao.umbrella.servers.world.handler;

import net.bafeimao.umbrella.generated.AccountProto;
import net.bafeimao.umbrella.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.server.message.HandlerContext;
import net.bafeimao.umbrella.support.server.message.HandlerExecutionException;
import net.bafeimao.umbrella.support.server.message.Listen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ktgu on 15/11/28.
 */
public class RoleMessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleMessageService.class);

    @Listen(MessageType.LOGIN_REQUEST)
    public void login(HandlerContext ctx, Packet packet) throws HandlerExecutionException {
        LOGGER.info("handling LOGIN_REQUEST ...");

        AccountProto.LoginResponse.Builder builder = AccountProto.LoginResponse.newBuilder();
        builder.setUid(10001);
        ctx.write(builder.build());

        // 发送用户名和密码不正确
        // Errors.sendError(ctx, packet.getSequence(), ErrorCode.SERVER_INTERNAL_ERROR);

//        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//
//                ctx.write(Notification.newBuilder().setText(s));
     }
}
