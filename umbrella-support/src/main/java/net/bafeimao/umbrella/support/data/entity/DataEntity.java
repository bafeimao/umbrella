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

package net.bafeimao.umbrella.support.data.entity;

import net.bafeimao.umbrella.annotation.IgnoreParsing;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by bafeimao on 2015/10/28.
 */
public class DataEntity<K extends Serializable> {
    protected K id;

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    @IgnoreParsing
    private LinkedList<DataEntity<?>> collection = new LinkedList<DataEntity<?>>();

//    public DataEntity() {
//    }
//
//    public DataEntity(LinkedList<DataEntity<K>> collection) {
//        this.collection = collection;
//    }

    public void setCollection(LinkedList<? extends DataEntity<?>> collection) {
        this.collection.addAll(collection);
    }

    public <E> E next() {
        return (E) next(1);
    }

    public <E> E next(int n) {
        if (collection != null) {
            int index = collection.indexOf(this);
            if (index < collection.size() - (n + 1)) {
                return (E) collection.get(collection.indexOf(this) + n);
            }
        }
        return null;
    }

    public <E> E prev() {
        return next(-1);
    }

    public <E> E prev(int n) {
        return next(-n);
    }
}
