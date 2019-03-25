package com.eeepay.zzq.demo.lib_common.excation;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述： 在日志保存之后，我们可以生成一个报告，
 * 并发送给服务器。报告的方法，可以是发送到邮箱，或者http请求发送给服务器
 * 实现了生成标题和内容，设置日志路径等
 * 作者：zhuangzeqin
 * 时间: 2018/7/10-15:50
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public abstract class AbstractCrashReportHandler implements CrashListener {

    private Context mContext;

    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");


    public AbstractCrashReportHandler(Context context) {
        mContext = context;
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(context, getLogDir(context), this);
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    /**
     * 设置日志路径
     *
     * @param context
     * @return
     */
    protected File getLogDir(Context context) {

        long timestamp = System.currentTimeMillis();

        String time = formatter.format(new Date());

        String fileName = "crash-" + time + "-" + timestamp + ".log";

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            final String LOG_PATH = Environment.getExternalStorageDirectory() + File.separator + CrashLogUtil.getAPPInfoLabel(context) + File.separator;

            return new File(LOG_PATH, fileName);
        } else {
            return new File(context.getFilesDir(), fileName);//data/data/包名/files
        }
    }

    protected abstract void sendReport(String title, String body, File file);

    @Override
    public void afterSaveCrash(File file) {
        // 发送到邮箱
        sendReport(CrashLogUtil.getAPPInfoLabel(mContext), CrashLogUtil.buildBody(mContext), file);
    }

}
