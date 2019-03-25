package com.eeepay.zzq.demo.lib_common;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.eeepay.zzq.demo.lib_common.excation.CrashHandlerManager;
import com.eeepay.zzq.demo.lib_common.retrofit.RetrofitManager;
import com.eeepay.zzq.demo.lib_common.utils.ApiUtils;
import com.eeepay.zzq.demo.lib_common.utils.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.simple.spiderman.SpiderMan;

/**
 * 描述：app 基类
 * 作者：zhuangzeqin
 * 时间: 2019/3/21-10:39
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class SuperApplication extends Application {
    private static SuperApplication mInstance = null;

    //static 代码段可以防止内存泄露
    static {
        initSmartRefreshLayoutBuilder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Utils.init(mInstance);
        MultiDex.install(this);
        //初始化ARouter
        initARouter();
        //初始化异常处理
        initCrashHandler();
        //初始化Logger
        initLogger();
        //***************可以根据不同切换的地址；进行设置*******************
        RetrofitManager.getInstance().initCustomConfig(ApiUtils.SERVICE_ADDRESS);
    }

    /**
     * 初始化ARouter路由框架
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值  设置app字体不随系统字体大小而变化
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    /**
     * 初始化异常处理
     */
    private void initCrashHandler() {
        SpiderMan.init(this).setTheme(R.style.SpiderManTheme_Dark);
        new CrashHandlerManager(getApplicationContext());
    }

    /**
     * 初始化Logger
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(500)        // (Optional) Skips some method invokes in stack trace. Default 5
//        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("MyTag")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                //true将打印日志消息，false将忽略它。这里设置Debug 模式下开始打印日志
                return BuildConfig.DEBUG;
            }
        });
    }

    /**
     * 设置全局的Header构建器,全局的Footer构建器
     */
    private static void initSmartRefreshLayoutBuilder() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(mInstance.getApplicationContext()).setSpinnerStyle(SpinnerStyle.Translate);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(mInstance.getApplicationContext()).setSpinnerStyle(SpinnerStyle.Translate);//.setDrawableSize(20)
            }
        });
    }

    /**
     * 获取APP的实例
     *
     * @return
     */
    public static SuperApplication getApplicationInstance() {
        return mInstance;
    }
}
