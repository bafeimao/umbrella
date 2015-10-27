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