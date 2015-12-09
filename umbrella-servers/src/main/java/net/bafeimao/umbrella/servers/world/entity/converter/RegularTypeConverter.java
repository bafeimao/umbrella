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

import com.google.common.base.Converter;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/12/9.
 *
 * @author gukaitong
 * @since 1.0
 */
public class RegularTypeConverter extends Converter<Object, Object> {
    private Class<?> expectedType;

    public RegularTypeConverter(Class<?> expectedType) {
        this.expectedType = expectedType;
    }

    @Override
    protected Object doForward(Object o) {
        if (o != null) {
            String s = o.toString();

            if (expectedType == String.class) {
                return s;
            }

            if (expectedType == Character.class) {
                return (Character) o;
            }

            if (expectedType == Date.class) {
                return DateTime.parse(s).toDate();
            }

            Double d = Double.valueOf(s);

            if (expectedType == Byte.class || expectedType == byte.class) {
                return d.byteValue();
            } else if (expectedType == Integer.class || expectedType == int.class) {
                return d.intValue();
            } else if (expectedType == Long.class || expectedType == long.class) {
                return d.longValue();
            } else if (expectedType == Double.class || expectedType == double.class) {
                return d;
            } else if (expectedType == Float.class || expectedType == float.class) {
                return d.floatValue();
            } else if (expectedType == Boolean.class || expectedType == boolean.class) {
                return d.intValue() == 0 ? false : true;
            }
        }
        return null;
    }

    @Override
    protected Object doBackward(Object number) {
        return null;
    }
}