package com.skyrain.library.k.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称: K.
 * 创建时间: 2017/4/15.
 * 创 建 人: Var_雨中行.
 * 类 描 述: Fragment的注解.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface KFragment {
    int value();
}
