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
import net.bafeimao.umbrella.servers.world.entity.Enemy;
import net.bafeimao.umbrella.servers.world.entity.Enemy_;
import net.bafeimao.umbrella.servers.world.entity.enums.Quality;
import net.bafeimao.umbrella.support.data.entity.EntityManager;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.googlecode.cqengine.query.QueryFactory.*;

/**
 * Created by bafeimao on 2015/10/30.
 *
 * @author bafeimao
 * @since 1.0
 */
public class DataEntityTests {
    @Test
    public void testGetEnemies() throws ExecutionException {
        EntityManager manager = new EntityManager();
        IndexedCollection<Enemy> enemies = manager.get(Enemy.class);

        System.out.println(enemies);

        Enemy enemy = enemies.iterator().next();

        System.out.println(enemy);
        List<Integer> skills = enemy.getSkills();
        System.out.println(skills);
    }

    @Test
    public void testGetEnemiesByQuery() throws ExecutionException {
        EntityManager manager = new EntityManager();
        IndexedCollection<Enemy> enemies = manager.get(Enemy.class);
        System.out.println(enemies.size());

        Query<Enemy> query = and(greaterThan(Enemy_.GRADE, 3), equal(Enemy_.QUALITY, Quality.WHITE));
        ResultSet<Enemy> retrieved = enemies.retrieve(query);
        System.out.println(retrieved.size());
    }
}
