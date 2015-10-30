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

import java.util.LinkedList;

/**
 * Created by bafeimao on 2015/10/28.
 */
public class CriteriaChain {
    private final Query query;
    private LinkedList<Criteria> criterias = new LinkedList<Criteria>();
    private boolean nextOr = false;

    public CriteriaChain(Query query) {
        this.query = query;
    }

    public FieldOps field(String name) {
        return new FieldOps(query, name);
    }

    public void add(Criteria criteria) {
        criteria.setOr(nextOr);
        criterias.add(criteria);
        this.nextOr = false;
    }

    public Query getQuery() {
        return query;
    }

    public LinkedList<Criteria> getCriterias() {
        return criterias;
    }

    public boolean isNextOr() {
        return nextOr;
    }

    public void setNextOr(boolean nextOr) {
        this.nextOr = nextOr;
    }
}
