package com.eeepay.zzq.demo.chat.mvp.model;

import android.support.annotation.NonNull;

import com.eeepay.zzq.demo.lib_common.bean.LoginInfo;
import com.eeepay.zzq.demo.lib_common.bean.Result;

import java.util.Map;

import io.reactivex.Observable;


/**
 * 描述：避免model 创建太多的接口，就统一写在这里；
 * 作者：zhuangzeqin
 * 时间: 2018/8/20-14:44
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public interface ModelContract {
    //登录相关的Model 接口
    interface ILoginModel {
        Observable<Result<LoginInfo.DataBean>> reqLonin(@NonNull String uuid, @NonNull Map<String, Object> request);
    }
    //登录model 接口
    interface ILoginModel2<T> {
        void reqLonin(@NonNull String mobile_username, @NonNull String mobile_password, @NonNull final IResultCallBack<T> resultCallBack);
    }
    //注册相关的Model 接口
    interface IRegisterModel<T> {
        Observable<Result<LoginInfo.DataBean>> reqRegister(@NonNull String username, @NonNull String password, @NonNull IResultCallBack<T> resultCallBack);
    }
    /**
     * 定义CallBack将结果返回给外部调用者使用
     **/
    interface IResultCallBack<T> {
        /**
         * 成功将结果回调出去
         **/
        void onSucess(String tag, T response);

        /**
         * 失败时将结错误信息回调出去
         **/
        void onFailure(String tag, String message);
    }
}
