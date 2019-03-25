package com.eeepay.zzq.demo.lib_common.view.viewbyid;

import android.support.annotation.IdRes;
import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：代替findViewById()方法的注解
 * 作者：zhuangzeqin
 * 时间: 2018/8/3-16:37
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
@Target(value = ElementType.FIELD)//标识这个注解类只能注解字段
@Retention(RetentionPolicy.RUNTIME)//表示这个注解在运行时期起作用
@Documented
public @interface ViewById {
    @IdRes int value() default View.NO_ID;
}
