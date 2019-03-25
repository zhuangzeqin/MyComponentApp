package com.eeepay.zzq.demo.lib_common.mvp.presenter.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：创建一个presenter 实例注解
 * 使用注解 @CreatePresenter 依次传入它们的 class 对象即可完成绑定
 * 作者：zhuangzeqin
 * 时间: 2018/7/31-11:52
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
@Target(ElementType.TYPE)//类，接口
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePresenter {
    Class<?>[] presenter() default {};
}
