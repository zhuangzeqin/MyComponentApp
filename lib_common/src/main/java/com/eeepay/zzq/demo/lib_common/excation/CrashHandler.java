package com.eeepay.zzq.demo.lib_common.excation;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



/**
 * 描述：在发生未能捕获的异常之后，保存LOG到文件，然后 调用前面定义的接口，对日志文件进行处理
 * 处理崩溃异常的类，实现UncaughtExceptionHandler接口
 * 作者：zhuangzeqin
 * 时间: 2018/7/10-15:51
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";

    private static final CrashHandler sHandler = new CrashHandler();

    private static final UncaughtExceptionHandler sDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

    private static final ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();

    private Future<?> future;

    private CrashListener mListener;

    private File mLogFile;

    private Context mContext;

    public static CrashHandler getInstance() {

        return sHandler;

    }

    /**
     * 初始化
     *
     * @param logFile
     * @param listener
     */
    public void init(Context mContext, File logFile, CrashListener listener) {
        this.mContext = mContext;
        mLogFile = logFile;
        mListener = listener;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, Log.getStackTraceString(ex));
        StringBuffer exceptionStr=new StringBuffer();
        StackTraceElement[] elements=ex.getStackTrace();
        exceptionStr.append("EXception:"+ex+"\n");
        for(StackTraceElement msg:elements){
            exceptionStr.append(msg+"\n");
        }
        /*if (BuildConfig.DEBUG) {
            ErrorActivity.show(mContext,exceptionStr.toString());
        }*/
        // TODO Auto-generated method stub
        final String buildBodyText = CrashLogUtil.buildBody(mContext);
        CrashLogUtil.writeLog(buildBodyText, mLogFile, TAG, ex.getMessage(), ex);
        future = THREAD_POOL.submit(new Runnable() {
            public void run() {
                if (mListener != null) {
                    mListener.afterSaveCrash(mLogFile);
                }
            }

            ;
        });
        if (!future.isDone()) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sDefaultHandler.uncaughtException(thread, ex);
    }

}
