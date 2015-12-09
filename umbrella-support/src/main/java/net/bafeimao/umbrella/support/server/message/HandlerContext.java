package net.bafeimao.umbrella.support.server.message;

import net.bafeimao.umbrella.support.server.session.Session;

/**
 * Created by ktgu on 15/11/22.
 */
public interface HandlerContext {
    Session session();

    void write(Object object);
}
