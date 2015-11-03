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


import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import net.bafeimao.umbrella.servers.world.entity.HeroEntity;
import net.bafeimao.umbrella.support.data.FilterOperator;
import net.bafeimao.umbrella.support.data.Query;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/10/29.
 */
public class QueryTests {
    @Test
    public void test1() {
        Query<HeroEntity> query = new Query<HeroEntity>();

        // name = 'xxx'
        List<?> heros = query.filter("name", "xxx").asList();
        Assert.assertNotNull(heros);

        // name != 'xxx'
        heros = query.field("name").neq("xxx").asList();
        System.out.println(heros);

        // name == 'xxx' and age = 30
        heros = query.filter("name", "xxx").filter("age", 20).asList();
        System.out.println(heros);
        heros = query.filter("name", "xxx").and("age", FilterOperator.EQ, 20).asList();
        System.out.println(heros);
        heros = query.filter("name", "xxx").and().field("age").eq(20).asList();
        System.out.println(heros);

        // name == 'xxx' or age > 10
        heros = query.filter("name", "xxx").or("age", FilterOperator.GTE, 10).asList();
        System.out.println(heros);
        heros = query.filter("name", "xxx").or().field("age").gte(10).asList();
        System.out.println(heros);

        // offset:1, limit:1
        heros = query.offset(1).limit(2).asList();
        heros = query.offset(5).limit(5).asList();
        heros = query.offset(3).filter("gender", 1).limit(5).asList();
    }

    @Test
    public void test3() {
        String str = "name='xxx' && age=12";
        Pattern pattern = Pattern.compile(".*=*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println();
        }
        System.out.println(matcher.matches());
    }

    @Test
    public void test41() {
        Pattern pattern = Pattern.compile("(http://|https://){1}[//w//.//-/:]+");
        Matcher matcher = pattern.matcher("dsdsds<http://dsds//gfgffdfd>fdf");
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            buffer.append(matcher.group());
            buffer.append("/r/n");
            System.out.println(buffer.toString());
        }
    }

    @Test
    public void test4() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Person person = new Person("xxx");
        int count = 10000;
        String name;

        // direct invoke
        long startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            name = person.getName();
        }
        System.out.println("total = " + (System.nanoTime() - startTime));

        // field invoke
        startTime = System.nanoTime();
        Field field = person.getClass().getDeclaredField("name");
        field.setAccessible(true);
        for (int i = 0; i < count; i++) {
            name = (String) field.get(person);
        }
        System.out.println("total = " + (System.nanoTime() - startTime));

        // field invoke
        startTime = System.nanoTime();
        FieldAccess fieldAccess = FieldAccess.get(Person.class);
        for (int i = 0; i < count; i++) {
            name = (String) fieldAccess.get(person, "name");
        }
        System.out.println("total = " + (System.nanoTime() - startTime));

        // method invoke - 1
        startTime = System.nanoTime();
        Method method = person.getClass().getDeclaredMethod("getName");
        for (int i = 0; i < count; i++) {
            name = (String) method.invoke(person);
        }
        System.out.println("total = " + (System.nanoTime() - startTime));

        // method invoke - 2
        startTime = System.nanoTime();
        MethodAccess access = MethodAccess.get(Person.class);
        for (int i = 0; i < count; i++) {
            name = (String) access.invoke(person, "getName");
        }
        System.out.println("total = " + (System.nanoTime() - startTime));
    }


    class Person {
        public Person(String name) {
            this.name = name;
        }

        public String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}





