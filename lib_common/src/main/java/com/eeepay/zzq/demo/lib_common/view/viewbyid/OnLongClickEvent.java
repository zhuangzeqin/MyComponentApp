package com.eeepay.zzq.demo.lib_common.view.viewbyid;

import android.support.annotation.IdRes;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：长按按钮点击事件的注解
 * 作者：zhuangzeqin
 * 时间: 2018/8/3-17:08
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
@Target(ElementType.METHOD)//方法上
@Retention(RetentionPolicy.RUNTIME)//运行时
public @interface OnLongClickEvent {
    @IdRes int[] value() default {View.NO_ID};//数组； 因为可能有多个长按按钮的点击事件
}
