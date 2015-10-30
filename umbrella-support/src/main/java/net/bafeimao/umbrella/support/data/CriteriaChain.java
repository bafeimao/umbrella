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
