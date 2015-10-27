package net.bafeimao.umbrella.support.test;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import net.bafeimao.umbrella.support.data.FilterOperator;
import net.bafeimao.umbrella.support.data.HeroEntity;
import net.bafeimao.umbrella.support.data.Query;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2015/10/29.
 */
public class QueryTests {
    @Test
    public void test1() {
        Query<HeroEntity> query = new Query<HeroEntity>();
        List<?> heros = null;

        // name = 'xxx'
        heros = query.filter("name", "xxx").asList();
        Assert.assertNotNull(heros);

        // name != 'xxx'
        heros = query.field("name").neq("xxx").asList();
        System.out.println(heros);

        // name == 'xxx' and age = 30
        heros = query.filter("name", "xxx").and("age", FilterOperator.EQ, 30).asList();
        System.out.println(heros);
        heros = query.filter("name", "xxx").and().field("age").eq(30).asList();
        System.out.println(heros);

        // name == 'xxx' or age > 10
        heros = query.filter("name", "xxx").or("age", FilterOperator.GTE, 10).asList();
        System.out.println(heros);
        heros = query.filter("name", "xxx").or().field("age").gte(10).asList();
        System.out.println(heros);
    }

    @Test
    public void test2() {
        Query<HeroEntity> query = new Query<HeroEntity>();

        List<?> heros = query.filter("name", "xxx").or().field("age").gt(10).asList();
        System.out.println(heros);
    }

    @Test
    public void test3() {
        String condition = "name='xxx' && age > 10 and gender=1";
        String[] arr = condition.split("(and)|(&&)");
        System.out.println(arr);
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





