package com.eeepay.zzq.demo.lib_common.mvp.presenter.base;

import java.util.HashMap;

/**
   * 描述：主要作用就是将 Presenter 的实例存储起来，用的是 HashMap 实现：
   * 作者：zhuangzeqin
   * 时间: 2018/7/31-12:06
   * 邮箱：zzq@eeepay.cn
   * 备注:
   */
public class PresenterStore<P extends BasePresenter> {

    private static final String DEFAULT_KEY = "PresenterStore.DefaultKey";
    private HashMap<String, P> mMap = new HashMap<>();

    public final void put(String key, P presenter) {
        P oldPresenter = mMap.put(DEFAULT_KEY + ":" + key, presenter);
        if (oldPresenter != null) {
            oldPresenter.onCleared();
        }
    }

    public final P get(String key) {
        return mMap.get(DEFAULT_KEY + ":" + key);
    }

    public final void clear() {
        for (P presenter : mMap.values()) {
            presenter.onCleared();
        }
        mMap.clear();
    }

    public int getSize() {
        return mMap.size();
    }

    public HashMap<String, P> getMap() {
        return mMap;
    }
}
