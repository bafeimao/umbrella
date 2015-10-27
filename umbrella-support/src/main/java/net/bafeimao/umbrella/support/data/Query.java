package net.bafeimao.umbrella.support.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bafeimao on 2015/10/27.
 */
public class Query<T extends DataEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query.class);
    private final EntityCache cache;
    private CriteriaChain criteriaChain;
    private Class<T> entityClass;

    public Query() {
        this.cache = new EntityCache();
    }

    public List<T> asList() {
        List<T> entities = cache.load(entityClass);
        List<T> retList = new ArrayList<T>();

        for (T entity : entities) {
            try {
                if (checkValid(entity)) {
                    retList.add(entity);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return retList;
    }

    private boolean checkValid(DataEntity entity) throws IllegalAccessException, NoSuchFieldException {
        boolean valid = true;
        for (Criteria criteria : this.criteriaChain.getCriterias()) {
            if (!valid && !criteria.isOr()) continue;
            valid = criteria.checkValid(entity);
        }
        return valid;
    }


    public T get() {
        List<T> entities = this.asList();
        if (entities != null && entities.size() > 0) {
            return entities.get(0);
        }
        return null;
    }

    public T getById(long id) {
        return null;
    }

    public List<T> getByIds() {
        return null;
    }

    public Query<T> filter(String property, Object value) {
        ensureCriteriaChain();
        criteriaChain.add(new Criteria(property, FilterOperator.EQ, value));
        return this;
    }

    public CriteriaChain and() {
        this.ensureCriteriaChain().setNextOr(false);
        return criteriaChain;
    }

    public Query<T> and(String property, FilterOperator op, Object value) {
        this.ensureCriteriaChain();
        this.criteriaChain.add(new Criteria(property, op, value));
        return this;
    }

    public CriteriaChain or() {
        this.ensureCriteriaChain().setNextOr(true);
        return this.criteriaChain;
    }

    public Query<T> or(String property, FilterOperator op, Object value) {
        this.ensureCriteriaChain().setNextOr(true);
        this.criteriaChain.add(new Criteria(property, op, value));
        return this;
    }

    private CriteriaChain ensureCriteriaChain() {
        if (this.criteriaChain == null) {
            criteriaChain = new CriteriaChain(this);
        }
        return criteriaChain;
    }

    public Query<T> offset(int n) {
        return this;
    }

    public Query<T> limit(int n) {
        return this;
    }

    public FieldOps field(String name) {
        return new FieldOps(this, name);
    }

    public static void main(String[] args) {
        Query<HeroEntity> query = new Query<HeroEntity>();

        List<?> heros = query.filter("name", "xxx").and().field("age").gt(10).asList();

//        query.filter("name", "foo").filter("age", 30);
//
//        query.field("name").contains("xxx").get();
//
//        query.field("name").endsWith("yyy")
//                .and()
//                .field("age").greaterThan(30)
//                .or()
//                .field("gender").eq(1)
//                .asList();
//
//        query.field("age").greaterThan(1).asList();
//
//        query.field("name").contains("aaa").and().field("age").greaterThan(30).asList();
    }

    public CriteriaChain getCriteriaChain() {
        this.ensureCriteriaChain();
        return criteriaChain;
    }
}
