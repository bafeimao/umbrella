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

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.FieldCriteria;

/**
 * Created by Administrator on 2015/10/27.
 */
public class MongoUtil {

    public static void main(String[] args) {
        Datastore ds = new DatastoreImpl(null,null,null);

        ds.createQuery(Foo.class).field("aaa").contains("xxx").get();
        ds.createQuery(Foo.class).filter("aaa", "xxx").field("aaa").contains("aaa");


        // delete
        // reload
        // 模糊查找
        // 精确查找
        // AND查找
        // OR查找
        // limit
        // sort
        // where
        // asList
        // get
        // getById
        // getByIds
        // count()
        // find(Class)
        // find(Class,property,value)
        // find(Class, property, value, offset, count)
        //
        // offset
        // offset().limit(5).asList()
        // Entity:prev(n)
        // Entity:next(n)
        //

    }

    class Foo {



        public void aa(){


        }

    }
}
