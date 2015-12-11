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

package net.bafeimao.umbrella.servers.world.test;

import com.google.common.collect.Iterators;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.common.NonUniqueObjectException;
import net.bafeimao.umbrella.servers.world.entity.Enemy;
import net.bafeimao.umbrella.servers.world.entity.Enemy_;
import net.bafeimao.umbrella.servers.world.entity.enums.Quality;
import net.bafeimao.umbrella.support.data.entity.EntityManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import static com.googlecode.cqengine.query.QueryFactory.*;

/**
 * Created by bafeimao on 2015/10/30.
 *
 * @author bafeimao
 * @since 1.0
 */
public class DataEntityTests {
    private EntityManager manager = new EntityManager();

    /**
     * 按单个条件检索
     */
    @Test
    public void testUniqueResult() throws ExecutionException {
        IndexedCollection<Enemy> enemies = manager.get(Enemy.class);
        Query<Enemy> query = equal(Enemy_.ID, 10010101L);
        ResultSet<Enemy> resultSet = enemies.retrieve(query);

        Assert.assertTrue(resultSet.size() == 1);

        Enemy enemy = resultSet.uniqueResult();
        System.out.println(enemy);
    }

    /**
     * 测试当查询返回值有多个的情况下调用uniqueResult()会抛出异常
     */
    @Test(expected = NonUniqueObjectException.class)
    public void testUniqueResultWithException() throws ExecutionException {
        IndexedCollection<Enemy> enemies = manager.get(Enemy.class);
        Query<Enemy> query = greaterThanOrEqualTo(Enemy_.GRADE, 1);
        ResultSet<Enemy> resultSet = enemies.retrieve(query);

        Assert.assertTrue(resultSet.size() > 1);

        Enemy enemy = resultSet.uniqueResult();
        System.out.println(enemy);
    }

    /**
     * StartsWith检索
     */
    @Test
    public void testQueryByStartsWith() throws ExecutionException {
        IndexedCollection<Enemy> enemies = manager.get(Enemy.class);
        Query<Enemy> query = startsWith(Enemy_.NAME, "骷髅弓兵");
        ResultSet<Enemy> resultSet = enemies.retrieve(query);

        Assert.assertTrue(resultSet.size() > 0);

        // 打印出所有的满足条件的检索记录
        for (Iterator<Enemy> iterator = resultSet.iterator(); iterator.hasNext(); ) {
            System.out.println(iterator.next());
        }

        System.out.println(resultSet.size());
    }

    /**
     * 组合多个条件检索
     */
    @Test
    public void testQueryByCompoundCriterias() throws ExecutionException {
        Query<Enemy> query = and(greaterThan(Enemy_.GRADE, 3), equal(Enemy_.QUALITY, Quality.WHITE));
        ResultSet<Enemy> retrieved = manager.get(Enemy.class).retrieve(query);
        System.out.println(retrieved.size());
    }

    /**
     * 检索然后枚举打印出所有查询结果
     */
    @Test
    public void testQueryAndIterate() throws ExecutionException {
        ResultSet<Enemy> resultSet = manager.get(Enemy.class).retrieve(
                greaterThanOrEqualTo(Enemy_.GRADE, 1),
                queryOptions(orderBy(descending(Enemy_.TYPE), ascending(Enemy_.GRADE))));

        System.out.println(resultSet.size());

        Assert.assertTrue(resultSet.size() > 0);

        // Prints all retrieved enemies
        for (Iterator<Enemy> iterator = resultSet.iterator(); iterator.hasNext(); ) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 检索并按多个字段排序
     */
    @Test
    public void testQueryBySort() throws ExecutionException {
        ResultSet<Enemy> resultSet = manager.get(Enemy.class).retrieve(
                greaterThanOrEqualTo(Enemy_.GRADE, 1),
                queryOptions(orderBy(descending(Enemy_.TYPE), ascending(Enemy_.GRADE))));

        System.out.println(resultSet.size());
    }

    /**
     * in查询
     */
    @Test
    public void testQueryByIn() throws ExecutionException {
        ResultSet<Enemy> enemies = manager.get(Enemy.class).retrieve(in(Enemy_.GRADE, 1, 2, 3));
        System.out.println(enemies.size());
    }

    /**
     * 对枚举型字段进行in查询
     */
    @Test
    public void testQueryByIn2() throws ExecutionException {
        ResultSet<Enemy> enemies = manager.get(Enemy.class).retrieve(in(Enemy_.QUALITY, Quality.GREEN, Quality.ORGANGE));
        System.out.println(enemies.size());
    }

    /**
     * 对枚举型字段进行in查询
     */
    @Test
    public void testQueryByEqualOnList() throws ExecutionException {
        ResultSet<Enemy> enemies = manager.get(Enemy.class).retrieve(equal(Enemy_.SKILLS, 200001002));
        System.out.println(enemies.size());
    }

    /**
     * has操作（is not null)
     */
    @Test
    public void testQueryByHas() throws ExecutionException {
        ResultSet<Enemy> enemies = manager.get(Enemy.class).retrieve(has(Enemy_.NAME));
        System.out.println(enemies.size());
    }

    /**
     * has操作（is null)
     */
    @Test
    public void testQueryByNotHas() throws ExecutionException {
        ResultSet<Enemy> enemies = manager.get(Enemy.class).retrieve(not(has(Enemy_.NAME)));
        System.out.println(enemies.size());
    }

    /**
     * 使用原生枚举器对IndexedCollection进行枚举
     */
    @Test
    public void testQueryThenIterateNative() throws ExecutionException {
        IndexedCollection<Enemy> enemies = manager.get(Enemy.class);

        Assert.assertTrue(enemies.size() > 0);

        Enemy enemy = enemies.iterator().next();
        System.out.println(enemy);
    }

    @Test
    public void testQueryThenIterate() throws ExecutionException {
        ResultSet<Enemy> resultSet = manager.get(Enemy.class).retrieve(
                greaterThanOrEqualTo(Enemy_.GRADE, 5), queryOptions(orderBy(ascending(Enemy_.GRADE))));

        Iterator<Enemy> iterator = resultSet.iterator();

        // 取到gradle=5的Enemy
        Enemy enemy5 = iterator.next();
        System.out.println(enemy5);

        // 取到gradle=10的Enemy
        Enemy enemy10 = Iterators.get(iterator, 4);
        System.out.println(enemy10);
    }
}
