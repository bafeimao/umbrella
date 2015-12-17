package net.bafeimao.umbrella.support.network.netty.handler;

import com.google.common.base.Preconditions;
import io.netty.channel.ChannelHandlerContext;
import net.bafeimao.umbrella.support.server.message.HandlerContext;
import net.bafeimao.umbrella.support.server.session.Session;

/**
 * Created by ktgu on 15/11/22.
 */
public class NettyChannelHandlerContext implements HandlerContext {
    private ChannelHandlerContext delegate;

    public NettyChannelHandlerContext(ChannelHandlerContext ctx) {
        Preconditions.checkNotNull(ctx);
        delegate = ctx;
    }

    @Override
    public Session session() {
        return null;
    }

    @Override
    public void write(Object object) {
        delegate.write(object);
    }
}
