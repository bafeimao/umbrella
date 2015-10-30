package net.bafeimao.umbrella.support.data;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/10/30.
 */
public interface EntityParser {
    <E extends DataEntity<?>> LinkedList<E> parse(Class<E> clazz, String fileName, String sheetName);
}
