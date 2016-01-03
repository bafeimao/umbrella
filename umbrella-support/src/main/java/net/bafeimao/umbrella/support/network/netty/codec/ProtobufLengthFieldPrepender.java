package net.bafeimao.umbrella.support.network.netty.codec;

import com.google.protobuf.CodedOutputStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by bafeimao.net(29283212@qq.com)
 */
public class ProtobufLengthFieldPrepender extends MessageToByteEncoder<ByteBuf> {

    @Override
    protected void encode(
            ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        int bodyLen = msg.readableBytes();
        // int headerLen = CodedOutputStream.computeRawVarint32Size(bodyLen);
        out.ensureWritable(4 + bodyLen);

        CodedOutputStream headerOut = CodedOutputStream.newInstance(new ByteBufOutputStream(out), 4);
        // headerOut.writeRawVarint32(bodyLen);
        // headerOut.writeFixed32NoTag(bodyLen); // TODO pending
        headerOut.flush();

        out.writeBytes(msg, msg.readerIndex(), bodyLen);
    }
}