package com.eeepay.zzq.demo.home.mvp.presenter.login;


import com.eeepay.zzq.demo.lib_common.mvp.presenter.interfaces.IBaseView;

/**
 * 描述：登录需要相关的View的操作
 * 作者：zhuangzeqin
 * 时间: 2018/7/31-15:55
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public interface LoginView extends IBaseView {
    void loginSuccess(String msg);//登录成功
}
