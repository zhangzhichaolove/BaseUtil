package com.chao.baselib.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 框架内监听注解
 *
 * @author Chao
 * @datetime: 2017年1月21日 下午5:48:04 Chao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Click {

    /**
     * 需要监听的控件id
     *
     * @return
     */
    int value();

    /**
     * 事件
     *
     * @return
     */
    int type() default 0;

}
