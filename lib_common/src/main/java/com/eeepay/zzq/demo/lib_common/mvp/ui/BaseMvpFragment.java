package com.eeepay.zzq.demo.lib_common.mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.eeepay.zzq.demo.lib_common.R;
import com.eeepay.zzq.demo.lib_common.mvp.presenter.base.BasePresenter;
import com.eeepay.zzq.demo.lib_common.mvp.presenter.base.PresenterDispatch;
import com.eeepay.zzq.demo.lib_common.mvp.presenter.base.PresenterProviders;
import com.eeepay.zzq.demo.lib_common.mvp.presenter.interfaces.IBaseView;
import com.eeepay.zzq.demo.lib_common.utils.ToastUtils;
import com.eeepay.zzq.demo.lib_common.view.dialog.DialogHelper;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：抽象的 MvpFragment 基类
 * 作者：zhuangzeqin
 * 时间: 2018/7/31-14:29
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends RxFragment implements IBaseView {
    protected View mRootView;
    protected LayoutInflater inflater;
    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;
    //标志位 fragment是否可见
    protected boolean isVisible;
    protected Context mContext;
    protected Activity mActivity;
    protected Bundle mBundle;
    /**
     * 解析用到的注解以及完成绑定和解绑 View 等一些公共的 Presenter 操作
     **/
    private PresenterProviders mPresenterProviders;
    /**
     * presenter 的调度器
     **/
    private PresenterDispatch mPresenterDispatch;
    /**
     * 等待加载的对话框
     */
    private ProgressDialog _waitDialog;
    /**
     * ------注释说明--butterknife注解框架------
     **/
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBundle = getArguments();//参数Bunlder
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null)
                parent.removeView(mRootView);
        } else {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            mUnbinder = ButterKnife.bind(this, mRootView);
            mActivity = getActivity();
            mContext = mActivity;
            this.inflater = inflater;
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);

        mPresenterDispatch.attachView(getActivity(), this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
        isPrepared = true;
        init();
        lazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        if (mUnbinder != null)
            try {
                mUnbinder.unbind();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        mPresenterDispatch.detachView();
        this.mActivity = null;
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenterDispatch.onSaveInstanceState(outState);
    }

    /**
     * 绑定生命周期 防止MVP内存泄漏
     *
     * @param <T>
     * @return
     */
    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }

    /**
     * 当只有一个presenter 的时候可以直接通过这个方法获取
     **/
    protected P getPresenter() {
        return mPresenterProviders.getPresenter(0);
    }

    public PresenterProviders getPresenterProviders() {
        return mPresenterProviders;
    }

    /**
     * 获取布局
     */
    public abstract @LayoutRes
    int getLayoutId();

    /**
     * 初始化
     */
    protected abstract void init();

    public View getViewById(@IdRes int id) {
        if (mRootView == null) throw new NullPointerException("mRootView is null.");
        View view = mRootView.findViewById(id);
        return view;
    }

    /**
     * 懒加载
     */
    private void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        lazyLoadData();
        isPrepared = false;
    }

    /**
     * 懒加载
     */
    @CallSuper
    protected void lazyLoadData() {

    }

    /**
     * 可见的情况下
     */
    protected void onVisible() {
        lazyLoad();//懒加载
    }

    /**
     * 不可见的情况下
     */
    protected void onInvisible() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @UiThread
    @Override
    public void showLoading() {
        showWaitDialog(getString(R.string.loading));
    }

    @UiThread
    public ProgressDialog showWaitDialog(String message) {
        if (mActivity != null && !mActivity.isFinishing() && isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelper.getProgressDialog(mActivity, message);
                _waitDialog.setCanceledOnTouchOutside(false);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    @UiThread
    @Override
    public void hideLoading() {
        if (mActivity != null && !mActivity.isFinishing() && _waitDialog != null && isVisible && _waitDialog.isShowing()) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @UiThread
    @Override
    public void showError(String error) {
        if (isVisible) {
            ToastUtils.setBgColor(Color.parseColor("#ff7e00"));
            ToastUtils.setMsgColor(Color.parseColor("#ffffff"));
            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
            ToastUtils.showShort(error);
        }
    }

    /**
     * 在支持路由的页面上添加注解(必选)
     * 这里的路径需要注意的是至少需要有两级，/xx/xx
     *
     * @param path
     * @Route(path = "/test/activity")
     */
    protected void goActivity(@NonNull final String path) {
        //应用内简单的跳转带转场动画(常规方式)
        goActivity(path, null, -1);
    }

    /**
     * 路由跳转Activity 并携带Bundle,flag
     *
     * @param path
     * @param bundle
     */
    protected void goActivity(@NonNull final String path, @Nullable Bundle bundle, int flag) {
        //应用内简单的跳转带转场动画(常规方式)
        ARouter.getInstance().build(path).withFlags(flag).
                withTransition(R.anim.eposp_push_left_in, R.anim.eposp_push_left_out).
                with(bundle).
                navigation();
    }

    /**
     * 路由跳转Activity flag
     *
     * @param path
     * @param flag
     */
    protected void goActivity(@NonNull final String path, int flag) {
        //应用内简单的跳转带转场动画(常规方式)
        goActivity(path, null, flag);

    }

    /**
     * 路由跳转Activity 并携带Bundle
     *
     * @param path
     * @param bundle
     */
    protected void goActivity(@NonNull final String path, @Nullable Bundle bundle) {
        //应用内简单的跳转带转场动画(常规方式)
        goActivity(path, bundle, -1);
    }

    /**
     * 类似 Intent 的
     * startActivityForResult 跳转
     *
     * @param path
     * @param bundle
     * @param requestCode
     */

    protected void goActivityForResult(@NonNull final String path, @Nullable Bundle bundle, int requestCode) {
        ARouter.getInstance().build(path).with(bundle).withTransition(R.anim.eposp_push_left_in, R.anim.eposp_push_left_out).navigation(mActivity, requestCode);
    }

    /**
     * ------startActivityForResult 跳转--------
     **/
    protected void goActivityForResult(@NonNull final String path, int requestCode) {
        goActivityForResult(path, null, requestCode);
    }

    /**
     * 清理中间activity，跳转到某activity，这方法较常用
     *
     * @param path
     */
    protected void goTopActivity(@NonNull final String path, @Nullable Bundle bundle) {
        goActivity(path, bundle, Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    /**
     * 清理中间activity，跳转到某activity，这方法较常用
     *
     * @param path
     */
    protected void goTopActivity(@NonNull final String path) {
        goTopActivity(path, null);
    }
}
