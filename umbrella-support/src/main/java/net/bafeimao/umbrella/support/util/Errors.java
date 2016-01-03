package net.bafeimao.umbrella.support.util;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import net.bafeimao.umbrella.generated.CommonProto;
import net.bafeimao.umbrella.generated.CommonProto.ErrorCode;
import net.bafeimao.umbrella.generated.CommonProto.Packet;
import net.bafeimao.umbrella.support.server.message.HandlerContext;

/**
 * Created by ktgu on 15/11/28.
 */
public class Errors {
    public static void sendError(HandlerContext ctx, Packet packet, MessageLiteOrBuilder error){
        Packet.Builder builder = packet.toBuilder();
        if(error instanceof MessageLite.Builder){
            builder.setData(((MessageLite.Builder) error).build().toByteString());
        }else {
            builder.setData(((MessageLite) error).toByteString());
        }

        ctx.write(builder.build());
    }

    public static void sendError(HandlerContext ctx, Packet packet, ErrorCode errorCode) {
        CommonProto.Error.Builder errBuilder = CommonProto.Error.newBuilder();
        errBuilder.setCode(errorCode);
        sendError(ctx,packet, errBuilder);
    }



    public static void sendError(HandlerContext ctx, Packet packet, int errorCode){
        sendError(ctx, packet, ErrorCode.valueOf(errorCode));
    }

    public static void sendError(HandlerContext ctx, Packet packet, int errorCode, String errorText) {
        CommonProto.Error.Builder errBuilder = CommonProto.Error.newBuilder();
        errBuilder.setCode(ErrorCode.valueOf(errorCode));
        if(errorText!=null && !errorText.isEmpty()) {
            errBuilder.setText(errorText);
        }

        sendError(ctx,packet, errBuilder);
    }
}
