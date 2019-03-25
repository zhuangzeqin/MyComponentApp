package com.eeepay.zzq.demo.lib_common.mvp.presenter.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：为了得到各个 Presenter 的实例，可以通过 @PresenterVariable 注解去获取 Presenter 实例
 * 作者：zhuangzeqin
 * 时间: 2018/7/31-12:00
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
@Target(ElementType.FIELD)//字段
@Retention(RetentionPolicy.RUNTIME)
public @interface PresenterVariable {
}
