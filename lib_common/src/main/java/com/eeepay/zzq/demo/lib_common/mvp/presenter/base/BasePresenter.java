package com.eeepay.zzq.demo.lib_common.mvp.presenter.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 描述：通用的presenter来为我们添加view的绑定与销毁。
 * 作者：zhuangzeqin
 * 时间: 2018/7/31-11:47
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class BasePresenter<V> {
//    protected Map<String, Object> mParams;
    /**
     * 上下文Contenxt
     **/
    protected Context mContext;
    /**
     * 通用view
     **/
    protected V mView;

    protected void onCleared() {

    }

    public BasePresenter() {
//        this.mParams = ApiUtils.getPubParams();//公共参数的请求
    }

    /**
     * 添加view的绑定方法
     **/
    public void attachView(Context context, V view) {
        this.mContext = context;
        this.mView = view;
    }

    /**
     * 加入一个销毁view的方法
     **/
    public void detachView() {
        this.mView = null;
    }

    /**
     * 是否已经绑定view的方法
     **/
    public boolean isAttachView() {
        return this.mView != null;
    }

    /**
     * 创建presenter空方法
     **/
    public void onCreatePresenter(@Nullable Bundle savedState) {

    }

    /**
     * 销毁presenter方法
     **/
    public void onDestroyPresenter() {
        this.mContext = null;
        detachView();
    }

    public void onSaveInstanceState(Bundle outState) {

    }
}
