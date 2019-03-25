package com.eeepay.zzq.demo.lib_common.view.viewbyid;

import android.support.annotation.IdRes;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：item点击事件的注解
 * 作者：zhuangzeqin
 * 时间: 2018/8/3-17:08
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
@Target(ElementType.METHOD)//方法上
@Retention(RetentionPolicy.RUNTIME)//运行时
//@Repeatable()参数指明接收的注解class,可以实现重复注解
//@Inherited //添加可继承元注解
public @interface OnItemClickEvent {
    @IdRes int[] value() default {View.NO_ID};//数组； 因为可能有多个长按按钮的点击事件
}
