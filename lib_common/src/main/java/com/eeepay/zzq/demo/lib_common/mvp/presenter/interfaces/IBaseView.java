package com.eeepay.zzq.demo.lib_common.mvp.presenter.interfaces;

import com.uber.autodispose.AutoDisposeConverter;

/**
 * 描述：提取显示loading界面和隐藏loading界面的方法，
 * 其他的view层接口就可以直接继承BaseView接口，不必重复的写显示和隐藏loading界面方法。
 * 作者：zhuangzeqin
 * 时间: 2017/3/20-16:39
 * 邮箱：zzq@eeepay.cn
 */
public interface IBaseView{
    /**
     * 显示loading界面
     **/
    void showLoading();

    /**
     * 隐藏loading界面
     **/
    void hideLoading();

    /**
     * 提示错误信息
     **/
    void showError(String error);

    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     *
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();
}
