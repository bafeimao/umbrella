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

import com.google.common.base.Converter;
import com.google.common.base.Strings;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/12/9.
 *
 * @author gukaitong
 * @since 1.0
 */
public class StringToDateConverter extends Converter<String, Date> {
    @SuppressWarnings("deprecation")
    @Override
    protected Date doForward(String s) {
        if (!Strings.isNullOrEmpty(s)) {
            // TODO 使用Joda库来解决此类问题
//            return new DateTime(s).toDate();
            return new Date(s);
        }
        return null;
    }

    @Override
    @Nullable
    protected String doBackward(Date value) {
        return value == null ? null : value.toString();
    }
}