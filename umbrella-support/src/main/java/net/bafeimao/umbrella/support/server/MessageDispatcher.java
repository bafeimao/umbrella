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

package net.bafeimao.umbrella.support.server;


import net.bafeimao.umbrella.support.generated.CommonProto;

import java.lang.reflect.Method;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/10.
 *
 * @author gukaitong
 * @since 1.0
 */
public class MessageDispatcher {
    public void dispatch(CommonProto.Packet packet) {
        long id = packet.getId();
        int type = packet.getType();


        Method handler = getDispatcher(1);
    }

    public Method getDispatcher(int type) {
        try {
            return this.getClass().getMethod("aaa");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
