package net.bafeimao.umbrella.support.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
public class EntityCache {
    public <E> List<E> load(Class<E> klazz) {
        List<E> list = new ArrayList<E>();
        list.add((E) new HeroEntity("xxx", 20, 1));
        list.add((E) new HeroEntity("yyy", 30, 0));
        list.add((E) new HeroEntity("zzz", 25, 1));
        return list;
    }
}
