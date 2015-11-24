package net.bafeimao.umbrella.support.server.message;

import io.netty.channel.ChannelHandlerContext;
import net.bafeimao.umbrella.support.server.session.Session;

/**
 * Created by ktgu on 15/11/22.
 */
public class NettyBasedChannelHandlerContext implements HandlerContext {
    private ChannelHandlerContext delegate;

    public NettyBasedChannelHandlerContext(ChannelHandlerContext ctx) {
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
