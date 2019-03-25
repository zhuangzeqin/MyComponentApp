package com.eeepay.zzq.demo.home.mvp.presenter.login;

import android.support.annotation.NonNull;

import com.eeepay.zzq.demo.home.mvp.model.login.LoginModel2;
import com.eeepay.zzq.demo.home.mvp.model.ModelContract;
import com.eeepay.zzq.demo.home.mvp.presenter.PresenterContract;
import com.eeepay.zzq.demo.lib_common.bean.LoginInfo;
import com.eeepay.zzq.demo.lib_common.mvp.presenter.base.BasePresenter;


/**
 * 描述 P层处理界面想要的数据；返回给V层，解耦
 * 作者：zhuangzeqin
 * 时间: 2018/7/31-15:57
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class LoginPresenter2 extends BasePresenter<LoginView> implements PresenterContract.loginPresenter{
    private final static String TAG = LoginPresenter2.class.getSimpleName();
    /**
     * 登录请求  13424230742 or 123456q
     * @param username
     * @param password
     */
    @Override
    public void login(@NonNull String username, @NonNull String password) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isAttachView())return;
        mView.showLoading();
        LoginModel2.with(mView).setTag(TAG).build().reqLonin(username, password,
                new ModelContract.IResultCallBack<LoginInfo.DataBean>() {
                    @Override
                    public void onSucess(String tag, LoginInfo.DataBean response) {
                        mView.hideLoading();
                        mView.loginSuccess(response.toString());
                    }

                    @Override
                    public void onFailure(String tag, String message) {
                        mView.hideLoading();
                        mView.showError(tag + "登录失败:" + message);
                    }
                });
    }

//    /**
//     * ------注释说明--登录------ 13424230742 or 123456q
//     **/
//    public void login(@NonNull String mobile_username, @NonNull String mobile_password) {
//        //View是否绑定 如果没有绑定，就不执行网络请求
//        if (!isAttachView()) return;
//        mView.showLoading();
//        LoginModel2.with(mView).setTag(TAG).build().reqLonin(mobile_username, mobile_password,
//                new ModelContract.IResultCallBack<LoginInfo.DataBean>() {
//                    @Override
//                    public void onSucess(String tag, LoginInfo.DataBean response) {
//                        mView.hideLoading();
//                        mView.loginSuccess(response.toString());
//                    }
//
//                    @Override
//                    public void onFailure(String tag, String message) {
//                        mView.hideLoading();
//                        mView.showError(tag + "登录失败:" + message);
//                    }
//                });
//    }
}
