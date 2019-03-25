package com.eeepay.zzq.demo.lib_common.mvp.presenter.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
   * 描述：为了隔离开 PresenterProviders 和 PresenterStore，免得耦合。
   * 作者：zhuangzeqin
   * 时间: 2018/7/31-14:06
   * 邮箱：zzq@eeepay.cn
   * 备注:
   */
public class PresenterDispatch {
    private PresenterProviders mProviders;

    public PresenterDispatch(PresenterProviders providers) {
        mProviders = providers;
    }

    public <P extends BasePresenter> void attachView(Context context, Object view) {
        PresenterStore store = mProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.attachView(context, view);
            }
        }
    }

    public <P extends BasePresenter> void detachView() {
        PresenterStore store = mProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.detachView();
            }
        }
    }

    public <P extends BasePresenter> void onCreatePresenter(@Nullable Bundle savedState) {
        PresenterStore store = mProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.onCreatePresenter(savedState);
            }
        }
    }

    public <P extends BasePresenter> void onDestroyPresenter() {
        PresenterStore store = mProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.onDestroyPresenter();
            }
        }
    }

    public <P extends BasePresenter> void onSaveInstanceState(Bundle outState) {
        PresenterStore store = mProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.onSaveInstanceState(outState);
            }
        }
    }
}
