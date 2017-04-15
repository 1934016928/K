package com.skyrain.library.k.process;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KFragment;
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
 * 类 描 述: Fragment注解处理器.
 */

public class KFragmentProcess {

    private Fragment fragment;
    private Class<?> clazz;
    private Class<?> viewClazz;
    private View view;

    /**
     * 处理注解
     *
     * @param fragment
     * @return 返回Fragment的View
     */
    public View resolveFragment(Fragment fragment) {
        this.fragment = fragment;
        try {
            this.clazz = Class.forName(fragment.getClass().getName());
            bindContentView();
            bindViewID();
            bindListener();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return view;
    }

    /**
     * 绑定Fragment的Layout
     *
     * @throws ClassNotFoundException
     */
    private void bindContentView() throws ClassNotFoundException {
        KFragment fg = clazz.getAnnotation(KFragment.class);
        if (fg == null) {
            return;
        }
        int ContentView = fg.value();
        view = LayoutInflater.from(fragment.getActivity()).inflate(ContentView, null);
        viewClazz = Class.forName(view.getClass().getName());
    }

    /**
     * 绑定视图ID
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void bindViewID() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length <= 0) {
            return;
        }
        for (Field field : fields) {
            KBind bind = field.getAnnotation(KBind.class);
            if (bind != null) {
                int viewID = bind.value();
                Method method = viewClazz.getMethod("findViewById", int.class);
                method.setAccessible(true);
                field.setAccessible(true);
                Object obj = method.invoke(view, viewID);
                field.set(fragment, obj);
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
                DynamicHandler handler = new DynamicHandler(fragment);
                Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, handler);
                handler.addMethod(methodName, method);
                for (int viewId : viewIds) {
                    Method findViewByIdMethod = viewClazz.getMethod("findViewById", int.class);
                    findViewByIdMethod.setAccessible(true);
                    View widget = (View) findViewByIdMethod.invoke(view, viewId);
                    Method setEventListenerMethod = widget.getClass().getMethod(listenerSet, listenerType);
                    setEventListenerMethod.setAccessible(true);
                    setEventListenerMethod.invoke(widget, listener);
                }
            }
        }
    }
}
