package net.bafeimao.umbrella.support.util;

import net.bafeimao.umbrella.generated.CommonProto.ErrorCode;
import net.bafeimao.umbrella.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.server.message.HandlerContext;

/**
 * Created by ktgu on 15/11/28.
 */
public class Errors {

    public static void sendError(HandlerContext ctx, Packet packet, ErrorCode errorCode) {
        sendError(ctx,packet, errorCode.getNumber());
    }

    public static void sendError(HandlerContext ctx, Packet packet, int errorCode){

        // TODO 根据errorCode和当前应用程序使用语言取出errorText
        String errorText = null;

        sendError(ctx, packet, errorCode, errorText);
    }

    public static void sendError(HandlerContext ctx, Packet packet, int errorCode, String errorText) {
        Packet.Builder builder = packet.toBuilder();
        builder.setError(errorCode);
        if(errorText != null) {
            builder.setErrorText(errorText);
        }
        ctx.write(builder.build());
    }
}
