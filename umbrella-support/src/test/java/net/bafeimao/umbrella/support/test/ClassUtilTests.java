package net.bafeimao.umbrella.support.test;

import net.bafeimao.umbrella.support.util.ClassUtils;
import org.junit.Test;

import java.util.Set;

/**
 * Created by ktgu on 15/11/28.
 */
public class ClassUtilTests {
    @Test
    public void testPackageScan() {
        Set<Class<?>> classes = ClassUtils.getClasses("net");
        System.out.println(classes);
    }
}
