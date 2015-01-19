package com.lumbi.peach.app;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gabriellumbi on 14-11-26.
 * Indicates that this View/Fragment should be found using an id.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Outlet {
    int value() default -1;
}
