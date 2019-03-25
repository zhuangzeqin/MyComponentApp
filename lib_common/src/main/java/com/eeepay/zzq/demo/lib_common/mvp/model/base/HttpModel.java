package com.eeepay.zzq.demo.lib_common.mvp.model.base;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.eeepay.zzq.demo.lib_common.SuperApplication;
import com.eeepay.zzq.demo.lib_common.bean.JsonHead;
import com.eeepay.zzq.demo.lib_common.mvp.model.exception.ApiException;
import com.eeepay.zzq.demo.lib_common.mvp.model.rxhelper.RxHttpErrorFunctionHelper;
import com.eeepay.zzq.demo.lib_common.mvp.model.rxhelper.RxSchedulersHelper;
import com.eeepay.zzq.demo.lib_common.mvp.model.rxhelper.ServerResultFunction;
import com.eeepay.zzq.demo.lib_common.retrofit.Api;
import com.eeepay.zzq.demo.lib_common.retrofit.RetrofitManager;
import com.eeepay.zzq.demo.lib_common.retrofit.RxActionManagerImpl;
import com.eeepay.zzq.demo.lib_common.utils.ApiUtils;
import com.eeepay.zzq.demo.lib_common.utils.NetworkUtil;
import com.orhanobut.logger.Logger;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 描述：抽象出一个BaseModel
 * 数据的获取、存储、数据状态变化都将是Model层的任务
 * 作者：zhuangzeqin
 * 时间: 2018/8/20-17:52
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class HttpModel {
    private String mTag;//设置请求的tag
    private ResultCallBack mResultCallBack;//监听的返回接口
    private Api api;//APi 请求
    private Map<String, Object> mParams;//参数封装
    private String mUrl;//设置请求的url

    private HttpModel(Builder builder) {
        this.mTag = builder.tag;
        this.mUrl = builder.url;
        this.mResultCallBack = builder.resultCallBack;
        this.api = builder.api;
        this.mParams = builder.params;
    }

    public Api getApi() {
        return api;
    }

    /**
     * 获取Builder 实例
     *
     * @return
     */
    public static Builder with(@NonNull String tag) {
        return new Builder(tag);
    }

    public static class Builder {
        private String tag;//设置请求的tag
        private ResultCallBack resultCallBack;//监听的返回接口
        private Api api;
        private Map<String, Object> params;
        private String url;//设置请求的url

        public Builder(@NonNull String tag) {
            this.tag = tag;
            this.api = RetrofitManager.getInstance().getApi();
            this.params = ApiUtils.getPubParams();//公共参数的请求
        }

        public Builder setResultCallBack(@NonNull ResultCallBack resultCallBack) {
            this.resultCallBack = resultCallBack;
            return this;
        }

        public Builder setUrl(@NonNull String url) {
            this.url = url;
            return this;
        }

        public Builder setParams(Map<String, String> param) {
            // putAll可以合并两个MAP，只不过如果有相同的key那么用后面的覆盖前面的
            if (param != null && !param.isEmpty()) {
                this.params.putAll(params);
            }
            return this;
        }

        /**
         * 静态内部类调用外部类的构造函数，来构造外部类
         * Builder类中的成员函数返回Builder对象自身的另一个作用就是让它支持链式调用，使代码可读性大大增强。
         *
         * @return
         */
        public HttpModel build() {
            return new HttpModel(this);
        }
    }

    /**
     * 开始请求数据
     */
    public void start() {
        if (mResultCallBack == null)
            throw new IllegalStateException("===ResultCallBack is null,you can must implement.===");
    }

    /**
     * ------注释说明-post 请求-------
     **/
    public void reqPost(@NonNull final ResultCallBack resultCallBack) {
        getApi().post(this.mUrl, this.mParams)
                .map(new ServerResultFunction())
                .compose(RxSchedulersHelper.io_main()).
                onErrorResumeNext(new RxHttpErrorFunctionHelper<ResultCallBack>()).
                subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        //上层调用时只关心成功和失败即可无需关心网络情况
                        if (!NetworkUtil.isNetworkAvailable(SuperApplication.getApplicationInstance().getApplicationContext())) {
                            resultCallBack.onFailure(mTag, "当前网络不可用，请检查网络情况");
                            Logger.d("当前网络不可用，请检查网络情况");
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
                    public void onNext(Object o) {
                        if (o instanceof JsonHead) {
                            JsonHead jsonHead = (JsonHead) o;
                            if (!jsonHead.isSuccess()) {
                                resultCallBack.onFailure(mTag, jsonHead.getMsg());
                            } else {
                                String msg = jsonHead.getMsg();
                                resultCallBack.onSucceed(mTag, msg);
                            }
                        } else if (o instanceof String) {
                            String jsonData = (String) o;
                            Class javaBeanclass = resultCallBack.getJavaBeanclass();
                            ServerResultFunction.GSON.fromJson(jsonData, javaBeanclass);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        RxActionManagerImpl.getInstance().cancel(mTag);
                        Logger.d(e.getMessage());
                        if (e instanceof ApiException) {
                            ApiException exception = (ApiException) e;
                            int code = exception.getCode();//错误码
                            String msg = exception.getMsg();//错误信息
                            resultCallBack.onFailure(code, msg);
                        } else {
                            resultCallBack.onFailure(mTag, "未知错误");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * ------注释说明-get 请求-------
     **/
    public void reqGet() {

    }

    /**
     * ------注释说明-下载请求-------
     **/
    public void downLoad() {

    }

    /**
     * 将结果返回给外部调用者使用
     **/
    public interface ResultCallBack<T> {//外围实现这个接口的时候； 传的就是具体的Data 数据 比如 ResultCallBack<List<A>>

        void onSucceed(Object tag, T data);//成功

        void onFailure(Object tag, String msg);//失败

        @NonNull
        Class<T> getJavaBeanclass();//由子业务来告诉具体泛型类型
    }
}
