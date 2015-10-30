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
     * ��Ӧ�������ж���Ĳ���
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
     * ��ֵ����
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
     * �ַ�������
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