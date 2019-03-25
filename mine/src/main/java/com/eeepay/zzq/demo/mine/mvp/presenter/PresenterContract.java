package com.eeepay.zzq.demo.mine.mvp.presenter;

import android.support.annotation.NonNull;

/**
 * 描述：P层需要的接口协议
 * 作者：zhuangzeqin
 * 时间: 2018/10/8-11:21
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public interface PresenterContract {
    /**
     * ------注释说明--登录------
     **/
    interface loginPresenter {
        void login(@NonNull String username, @NonNull String password);
    }

    /**
     * ------注释说明--注册------
     **/
    interface registerPresenter {
        void register();
    }
    /** ------add by zhuangzeqin 2018年10月8日11:32:21 注释说明 后面如果有其它的接口协议继续添加即可-------- **/
}
