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
