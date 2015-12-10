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

package net.bafeimao.umbrella.support.data.entity;

import com.google.common.base.Converter;
import net.bafeimao.umbrella.support.data.entity.converter.*;
import org.joda.time.DateTime;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/12/9.
 *
 * @author gukaitong
 * @since 1.0
 */
public abstract class AbstractEntityParser implements EntityParser {
    private Map<Class<?>, List<Field>> fieldsByEntity = new HashMap<Class<?>, List<Field>>();
    protected Map<Class<?>, Converter<?, ?>> convertersByType = new HashMap<Class<?>, Converter<?, ?>>();

    /**
     * 注册默认的数据转换器
     */
    private void registerDefaultConverters() {
        convertersByType.put(Byte.class, new StringToNumberConverter(Byte.class));
        convertersByType.put(byte.class, new StringToNumberConverter(byte.class));

        convertersByType.put(Short.class, new StringToNumberConverter(Short.class));
        convertersByType.put(short.class, new StringToNumberConverter(short.class));

        convertersByType.put(Integer.class, new StringToNumberConverter(Integer.class));
        convertersByType.put(int.class, new StringToNumberConverter(int.class));

        convertersByType.put(Long.class, new StringToNumberConverter(Long.class));
        convertersByType.put(long.class, new StringToNumberConverter(long.class));

        convertersByType.put(Float.class, new StringToNumberConverter(Float.class));
        convertersByType.put(float.class, new StringToNumberConverter(float.class));

        convertersByType.put(Double.class, new StringToNumberConverter(Double.class));
        convertersByType.put(double.class, new StringToNumberConverter(double.class));

        convertersByType.put(Character.class, new StringToCharConverter());
        convertersByType.put(char.class, new StringToCharConverter());

        convertersByType.put(Boolean.class, new StringToBooleanConverter());
        convertersByType.put(boolean.class, new StringToBooleanConverter());

        convertersByType.put(Date.class, new StringToDateConverter());
        convertersByType.put(DateTime.class, new StringToDateTimeConverter());

        convertersByType.put(List.class, new JsonToArrayListConverter());
    }

    public AbstractEntityParser() {
        this.registerDefaultConverters();
    }

    /**
     * 根据字段类型获取类型转换器，如果相应的类型转换器不存在，则检查注解中有没有提供自定义的转换器，如果有则实例化并返回
     *
     * @param field 待赋值的字段
     * @return 返回相应的数据转换器
     */
    protected Converter<String, ?> getConverter(Field field) {
        Class<?> fieldType = field.getType();

        if (field.getName().equals("id")) {  // TODO 需要重构相关实现，这里是权宜之计
            fieldType = Long.class;
        }

        Converter<?, ?> converter = convertersByType.get(fieldType);

        if (converter == null && field.isAnnotationPresent(DataConverter.class)) {
            DataConverter anno = field.getAnnotation(DataConverter.class);
            Class<?> converterClass = anno.value();
            try {
                converter = (Converter<?, ?>) converterClass.newInstance();
                convertersByType.put(field.getType(), converter);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return (Converter<String, ?>) converter;
    }

    protected List<Field> getEntityFields(Class<?> entityClass) {
        List<Field> entityFields = fieldsByEntity.get(entityClass);

        if (entityFields == null) {
            entityFields = new ArrayList<Field>();
            fieldsByEntity.put(entityClass, entityFields);

            do { // 查找所有的Fields（包括继承的类中的字段）
                Field[] fields = entityClass.getDeclaredFields();
                for (Field field : fields) {
                    entityFields.add(field);
                }
            } while ((entityClass = entityClass.getSuperclass()) != null);
        }

        return entityFields;
    }
}
