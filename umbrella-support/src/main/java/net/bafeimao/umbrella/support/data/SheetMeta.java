package net.bafeimao.umbrella.support.data;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2015/10/30.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SheetMeta {
    String name();
    String file() default "";
}