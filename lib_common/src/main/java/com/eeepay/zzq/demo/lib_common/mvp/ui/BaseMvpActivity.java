package com.eeepay.zzq.demo.lib_common.mvp.ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.eeepay.zzq.demo.lib_common.R;
import com.eeepay.zzq.demo.lib_common.mvp.presenter.base.BasePresenter;
import com.eeepay.zzq.demo.lib_common.mvp.presenter.base.PresenterDispatch;
import com.eeepay.zzq.demo.lib_common.mvp.presenter.base.PresenterProviders;
import com.eeepay.zzq.demo.lib_common.mvp.presenter.interfaces.IBaseView;
import com.eeepay.zzq.demo.lib_common.utils.ActivityStackManager;
import com.eeepay.zzq.demo.lib_common.utils.ToastUtils;
import com.eeepay.zzq.demo.lib_common.utils.VirturlUtil;
import com.eeepay.zzq.demo.lib_common.view.dialog.DialogHelper;
import com.eeepay.zzq.demo.lib_common.view.viewbyid.InjectUtils;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 描述：抽象出MVP的基类后面定义的泛型 P 主要是为了一个 Presenter 的时候
 * 方便使用 getPresenter() 方法时用到的
 * 作者：zhuangzeqin
 * 时间: 2018/7/31-14:16
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends RxAppCompatActivity implements IBaseView {
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();
    /***获取Toolbar**/
    protected Toolbar mToolbar;
    /***获取title**/
    private TextView mTitle;
    /***获取右边的标题**/
    protected TextView mRightTitle;
    /***获取返回的图标**/
    protected TextView mBack;
    /**
     * ------presenter 提供者 解析用到的注解以及完成绑定和解绑 View 等一些公共的 Presenter 操作--------
     **/
    private PresenterProviders mPresenterProviders;
    /**
     * ------presenter 的调度器--------
     **/
    private PresenterDispatch mPresenterDispatch;
    /***等待加载的对话框**/
    private ProgressDialog mWaitDialog;
    /***上下文对象**/
    protected Context mContext;
    /** ------注释说明--View注解框架------ **/
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** 设置添加到ActivityManager 管理类**/
        ActivityStackManager.getInstance().push(this);
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /** ------不可横屏幕-------- **/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getContentView());
        mUnbinder = ButterKnife.bind(this);
        /** ------注释说明--自定义注解；如果不需要可以注释掉------ **/
        InjectUtils.getInstance().inject(this);
        //解决华为虚拟键冲突遮挡底部按钮
        VirturlUtil.assistActivity(findViewById(android.R.id.content));
        /** ------注释说明-------- **/
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);
        //绑View的操作
        mPresenterDispatch.attachView(this, this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
        /** ------注释说明-------- **/
        /** 设置状态栏问题颜色（黑/白） **/
        setStatusBarTextLight(true);
        /** 设置初始化 ToolBar **/
        initToolBar(setTitle());
        /** 沉浸式状态栏背景默认设置**/
        initBgBar(R.color.unify_txt_color_ff7e00);
        /** 设置初始化 View **/
        initView();
        /** 设置点击事件操作**/
        eventOnClick();
        /** 初始化数据**/
        initData();
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder!=null)
            mUnbinder.unbind();
        //解绑View的操作
        mPresenterDispatch.detachView();
        mPresenterDispatch.onDestroyPresenter();
        ActivityStackManager.getInstance().remove(this);
        super.onDestroy();

    }

    /**
     * 重写onRequestPermissionsResult，用于接受请求结果
     *
     * @param requestCode  请求的唯一code
     * @param permissions  一些列的请求权限
     * @param grantResults 用户授权的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenterDispatch.onSaveInstanceState(outState);
    }

    //后面定义的泛型 P 主要是为了一个 Presenter 的时候使用 getPresenter() 方法时用到的，index =0,数组只有1个
    protected P getPresenter() {
        return mPresenterProviders.getPresenter(0);
    }

    /**
     * ------获取 presenter 提供者--------
     **/
    public PresenterProviders getPresenterProviders() {
        return mPresenterProviders;
    }

    /**
     * 显示等待对话框
     **/
    @Override
    public void showLoading() {
        showWaitDialog(getString(R.string.loading));
    }

    /**
     * 关闭显示等待对话框
     **/
    @UiThread
    @Override
    public void hideLoading() {
        if (!isFinishing() && mWaitDialog != null) {
            try {
                mWaitDialog.dismiss();
                mWaitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 显示错误信息
     **/
    @UiThread
    @Override
    public void showError(String error) {
        Logger.d(TAG, error);
        ToastUtils.setBgColor(Color.parseColor("#ff7e00"));
        ToastUtils.setMsgColor(Color.parseColor("#ffffff"));
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort(error);
    }

    /**
     * 显示等待对话框
     **/
    @UiThread
    public ProgressDialog showWaitDialog(String message) {
        if (!isFinishing()) {
            if (mWaitDialog == null) {
                mWaitDialog = DialogHelper.getProgressDialog(this, message);
                mWaitDialog.setCanceledOnTouchOutside(false);
            }
            if (mWaitDialog != null) {
                mWaitDialog.setMessage(message);
                mWaitDialog.show();
            }
            return mWaitDialog;
        }
        return null;
    }

    /**
     * 设置状态栏问题颜色（黑/白）
     *
     * @param isLight true:白色 false:黑色
     */
    protected void setStatusBarTextLight(boolean isLight) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | (isLight ? View.SYSTEM_UI_FLAG_LAYOUT_STABLE : View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
        }
    }

    /**
     * 初始化 ToolBar
     *
     * @param title
     */
    protected void initToolBar(String title) {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("");
            mTitle = findViewById(R.id.tv_title);
            if (!TextUtils.isEmpty(title) && mTitle != null) {
                mTitle.setText(title);
            } else {
                /** ------当没有标题的时候； 隐藏掉toolbar-------- **/
                mToolbar.setVisibility(View.GONE);
                setStatusBarTextLight(false);
            }
            mBack = findViewById(R.id.iv_back);
            mRightTitle = findViewById(R.id.tv_rightTitle);
            if (mBack != null) {
                mBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        } else {
            Logger.d(TAG, "mToolbar 控件为空");
        }
    }

    /**
     * 沉浸式状态栏背景设置
     **/
    @CallSuper
    protected void initBgBar(@ColorRes int id) {
        //4.4 以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setHeight(mToolbar);
        }
        if (mToolbar != null)
            mToolbar.setBackgroundColor(getResources().getColor(id));//颜色设置
//        mToolbar.setBackgroundResource(R.mipmap.icon_shopping_goods_bg);//图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//api >= 21
//            getWindow().setNavigationBarColor(Color.parseColor("#1bb5d7"));
            getWindow().setNavigationBarColor(mToolbar.getVisibility() == View.VISIBLE ? getResources().getColor(id) : 0);
            //getWindow().setNavigationBarColor(Color.BLUE);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
    }

    /**
     * 获取控件对象的方法
     **/
    public <T extends View> T getViewById(@IdRes int id) {
        View view = findViewById(id);
        return (T) view;
    }

    /**
     * 以ToolBar为例,动态设置ToolBar的高度,并且设置一个padding，top为状态栏的高度，
     *
     * @param view
     */
    private void setHeight(View view) {
        if (view == null) return;
        // 获取actionbar的高度
        TypedArray actionbarSizeTypedArray = obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize
        });
        float height = actionbarSizeTypedArray.getDimension(0, 0);
        // ToolBar的top值
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        double statusBarHeight = getStatusBarHeight(this);
        lp.height = (int) (statusBarHeight + height);
        view.setPadding(0, (int) statusBarHeight, 0, 0);
        mToolbar.setLayoutParams(lp);
    }

    /**
     * ------获取系统状态栏高度--------
     **/
    private double getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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
        ARouter.getInstance().build(path).with(bundle).withTransition(R.anim.eposp_push_left_in, R.anim.eposp_push_left_out).navigation(this, requestCode);
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
     * 布局id
     **/
    protected abstract int getContentView();

    /**
     * 抽象的设置的标题的方法 子类实现
     **/
    protected abstract String setTitle();

    /**
     * 初始化操作
     **/
    protected abstract void initView();

    /**
     * 初始化点击事件操作
     **/
    protected abstract void eventOnClick();

    /**
     * 初始化数据
     **/
    protected abstract void initData();
}
