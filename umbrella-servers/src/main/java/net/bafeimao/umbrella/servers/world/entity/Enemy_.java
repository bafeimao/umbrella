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

package net.bafeimao.umbrella.servers.world.entity;

import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.index.hash.HashIndex;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.index.radixreversed.ReversedRadixTreeIndex;
import com.googlecode.cqengine.query.option.QueryOptions;
import net.bafeimao.umbrella.servers.world.entity.enums.Quality;

/**
 * Created by bafeimao on 2015/10/28.
 */
public class Enemy_ extends Enemy {

    public static void resolveIndexesFor(IndexedCollection<Enemy> collection) {
        collection.addIndex(NavigableIndex.onAttribute(Enemy_.ID));
        collection.addIndex(ReversedRadixTreeIndex.onAttribute(Enemy_.NAME));
        collection.addIndex(HashIndex.onAttribute(Enemy_.GRADE));
    }

    /**
     * CQEngine attribute for accessing field {@code Enemy.name}.
     */
    // Note: For best performance:
    // - if this field cannot be null, replace this SimpleNullableAttribute with a SimpleAttribute
    public static final Attribute<Enemy, String> NAME = new SimpleNullableAttribute<Enemy, String>("NAME") {
        public String getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.name; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.type}.
     */
    public static final Attribute<Enemy, Integer> TYPE = new SimpleAttribute<Enemy, Integer>("TYPE") {
        public Integer getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.type; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.grade}.
     */
    public static final Attribute<Enemy, Integer> GRADE = new SimpleAttribute<Enemy, Integer>("GRADE") {
        public Integer getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.grade; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.quality}.
     */
    public static final Attribute<Enemy, Quality> QUALITY = new SimpleAttribute<Enemy, Quality>("QUALITY") {
        public Quality getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.quality; }
    };

//    /**
//     * CQEngine attribute for accessing field {@code Enemy.skills}.
//     */
//    // Note: For best performance:
//    // - if the list cannot contain null elements change true to false in the following constructor, or
//    // - if the list cannot contain null elements AND the field itself cannot be null, replace this
//    //   MultiValueNullableAttribute with a MultiValueAttribute (and change getNullableValues() to getValues())
//    public static final Attribute<Enemy, Integer> SKILLS = new MultiValueNullableAttribute<Enemy, Integer>("SKILLS", true) {
//        public Iterable<Integer> getNullableValues(Enemy enemy, QueryOptions queryOptions) { return enemy.skills; }
//    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.id}.
     */
    // Note: For best performance:
    // - if this field cannot be null, replace this SimpleNullableAttribute with a SimpleAttribute
    public static final Attribute<Enemy, Long> ID = new SimpleNullableAttribute<Enemy, Long>("ID") {
        public Long getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.getId(); }
    };
}
