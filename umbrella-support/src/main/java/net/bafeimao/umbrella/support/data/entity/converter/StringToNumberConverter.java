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

/**
 * Created by gukaitong(29283212@qq.com) on 2015/12/9.
 *
 * @author gukaitong
 * @since 1.0
 */
public class StringToNumberConverter extends Converter<String, Number> {
    private Class<?> resultType;

    public StringToNumberConverter(Class<?> resultType) {
        this.resultType = resultType;
    }

    public static void main(String[] args) {
        new StringToNumberConverter(Integer.class);
    }

    private void checkRange(Double d, long min, long max) {
        if (d > max) {
            throw new NumberFormatException(String.format("Value out of range. Value:\"d%\" Radix:10", max));
        }

        if (d < min) {
            throw new NumberFormatException(String.format("Value out of range. Value:\"d%\" Radix:10", min));
        }
    }

    @Override
    @Nullable
    // TODO Check range
    protected Number doForward(String s) {
        Double d = Double.valueOf(s);

        if (Strings.isNullOrEmpty(s)) {
            d = 0d;
        }

        if (resultType == Byte.class || resultType == byte.class) {
            return Byte.valueOf(d.byteValue());
        } else if (resultType == Short.class || resultType == short.class) {
            return Short.valueOf(d.shortValue());
        } else if (resultType == Integer.class || resultType == int.class) {
            return Integer.valueOf(d.intValue());
        } else if (resultType == Long.class || resultType == long.class) {
            return Long.valueOf(d.longValue());
        } else if (resultType == Float.class || resultType == float.class) {
            return Float.valueOf(d.floatValue());
        }

        return d;
    }

    @Override
    @Nullable
    protected String doBackward(Number t) {
        //  TODO 待完善，根据类型不同返回不同的默认值
        return t == null ? null : t.toString();
    }
}