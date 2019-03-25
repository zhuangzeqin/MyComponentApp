package com.eeepay.zzq.demo.lib_common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;

import java.util.Stack;

/**
 * 描述：Activity栈管理
 * 作者：zhuangzeqin
 * 时间: 2018/8/16-20:15
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public final class ActivityStackManager {
    private static volatile ActivityStackManager mInstance = null;
    private static Stack<Activity> activityStack;// 栈一个标准的后进先出的栈

    /**
     * ------注释说明---私有的构造函数-----
     **/
    private ActivityStackManager() {
        activityStack = new Stack<Activity>();
    }

    public static ActivityStackManager getInstance() {
        if (mInstance == null) {
            synchronized (ActivityStackManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityStackManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 压栈
     *
     * @param activity
     */
    public void push(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * 出栈
     *
     * @return
     */
    public Activity pop() {
        if (activityStack.isEmpty())
            return null;
        return activityStack.pop();
    }

    /**
     * 栈顶
     *
     * @return
     */
    public Activity peek() {
        if (activityStack.isEmpty())
            return null;
        return activityStack.peek();
    }

    /**
     * 用于异地登录或者退出时清除activity
     */
    public void clearActivity() {
        while (!activityStack.isEmpty()) {
            Activity activity = activityStack.pop();
            /*if (activity instanceof LoginActivity) {
            } else {
                activity.finish();
            }*/
        }
    }

    /**
     * 移除
     *
     * @param activity
     */
    public void remove(Activity activity) {
        if (activityStack.size() > 0 && activity == activityStack.peek())
            activityStack.pop();
        else
            activityStack.remove(activity);
    }

    /**
     * 是否存在栈
     *
     * @param activity
     * @return
     */
    public boolean contains(Activity activity) {
        return activityStack.contains(activity);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        while (!activityStack.isEmpty()) {
            activityStack.pop().finish();
        }
    }

    /**
     * 退出应用程序
     *
     * @param context
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            //清除通知栏
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }
}