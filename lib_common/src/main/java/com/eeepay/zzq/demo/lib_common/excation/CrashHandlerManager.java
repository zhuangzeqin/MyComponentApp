package com.eeepay.zzq.demo.lib_common.excation;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * 描述：异常管理类
 * 作者：zhuangzeqin
 * 时间: 2018/7/10-15:52
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class CrashHandlerManager extends AbstractCrashReportHandler {

    private static final String TAG = "CrashHandlerManager";

    public CrashHandlerManager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void sendReport(String title, String body, File file) {
        // TODO Auto-generated method stub
        Log.i(TAG, title);

        Log.i(TAG, body);

        Log.i(TAG, file.getAbsolutePath());


    }

}
