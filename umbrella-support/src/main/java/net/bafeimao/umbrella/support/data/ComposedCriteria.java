package net.bafeimao.umbrella.support.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bafeimao on 2015/10/29.
 */
public class ComposedCriteria extends Criteria {
    private List<Criteria> criterias = new ArrayList<Criteria>();

    public ComposedCriteria(String property, FilterOperator operator, Object value) {
        super(property, operator, value);
    }

    public ComposedCriteria(String property, FilterOperator operator, Object value, boolean or) {
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
