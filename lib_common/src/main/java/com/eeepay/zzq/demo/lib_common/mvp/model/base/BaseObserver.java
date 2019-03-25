package com.eeepay.zzq.demo.lib_common.mvp.model.base;

import android.text.TextUtils;

import com.eeepay.zzq.demo.lib_common.SuperApplication;
import com.eeepay.zzq.demo.lib_common.bean.Result;
import com.eeepay.zzq.demo.lib_common.mvp.model.exception.ApiException;
import com.eeepay.zzq.demo.lib_common.retrofit.RxActionManagerImpl;
import com.eeepay.zzq.demo.lib_common.utils.NetworkUtil;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 描述：抽象的订阅者Subscriber
 * 作者：zhuangzeqin
 * 时间: 2018/8/16-14:57
 * 邮箱：zzq@eeepay.cn
 * 备注:Result<T>固定的后台数据格式
 */
public abstract class BaseObserver<T> implements Observer<Result<T>> {//Result<T>固定的后台数据格式
    private String mTag;//请求标识

    public BaseObserver(String tag) {
        if (TextUtils.isEmpty(tag))
            this.mTag = String.valueOf(System.currentTimeMillis()); //默认以时间戳为请求标识
        else
            this.mTag = tag;
    }

    @Override
    public void onSubscribe(Disposable d) {
        //上层调用时只关心成功和失败即可无需关心网络情况
        if (!NetworkUtil.isNetworkAvailable(SuperApplication.getApplicationInstance().getApplicationContext())) {
            Logger.d("当前网络不可用，请检查网络情况");
            onFailure(mTag, "当前网络不可用，请检查网络情况");
            // 最好主动调用下面这一句,取消本次Subscriber订阅
            if (!d.isDisposed())
                d.dispose();
            return;
        }
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().add(mTag, d);
        }
    }

    @Override
    public void onNext(Result<T> t) {//Result<T>固定的后台数据格式
        cancel();
//        // response = "{\"status\":200,\"msg\":\"失败\",\"data\":[{\"cardNo\":\"100000000000091\",\"accNo\":\"6225768749734024\",\"accName\":\"卢紫俊\",\"bankName\":\"招商银行\",\"accNoT\":\"4024\"}]}";
        if (t != null) {
            //非200的情况
            if (!t.isSuccess()) {
                String message = t.msg;
                if (!TextUtils.isEmpty(message)) {//不为空的；提示错误信息
                    onFailure(mTag, message);
                    return;
                }
            } else if (t.isSuccess() && t.data == null) { //200的情况 并且 data 为null
                //{"status":200,"msg":"找回密码成功","data":null}
                String message = t.msg;
                if (!TextUtils.isEmpty(message)) {//不为空的；提示错误信息
                    if (t.data.getClass() == String.class)//T 为String 的情况
                    {
                        onSucess(mTag, (T) message);
                        return;
                    }
                    onFailure(mTag, message);// 如果T 为对象的
                }
            } else {
                if (t.isSuccess())//成功
                    onSucess(mTag, t.data);//返回数据
            }
        } else {
            onFailure(mTag, "暂时无法获取数据；请稍后重试");
        }
    }

    @Override
    public void onError(Throwable e) {
        RxActionManagerImpl.getInstance().remove(mTag);
        Logger.d(e.getMessage());
        if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            int code = exception.getCode();//错误码
            String msg = exception.getMsg();//错误信息
            onFailure(mTag, "code:" + code + ":" + msg);
        } else {
            onFailure(mTag, "未知错误");
        }
       /* if (e instanceof ConnectException) {
            // 服务器异常
            mErrorMsg = "连接失败";
        } else if (e instanceof NetworkErrorException) {
            mErrorMsg = "网络错误.";
        } else if (e instanceof UnknownHostException) {
            mErrorMsg = "网络不可用";
        } else if (e instanceof SocketTimeoutException) {
            // 超时
            mErrorMsg = "连接超时";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException) {
            mErrorMsg = "解析错误";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            mErrorMsg = "证书验证失败";
        } else if (e instanceof HttpException) {
            mErrorMsg = "网络错误,数据加载失败";
        } else {
            mErrorMsg = "请求失败，请稍后重试.";
        }*/
//        onFailure(mErrorMsg);
    }

    @Override
    public void onComplete() {
        //解除订阅的地方调用 disposable.dispose()

    }

    /**
     * 手动取消请求
     */
    public void cancel() {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().cancel(mTag);
        }
    }

    /**
     * 是否已经处理
     */
    public boolean isDisposed() {
        if (TextUtils.isEmpty(mTag)) {
            return true;
        }
        return RxActionManagerImpl.getInstance().isDisposed(mTag);
    }

    /**
     * 成功将结果回调出去
     **/
    public abstract void onSucess(String tag, T response);

    /**
     * 失败时将结错误信息回调出去
     **/
    public abstract void onFailure(String tag, String message);
}
