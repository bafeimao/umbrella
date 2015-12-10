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
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/10/28.
 */
public class EntityManager<T extends DataEntity<?>> {
    private final EntityParser parser = new EntityExcelParser();

    private final LoadingCache<Class<T>, IndexedCollection<T>> cache = CacheBuilder.newBuilder().build(
            new CacheLoader<Class<T>, IndexedCollection<T>>() {
                @Override
                public IndexedCollection<T> load(Class<T> entityClass) throws Exception {
                    LinkedList<T> entities = parser.parse(entityClass);

                    IndexedCollection<T> collection = new ConcurrentIndexedCollection<T>();
                    collection.addAll(entities);

                    this.resolveIndexes(entityClass, collection);

                    return collection;
                }

                private void resolveIndexes(Class<T> entityClass, IndexedCollection<T> collection) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException {
                    Class<?> extClass = Class.forName(entityClass.getName() + "_");
                    Method method = extClass.getMethod("resolveIndexesFor", IndexedCollection.class);

                    method.invoke(null, collection);
                }
            });

    public IndexedCollection<T> get(Class<T> entityClass) throws ExecutionException {
        return this.cache.get(entityClass);
    }

    public void cleanUp() {
        this.cache.cleanUp();
    }

    public void registerConverter(Class<?> dataType, Converter<Object, ?> converter) {
        parser.registerConverter(dataType, converter);
    }
}