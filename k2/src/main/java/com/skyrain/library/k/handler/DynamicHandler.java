package com.skyrain.library.k.handler;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 项目名称: K.
 * 创建时间: 2017/4/15.
 * 创 建 人: Var_雨中行.
 * 类 描 述: 动态代理.
 */

public class DynamicHandler implements InvocationHandler {

    private final HashMap<String, Method> methodMap = new HashMap<>(1);
    private WeakReference<Object> handlerRef;

    /**
     * 构造方法
     *
     * @param object
     */
    public DynamicHandler(Object object) {
        this.handlerRef = new WeakReference<>(object);
    }

    /**
     * 添加处理方法
     *
     * @param name
     * @param method
     */
    public void addMethod(String name, Method method) {
        methodMap.put(name, method);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Object handler = handlerRef.get();
        if (handler != null) {
            String methodName = method.getName();
            method = methodMap.get(methodName);
            if (method != null) {
                method.setAccessible(true);
                return method.invoke(handler);
            }
        }
        return null;
    }
}
