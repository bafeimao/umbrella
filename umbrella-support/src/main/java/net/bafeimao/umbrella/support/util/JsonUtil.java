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
            throw new JsonSerializeException("���������л���JSON�ַ���ʱ���������쳣", e);
        }
    }

    /**
     * ��ָ����JSON��ʽ���ַ���������ָ�����͵Ķ���
     *
     * @param <T>  ���صĶ�������
     * @param json Ҫ�����ɶ����JSON��ʽ�ַ���
     * @param type ָ��Ҫ�����ɶ��������
     * @return ���ؽ�������
     * @throws JsonDeserializeException �Ǽ�����쳣,��ʾ���������в����Ĵ���
     */
    public static <T> T toBean(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonParseException e) {
            throw new JsonDeserializeException("�����쳣", e);
        } catch (JsonMappingException e) {
            throw new JsonDeserializeException("�ֶ�ӳ���쳣", e);
        } catch (IOException e) {
            throw new JsonDeserializeException("IO�쳣", e);
        }
    }

    /**
     * ��ָ����JSON��ʽ���ַ���������ָ�����͵Ķ���
     *
     * @param <T>          ���صĶ�������
     * @param json         Ҫ�����ɶ����JSON��ʽ�ַ���
     * @param valueTypeRef ָ��Ҫ�����ɶ��������
     * @return ���ؽ�������
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String json, TypeReference<?> valueTypeRef) {
        try {
            return (T) objectMapper.readValue(json, valueTypeRef);
        } catch (JsonParseException e) {
            throw new JsonDeserializeException("�����쳣", e);
        } catch (JsonMappingException e) {
            throw new JsonDeserializeException("�ֶ�ӳ���쳣", e);
        } catch (IOException e) {
            throw new JsonDeserializeException("IO�쳣", e);
        }
    }

    public static JsonNode toJsonNode(Object json) {
        try {
            return objectMapper.readValue(json.toString(), JsonNode.class);
        } catch (IOException e) {
            LOGGER.error("50003", "��JSON�ַ��������ɶ���ʱ���������쳣,ԭ��:IO�쳣", e);
        }
        return null;
    }
}
