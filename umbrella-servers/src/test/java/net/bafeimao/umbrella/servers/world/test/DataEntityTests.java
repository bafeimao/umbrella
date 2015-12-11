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

    @Test
    public void testGetAllEnemies() throws ExecutionException {
        IndexedCollection<Enemy> enemies = manager.get(Enemy.class);

        Assert.assertTrue(enemies.size() > 0);

        System.out.println(enemies.size());
    }

    @Test
    public void testGetEnemyByNativeCollection() throws ExecutionException {
        IndexedCollection<Enemy> enemies = manager.get(Enemy.class);

        Assert.assertTrue(enemies.size() > 0);

        Enemy enemy = enemies.iterator().next();
        System.out.println(enemy);
    }

    @Test
    public void testQueryEnemyById() throws ExecutionException {
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
    public void testUsageForUniqueResultWithException() throws ExecutionException {
        IndexedCollection<Enemy> enemies = manager.get(Enemy.class);
        Query<Enemy> query = greaterThanOrEqualTo(Enemy_.GRADE, 1);
        ResultSet<Enemy> resultSet = enemies.retrieve(query);

        Assert.assertTrue(resultSet.size() > 1);

        Enemy enemy = resultSet.uniqueResult();
        System.out.println(enemy);
    }

    @Test
    public void testQueryEnemyByStartsWith() throws ExecutionException {
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

    @Test
    public void testQueryEnemyByCompoundCriterias() throws ExecutionException {
        IndexedCollection<Enemy> enemies = manager.get(Enemy.class);
        Query<Enemy> query = and(greaterThan(Enemy_.GRADE, 3), equal(Enemy_.QUALITY, Quality.WHITE));
        ResultSet<Enemy> retrieved = enemies.retrieve(query);
        System.out.println(retrieved.size());
    }

    @Test
    public void testQueryEnemyBySort() throws ExecutionException {
        IndexedCollection<Enemy> enemies = manager.get(Enemy.class);
        Query<Enemy> query = greaterThanOrEqualTo(Enemy_.GRADE, 1);
        ResultSet<Enemy> resultSet = enemies.retrieve(query,
                queryOptions(orderBy(descending(Enemy_.TYPE), ascending(Enemy_.GRADE))));

        Assert.assertTrue(resultSet.size() > 0);

        // Prints all retrieved enemies
        for (Iterator<Enemy> iterator = resultSet.iterator(); iterator.hasNext(); ) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testEnemyByNext() throws ExecutionException {
        IndexedCollection<Enemy> enemies = manager.get(Enemy.class);

        Assert.assertTrue(enemies.size() > 0);

        Enemy enemy = enemies.iterator().next();
        System.out.println(enemy);

        // 从Enemy关联的Collection中找出下一条记录
        //Enemy nextEnemy = enemy.next();

//        System.out.println(nextEnemy);
    }


}
