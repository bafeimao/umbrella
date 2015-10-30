package net.bafeimao.umbrella.support.data;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/28.
 */
public class EntityCache {
    public <E> List<E> load(Class<E> clazz) {
        List<E> list = new ArrayList<E>();
        list.add((E) new HeroEntity("aaa", 5, 1));
        list.add((E) new HeroEntity("bbb", 10, 0));
        list.add((E) new HeroEntity("ccc", 15, 1));
        list.add((E) new HeroEntity("xxx", 20, 1));
        list.add((E) new HeroEntity("yyy", 25, 1));
        list.add((E) new HeroEntity("zzz", 30, 1));
        return list;
    }
}
