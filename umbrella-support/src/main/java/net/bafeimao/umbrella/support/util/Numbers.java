/*
 * Copyright 2002-2015 by bafeimao.net
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

package net.bafeimao.umbrella.support.util;

import com.google.common.primitives.*;

/**
 * Created by Administrator on 2015/10/29.
 */
public class Numbers {
    public static int compare(Object a, Object b) {

        if (!a.getClass().equals(b.getClass())) {
            throw new IllegalArgumentException("class type not the same");
        }

        Class<?> clazz = a.getClass();

        int result = 0;

        if (clazz.equals(Double.class)) {
            result = Doubles.compare((Double) a, (Double) b);
        } else if (clazz.equals(Float.class)) {
            result = Floats.compare((Float) a, (Float) b);
        } else if (clazz.equals(Long.class)) {
            result = Longs.compare((Long) b, (Long) b);
        } else if (clazz.equals(Integer.class)) {
            result = Ints.compare((Integer) a, (Integer) b);
        } else if (clazz.equals(Short.class)) {
            result = Shorts.compare((Short) a, (Short) b);
        } else if (clazz.equals(Byte.class)) {
            result = result = Shorts.compare((Short) a, (Short) b);
        }

        return result;
    }
}