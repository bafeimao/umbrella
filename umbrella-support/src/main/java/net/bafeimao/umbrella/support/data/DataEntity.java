package net.bafeimao.umbrella.support.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by bafeimao on 2015/10/28.
 */
public class DataEntity<K extends Serializable> {
    protected K id;

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    private LinkedList<DataEntity<K>> collection = new LinkedList<DataEntity<K>>();

    public DataEntity() {
    }

    public DataEntity(LinkedList<DataEntity<K>> collection) {
        this.collection = collection;
    }

    public List<DataEntity<K>> getCollection() {
        return collection;
    }

    public DataEntity<K> next() {
        return next(1);
    }

    public DataEntity<K> next(int n) {
        if (collection != null) {
            int index = collection.indexOf(this);
            if (index < collection.size() - (n + 1)) {
                return collection.get(collection.indexOf(this) + n);
            }
        }
        return null;
    }

    public DataEntity<K> prev() {
        return next(-1);
    }

    public DataEntity<K> prev(int n) {
        return next(-n);
    }
}
