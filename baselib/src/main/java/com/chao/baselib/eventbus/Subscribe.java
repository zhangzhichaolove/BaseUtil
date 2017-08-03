package com.chao.baselib.eventbus;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Chao on 2017/6/2.
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {

    //ThreadMode value() default ThreadMode.POSTING;

    ThreadMode threadMode() default ThreadMode.POSTING;
}
