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

package net.bafeimao.umbrella.servers.world.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Converter;
import net.bafeimao.umbrella.support.util.json.JsonUtil;

import java.util.List;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/12/9.
 *
 * @author gukaitong
 * @since 1.0
 */
public class GenericArrayListConverter<T> extends Converter<Object, List> {
    @Override
    protected List doForward(Object o) {
        if (o != null) {
            return JsonUtil.toBean(o.toString(), new TypeReference<List<T>>() {});
        }
        return null;
    }

    @Override
    protected Object doBackward(List ts) {
        return null;
    }
}
