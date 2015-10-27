package net.bafeimao.umbrella.support.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Administrator on 2015/10/27.
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object bean) {
        return null;
    }

    public static <T> T toBean(String json) {
        return null;
    }

    public static <T> T toBean(String json, TypeReference<T> typeReference) {
        return null;
    }
}
