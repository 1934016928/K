package com.skyrain.library.k;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import com.skyrain.library.k.process.KActivityProcess;
import com.skyrain.library.k.process.KFragmentProcess;

/**
 * 项目名称: K.
 * 创建时间: 2017/4/15.
 * 创 建 人: Var_雨中行.
 * 类 描 述: 绑定Class.
 */

public class BindClass {

    /**
     * 绑定Activity对象
     *
     * @param activity
     */
    public static void bind(Activity activity) {
        KActivityProcess process = new KActivityProcess();
        process.resolveActivity(activity);
    }

    /**
     * 绑定Fragment对象
     *
     * @param fragment
     * @return 返回Fragment的View
     */
    public static View bind(Fragment fragment) {
        KFragmentProcess process = new KFragmentProcess();
        return process.resolveFragment(fragment);
    }
}
