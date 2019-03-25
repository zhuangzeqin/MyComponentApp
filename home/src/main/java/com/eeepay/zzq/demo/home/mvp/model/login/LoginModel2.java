package com.eeepay.zzq.demo.home.mvp.model.login;

import android.support.annotation.NonNull;

import com.eeepay.zzq.demo.home.mvp.model.ModelContract;
import com.eeepay.zzq.demo.lib_common.bean.LoginInfo;
import com.eeepay.zzq.demo.lib_common.bean.Result;
import com.eeepay.zzq.demo.lib_common.mvp.model.base.BaseModel;
import com.eeepay.zzq.demo.lib_common.mvp.model.base.BaseObserver;
import com.eeepay.zzq.demo.lib_common.mvp.model.rxhelper.RxHttpErrorFunctionHelper;
import com.eeepay.zzq.demo.lib_common.mvp.model.rxhelper.RxSchedulersHelper;
import com.eeepay.zzq.demo.lib_common.mvp.presenter.interfaces.IBaseView;
import com.eeepay.zzq.demo.lib_common.utils.Md5;
import com.eeepay.zzq.demo.lib_common.utils.Utils;

/**
 * 描述：登录的model
 * 数据的获取、存储、数据状态变化都将是Model层的任务
 * m层将数据 回到给P层
 * 作者：zhuangzeqin
 * 时间: 2018/8/20-14:50
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public final class LoginModel2 extends BaseModel implements ModelContract.ILoginModel2<LoginInfo.DataBean> {
    private String mTag;//设置请求的tag
    private IBaseView mView;//自动销毁的转换器;//自动销毁的转换器
    private LoginModel2(Builder builder) {
        if (builder == null) return;
        this.mTag = builder.tag;
        this.mView = builder.view;
    }
    /**
     * 获取Builder 实例
     *
     * @return
     */
    public static Builder with(@NonNull IBaseView view) {
        return new Builder(view);
    }
    public static class Builder {
        private String tag;//设置请求的tag
        private IBaseView view;//自动销毁的转换器

        public Builder(@NonNull IBaseView view) {
            this.view = view;
        }

        //自动销毁的转换器
        public Builder setTag(@NonNull String tag) {
            this.tag = tag;
            return this;
        }

        /**
         * 静态内部类调用外部类的构造函数，来构造外部类
         * Builder类中的成员函数返回Builder对象自身的另一个作用就是让它支持链式调用，使代码可读性大大增强。
         *
         * @return
         */
        public LoginModel2 build() {
            return new LoginModel2(this);
        }
    }
    @Override
    public void reqLonin(@NonNull String mobile_username, @NonNull String mobile_password, @NonNull final ModelContract.IResultCallBack<LoginInfo.DataBean> resultCallBack) {
        if (mView == null)
            throw new IllegalStateException("=== reqLonin mView is null===");
        if (resultCallBack == null)
            throw new IllegalStateException("=== resultCallBack is null===");
        /** ------注释说明-参数的封装------- **/
        mParams.put("apiTag", "merLogin");
        mParams.put("mobile_username", mobile_username);
        mParams.put("mobile_password", Md5.encode(mobile_password));
        mParams.put("parentMer", "Y");//myInfo 接口新增请求参数 parentMer ,值为Y时,表示需要查询引路人信息,引路人的响应数据在 parMerInfo 参数中.
        mParams.put("getMerCapas", "Y");//add by zhuangzeqin 二〇一八年七月三日 10:48:00 myInfo 接口新增请求参数 新增getMerCapas参数,传Y,则会查询身份级别信息
        getApi().reqLonin(Utils.getUUID(),mParams).
                compose(RxSchedulersHelper.<Result<LoginInfo.DataBean>>io_main()).
                onErrorResumeNext(new RxHttpErrorFunctionHelper<Result<LoginInfo.DataBean>>()).
                as(mView.<Result<LoginInfo.DataBean>>bindAutoDispose()).//自动销毁的转换器
                subscribe(new BaseObserver<LoginInfo.DataBean>(this.mTag) {//订阅需要传tag
            @Override
            public void onSucess(String tag,LoginInfo.DataBean response) {
                resultCallBack.onSucess(tag,response);//m层将数据 回到给P层
            }

            @Override
            public void onFailure(String tag,String message) {
                resultCallBack.onFailure(tag,message);
            }
        });
    }
}
