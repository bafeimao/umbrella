package net.bafeimao.umbrella.support.data;

/**
 * Created by Administrator on 2015/10/28.
 */
public class FieldOps {
    private final Query<?> query;
    private final String name;

    public FieldOps(Query<?> query, String name) {
        this.query = query;
        this.name = name;
    }

    /**
     * ==================
     * 可应用于所有对象的操作
     * ==================
     */

    public Query<?> eq(Object value) {
        query.getCriteriaChain().add(new Criteria(this.name, FilterOperator.EQ, value));
        return query;
    }

    public Query<?> neq(Object value) {
        query.getCriteriaChain().add(new Criteria(this.name, FilterOperator.NEQ, value));
        return query;
    }

    /**
     * ==================
     * 数值操作
     * ==================
     */

    public Query<?> gt(Object value) {
        query.getCriteriaChain().add(new Criteria(this.name, FilterOperator.GT, value));
        return query;
    }

    public Query<?> gte(Object value) {
        query.getCriteriaChain().add(new Criteria(this.name, FilterOperator.GTE, value));
        return query;
    }

    public Query<?> lt(Object value) {
        query.getCriteriaChain().add(new Criteria(this.name, FilterOperator.LT, value));
        return query;
    }

    public Query<?> lte(Object value) {
        query.getCriteriaChain().add(new Criteria(this.name, FilterOperator.LTE, value));
        return query;
    }

    /**
     * ==================
     * 字符串操作
     * ==================
     */

    public Query<?> contains(Object value) {
        query.getCriteriaChain().add(new Criteria(this.name, FilterOperator.CONTAINS, value));
        return query;
    }

    public Query<?> endsWith(Object value) {
        query.getCriteriaChain().add(new Criteria(this.name, FilterOperator.ENDS_WITH, value));
        return query;
    }

    public Query<?> startsWith(Object value) {
        query.getCriteriaChain().add(new Criteria(this.name, FilterOperator.STARTS_WITH, value));
        return query;
    }


}