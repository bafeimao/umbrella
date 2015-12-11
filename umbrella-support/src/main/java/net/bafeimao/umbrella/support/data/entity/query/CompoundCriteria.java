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

package net.bafeimao.umbrella.support.data.entity.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bafeimao on 2015/10/29.
 */
public class CompoundCriteria extends Criteria {
    private List<Criteria> criterias = new ArrayList<Criteria>();

    public CompoundCriteria(String property, FilterOperator operator, Object value) {
        super(property, operator, value);
    }

    public CompoundCriteria(String property, FilterOperator operator, Object value, boolean or) {
        super(property, operator, value, or);
    }

    public void add(String property, FilterOperator operator, Object value) {
        criterias.add(new Criteria(property, operator, value, false));
    }

    public void add(String property, FilterOperator operator, Object value, boolean or) {
        criterias.add(new Criteria(property, operator, value, or));
    }

    @Override
    public boolean checkValid(Object entity) throws NoSuchFieldException, IllegalAccessException {
        boolean valid = true;
        for (Criteria criteria : this.criterias) {
            if (!valid && !criteria.isOr()) continue;
            valid = criteria.checkValid(entity);
        }
        return valid;
    }
}
