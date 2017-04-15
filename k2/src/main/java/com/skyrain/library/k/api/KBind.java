package com.skyrain.library.k.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称: K.
 * 创建时间: 2017/4/15.
 * 创 建 人: Var_雨中行.
 * 类 描 述: 绑定View的ID.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KBind {
    int value();
}
