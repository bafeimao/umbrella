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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * Created by Administrator on 2015/10/27.
 */
public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    public static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        objectMapper.registerModule(module);
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);

        } catch (JsonProcessingException e) {
            throw new JsonSerializeException("将对象序列化成JSON字符串时发生解析异常", e);
        }
    }

    /**
     * 将指定的JSON格式的字符串解析成指定类型的对象
     *
     * @param <T>  返回的对象类型
     * @param json 要解析成对象的JSON格式字符串
     * @param type 指定要解析成对象的类型
     * @return 返回解析对象
     * @throws JsonDeserializeException 非检查型异常,表示解析过程中产生的错误
     */
    public static <T> T toBean(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonParseException e) {
            throw new JsonDeserializeException("解析异常", e);
        } catch (JsonMappingException e) {
            throw new JsonDeserializeException("字段映射异常", e);
        } catch (IOException e) {
            throw new JsonDeserializeException("IO异常", e);
        }
    }

    /**
     * 将指定的JSON格式的字符串解析成指定类型的对象
     *
     * @param <T>          返回的对象类型
     * @param json         要解析成对象的JSON格式字符串
     * @param valueTypeRef 指定要解析成对象的类型
     * @return 返回解析对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String json, TypeReference<?> valueTypeRef) {
        try {
            return (T) objectMapper.readValue(json, valueTypeRef);
        } catch (JsonParseException e) {
            throw new JsonDeserializeException("解析异常", e);
        } catch (JsonMappingException e) {
            throw new JsonDeserializeException("字段映射异常", e);
        } catch (IOException e) {
            throw new JsonDeserializeException("IO异常", e);
        }
    }

    public static JsonNode toJsonNode(Object json) {
        try {
            return objectMapper.readValue(json.toString(), JsonNode.class);
        } catch (IOException e) {
            LOGGER.error("50003", "将JSON字符串解析成对象时发生解析异常,原因:IO异常", e);
        }
        return null;
    }
}
