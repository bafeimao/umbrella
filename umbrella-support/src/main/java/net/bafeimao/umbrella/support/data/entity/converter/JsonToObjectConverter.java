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

package net.bafeimao.umbrella.support.data.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Converter;
import com.google.common.base.Strings;
import net.bafeimao.umbrella.support.util.json.JsonUtil;

import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/12/9.
 *
 * @author gukaitong
 * @since 1.0
 */
public class JsonToObjectConverter<T> extends Converter<String, Object> {

    private Class<T> resultType;

    public JsonToObjectConverter() {
        this.resultType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    @Nullable
    protected T doForward(String s) {
        if (!Strings.isNullOrEmpty(s)) {
            return JsonUtil.toBean(s, resultType);
        }
        return null;
    }

    @Override
    @Nullable
    protected String doBackward(Object object) {
        return JsonUtil.toJson(object);
    }

    public static void main(String[] args) {
        JsonToObjectConverter converter = new JsonToObjectConverter<Integer>();


        Object m = converter.doForward("10");
        System.out.println(m);
    }
}