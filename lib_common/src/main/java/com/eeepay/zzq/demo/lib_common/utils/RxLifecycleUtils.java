package com.eeepay.zzq.demo.lib_common.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

/**
 * 描述：class describe
 * 作者：zhuangzeqin
 * 时间: 2018/9/3-19:06
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class RxLifecycleUtils {
    private RxLifecycleUtils() {
        throw new IllegalStateException("Can't instance the RxLifecycleUtils");

    }

    public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner lifecycleOwner) {
        return AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner)
        );
    }public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner lifecycleOwner,Lifecycle.Event event) {
        return AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner,event)
        );
    }
}
