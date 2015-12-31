/*
 * Copyright 2002-2015 by bafeimao.net, The umbrella Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.bafeimao.umbrella.servers.world.test;

import net.bafeimao.umbrella.servers.world.handler.LoginRequestHandler;
import net.bafeimao.umbrella.servers.world.handler.RoleMessageService;
import net.bafeimao.umbrella.generated.CommonProto.MessageType;
import net.bafeimao.umbrella.support.server.message.HandlerExecutionException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/12/8.
 *
 * @author gukaitong
 * @since 1.0
 */
public class CodeGenTests {

    @Test
    public void testGenDelegatedMessageHandler() throws IOException {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        ve.init();

        Template t = ve.getTemplate("delegate-message-handler.vm");
        VelocityContext ctx = new VelocityContext();

        String classSimpleName = RoleMessageService.class.getSimpleName();
        MessageType messageType = MessageType.LOGIN_REQUEST;
        String handlerNamePrefix = getHandlerNamePrefix(messageType);

        ctx.put("packageName", RoleMessageService.class.getPackage().getName());
        ctx.put("classSimpleName", classSimpleName);
        ctx.put("messageType", messageType);
        ctx.put("handlerNamePrefix", handlerNamePrefix);

        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);
        System.out.println(sw.toString());

//
        String fileName = "D:\\IdeaProjects\\umbrella\\umbrella-servers\\src\\main\\java\\net\\bafeimao\\umbrella\\servers\\world\\handler\\" + handlerNamePrefix + "Handler.java";
//        FileWriter writer = new FileWriter(fileName);
//        writer.append(sw.toString());
//        writer.write(sw.toS        tring());

        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(sw.toString().getBytes());
    }

    @Test
    public void testInvokeDelegatedMessageHandler() throws HandlerExecutionException {
        LoginRequestHandler handler = new LoginRequestHandler();
        handler.setDelegate(getDelegate(RoleMessageService.class));
        handler.handle(null, null);
    }

    private Map<Class<?>, Object> delegatesByType = new HashMap<Class<?>, Object>();

    private <T> T getDelegate(Class<T> expectedType) {
        Object matched = delegatesByType.get(expectedType);
        if (matched == null) {
            try {
                matched = expectedType.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            delegatesByType.put(expectedType, matched);
        }
        return (T) matched;
    }

    private String getHandlerNamePrefix(MessageType messageType) {
        String s = messageType.toString();
        String[] parts = s.split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase());
        }
        return sb.toString();
    }
}
