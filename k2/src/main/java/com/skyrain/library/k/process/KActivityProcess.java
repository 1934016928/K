package com.skyrain.library.k.process;

import android.app.Activity;
import android.view.View;

import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.skyrain.library.k.handler.DynamicHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 项目名称: K.
 * 创建时间: 2017/4/15.
 * 创 建 人: Var_雨中行.
 * 类 描 述: Activity注解处理器.
 */

public class KActivityProcess {

    private Activity activity;
    private Class<?> clazz;

    /**
     * 注解处理
     *
     * @param activity
     */
    public void resolveActivity(Activity activity) {
        this.activity = activity;
        try {
            this.clazz = (Class) Class.forName(activity.getClass().getName());
            bindContentView();
            bindViewID();
            bindListener();
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定Layout资源
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void bindContentView() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        KActivity ka = clazz.getAnnotation(KActivity.class);
        if (ka == null) {
            return;
        }
        int layoutId = ka.value();
        Method method = clazz.getMethod("setContentView", int.class);
        method.setAccessible(true);
        method.invoke(activity, layoutId);
        method.setAccessible(false);
    }

    /**
     * 绑定View的ID
     *
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private void bindViewID() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length <= 0) {
            return;
        }
        for (Field field : fields) {
            KBind bind = field.getAnnotation(KBind.class);
            if (bind != null) {
                Method method = clazz.getMethod("findViewById", int.class);
                int viewID = bind.value();
                method.setAccessible(true);
                field.setAccessible(true);
                Object obj = method.invoke(activity, viewID);
                field.set(activity, obj);
                field.setAccessible(false);
            }
        }
    }

    /**
     * 绑定View的事件监听
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    private void bindListener() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(KListener.class)) {
                KListener onClick = method.getAnnotation(KListener.class);
                int[] viewIds = onClick.value();
                String listenerSet = onClick.listenerSet();
                Class<?> listenerType = onClick.listenerType();
                String methodName = onClick.listenerMethod();
                DynamicHandler handler = new DynamicHandler(activity);
                Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, handler);
                handler.addMethod(methodName, method);
                for (int viewId : viewIds) {
                    Method findViewByIdMethod = clazz.getMethod("findViewById", int.class);
                    findViewByIdMethod.setAccessible(true);
                    View view = (View) findViewByIdMethod.invoke(activity, viewId);
                    Method setEventListenerMethod = view.getClass().getMethod(listenerSet, listenerType);
                    setEventListenerMethod.setAccessible(true);
                    setEventListenerMethod.invoke(view, listener);
                }
            }
        }
    }
}
