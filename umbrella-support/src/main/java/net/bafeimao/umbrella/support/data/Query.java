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
        int index = 0;
        for (T entity : entities) {
            if (offset > 0 && index < offset) {
                index++;
                continue;
            }

            if (limit > 0 && limit <= retList.size())
                break;

            try {
                if (isSatisfied(entity)) {
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

    public T get() {
        List<T> entities = cache.load(entityClass);
        int index = 0;
        for (T entity : entities) {
            if (offset > 0 && index < offset) {
                index++;
                continue;
            }

            try {
                if (isSatisfied(entity)) {
                    return entity;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private boolean isSatisfied(DataEntity entity) throws IllegalAccessException, NoSuchFieldException {
        boolean valid = true;
        for (Criteria criteria : getCriteriaChain().getCriterias()) {
            if (!valid && !criteria.isOr()) continue;
            valid = criteria.checkValid(entity);
        }
        return valid;
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

    private int offset = 0;

    public Query<T> offset(int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("Negative offset.");
        }
        this.offset = offset;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    private int limit = -1;

    public Query<T> limit(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Negative limit.");
        }
        this.limit = limit;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public FieldOps field(String name) {
        return new FieldOps(this, name);
    }

    public CriteriaChain getCriteriaChain() {
        this.ensureCriteriaChain();
        return criteriaChain;
    }

    public Query<T> where(String condition) {
        throw new UnsupportedOperationException("where");
    }
}
