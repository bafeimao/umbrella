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
import com.googlecode.cqengine.attribute.MultiValueNullableAttribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.query.option.QueryOptions;
import net.bafeimao.umbrella.servers.world.entity.enums.Quality;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by bafeimao on 2015/10/28.
 */
public class Enemy_ extends Enemy {

    public static void resolveIndexesFor(IndexedCollection<Enemy> collection) {
        collection.addIndex(NavigableIndex.onAttribute(Enemy_.ID));
//        collection.addIndex(ReversedRadixTreeIndex.onAttribute(Enemy_.NAME));
//        collection.addIndex(HashIndex.onAttribute(Enemy_.GRADE));
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
    public static final Attribute<Enemy, Byte> TYPE = new SimpleAttribute<Enemy, Byte>("TYPE") {
        public Byte getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.type; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.grade}.
     */
    public static final Attribute<Enemy, Integer> GRADE = new SimpleAttribute<Enemy, Integer>("GRADE") {
        public Integer getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.grade; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.hp}.
     */
    public static final Attribute<Enemy, Long> HP = new SimpleAttribute<Enemy, Long>("HP") {
        public Long getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.hp; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.quality}.
     */
    // Note: For best performance:
    // - if this field cannot be null, replace this SimpleNullableAttribute with a SimpleAttribute
    public static final Attribute<Enemy, Quality> QUALITY = new SimpleNullableAttribute<Enemy, Quality>("QUALITY") {
        public Quality getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.quality; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.boss}.
     */
    public static final Attribute<Enemy, Boolean> BOSS = new SimpleAttribute<Enemy, Boolean>("BOSS") {
        public Boolean getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.boss; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.char1}.
     */
    public static final Attribute<Enemy, Character> CHAR1 = new SimpleAttribute<Enemy, Character>("CHAR1") {
        public Character getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.char1; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.byte1}.
     */
    public static final Attribute<Enemy, Byte> BYTE1 = new SimpleAttribute<Enemy, Byte>("BYTE1") {
        public Byte getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.byte1; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.short1}.
     */
    public static final Attribute<Enemy, Short> SHORT1 = new SimpleAttribute<Enemy, Short>("SHORT1") {
        public Short getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.short1; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.int1}.
     */
    public static final Attribute<Enemy, Integer> INT1 = new SimpleAttribute<Enemy, Integer>("INT1") {
        public Integer getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.int1; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.long1}.
     */
    public static final Attribute<Enemy, Long> LONG1 = new SimpleAttribute<Enemy, Long>("LONG1") {
        public Long getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.long1; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.float1}.
     */
    public static final Attribute<Enemy, Float> FLOAT1 = new SimpleAttribute<Enemy, Float>("FLOAT1") {
        public Float getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.float1; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.double1}.
     */
    public static final Attribute<Enemy, Double> DOUBLE1 = new SimpleAttribute<Enemy, Double>("DOUBLE1") {
        public Double getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.double1; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.boolean1}.
     */
    public static final Attribute<Enemy, Boolean> BOOLEAN1 = new SimpleAttribute<Enemy, Boolean>("BOOLEAN1") {
        public Boolean getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.boolean1; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.date1}.
     */
    // Note: For best performance:
    // - if this field cannot be null, replace this SimpleNullableAttribute with a SimpleAttribute
    public static final Attribute<Enemy, Date> DATE1 = new SimpleNullableAttribute<Enemy, Date>("DATE1") {
        public Date getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.date1; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.dateTime1}.
     */
    // Note: For best performance:
    // - if this field cannot be null, replace this SimpleNullableAttribute with a SimpleAttribute
    public static final Attribute<Enemy, DateTime> DATE_TIME1 = new SimpleNullableAttribute<Enemy, DateTime>("DATE_TIME1") {
        public DateTime getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.dateTime1; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.skills}.
     */
    // Note: For best performance:
    // - if the list cannot contain null elements change true to false in the following constructor, or
    // - if the list cannot contain null elements AND the field itself cannot be null, replace this
    //   MultiValueNullableAttribute with a MultiValueAttribute (and change getNullableValues() to getValues())
    public static final Attribute<Enemy, Integer> SKILLS = new MultiValueNullableAttribute<Enemy, Integer>("SKILLS", true) {
        public Iterable<Integer> getNullableValues(Enemy enemy, QueryOptions queryOptions) { return enemy.skills; }
    };

    /**
     * CQEngine attribute for accessing field {@code Enemy.id}.
     */
    // Note: For best performance:
    // - if this field cannot be null, replace this SimpleNullableAttribute with a SimpleAttribute
    public static final Attribute<Enemy, Long> ID = new SimpleNullableAttribute<Enemy, Long>("ID") {
        public Long getValue(Enemy enemy, QueryOptions queryOptions) { return enemy.getId(); }
    };
}
