package com.eeepay.zzq.demo.cpa;

import android.support.multidex.MultiDex;

import com.eeepay.zzq.demo.lib_common.SuperApplication;

/**
 * 描述：class describe
 * 作者：zhuangzeqin
 * 时间: 2019/3/22-14:55
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class App extends SuperApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
