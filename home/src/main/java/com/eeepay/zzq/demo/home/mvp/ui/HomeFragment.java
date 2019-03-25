package com.eeepay.zzq.demo.home.mvp.ui;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eeepay.zzq.demo.home.R;
import com.eeepay.zzq.demo.home.mvp.presenter.login.LoginPresenter;
import com.eeepay.zzq.demo.home.mvp.presenter.login.LoginPresenter2;
import com.eeepay.zzq.demo.home.mvp.presenter.login.LoginView;
import com.eeepay.zzq.demo.lib_common.mvp.presenter.base.CreatePresenter;
import com.eeepay.zzq.demo.lib_common.mvp.presenter.base.PresenterVariable;
import com.eeepay.zzq.demo.lib_common.mvp.ui.BaseMvpFragment;
import com.eeepay.zzq.demo.lib_common.utils.ToastUtils;

/**
 * 描述：class describe
 * 作者：zhuangzeqin
 * 时间: 2019/3/21-14:48
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
@Route(path = "/home/HomeFragment")
@CreatePresenter(presenter = {
        LoginPresenter.class,
        LoginPresenter2.class})
public class HomeFragment extends BaseMvpFragment implements LoginView ,View.OnClickListener{
    @PresenterVariable//采用注解无需new
    private LoginPresenter mLoginPresenter;
    @PresenterVariable//采用注解无需new
    private LoginPresenter2 mLoginPresenter2;
//    @BindView(R2.id.btn_login)
    Button btn_Login;
//    @BindView(R2.id.btn_register)
    Button btn_Register;
//    @BindView(R2.id.btn_tes)
    Button btnTes;
/*//    @BindView(R.id.clroot)
    ConstraintLayout clRoot;
//    @BindView(R.id.ll_mRootView)
    LinearLayout llRootView;*/

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        btn_Login = (Button) getViewById(R.id.btn_login);
        btn_Register = (Button) getViewById(R.id.btn_register);
        btnTes = (Button) getViewById(R.id.btn_tes);

        btn_Login.setOnClickListener(this);
        btn_Register.setOnClickListener(this);
        btnTes.setOnClickListener(this);
    }

    /*@OnClick({R2.id.btn_login, R2.id.btn_register, R2.id.btn_tes})
    public void onclickView(View view) {
        int i = view.getId();
        if (i == R.id.btn_login) {//所有子View
            mLoginPresenter2.login("13424230742", "123456q");//调用登录请求
//                mLoginPresenter.login("13424230742", "123456q");//调用登录请求

        } else if (i == R.id.btn_register) {//调用注册请求

        } else if (i == R.id.btn_tes) {
            String path = "/chat/MainActivity";
            goActivity(path);

        }
    }*/

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        mLoginPresenter2.login("13424230742", "123456q");//调用登录请求
    }

    @UiThread
    @Override
    public void loginSuccess(String msg) {
        ToastUtils.showLong(msg);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_login) {//所有子View
            mLoginPresenter2.login("13424230742", "123456q");//调用登录请求
//                mLoginPresenter.login("13424230742", "123456q");//调用登录请求

        } else if (i == R.id.btn_register) {//调用注册请求

        } else if (i == R.id.btn_tes) {
            String path = "/chat/MainActivity";
            goActivity(path);

        }
    }
}
