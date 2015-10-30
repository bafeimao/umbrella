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

package net.bafeimao.umbrella.support.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    private LinkedList<DataEntity<K>> collection = new LinkedList<DataEntity<K>>();

    public DataEntity() {
    }

    public DataEntity(LinkedList<DataEntity<K>> collection) {
        this.collection = collection;
    }

    public List<DataEntity<K>> getCollection() {
        return collection;
    }

    public DataEntity<K> next() {
        return next(1);
    }

    public DataEntity<K> next(int n) {
        if (collection != null) {
            int index = collection.indexOf(this);
            if (index < collection.size() - (n + 1)) {
                return collection.get(collection.indexOf(this) + n);
            }
        }
        return null;
    }

    public DataEntity<K> prev() {
        return next(-1);
    }

    public DataEntity<K> prev(int n) {
        return next(-n);
    }
}
