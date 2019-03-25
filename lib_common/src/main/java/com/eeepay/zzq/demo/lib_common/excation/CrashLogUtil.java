package com.eeepay.zzq.demo.lib_common.excation;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 描述：日志文件进行处理工具类
 * 作者：zhuangzeqin
 * 时间: 2018/7/10-15:52
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class CrashLogUtil {

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());

    /**
     * * 将日志写入文件。
     *
     * @param tag
     * @param message
     * @param tr
     */
    public static synchronized void writeLog(String buildBodyText, File logFile, String tag, String message, Throwable tr) {
        logFile.getParentFile().mkdirs();//在项目启动的时候 最好6.0 权限申请 否则有可能创建文件夹失败
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String time = timeFormat.format(Calendar.getInstance().getTime());
        synchronized (logFile) {
            FileWriter fileWriter = null;
            BufferedWriter bufdWriter = null;
            PrintWriter printWriter = null;
            try {
                fileWriter = new FileWriter(logFile, true);
                bufdWriter = new BufferedWriter(fileWriter);
                printWriter = new PrintWriter(fileWriter);
                bufdWriter.append(buildBodyText).append('\n').append(time).append(" ").append('\n').append("E").append('/').append(tag).append(" ").append(message).append('\n');
                bufdWriter.flush();
                tr.printStackTrace(printWriter);
                printWriter.flush();
                fileWriter.flush();
            } catch (IOException e) {
                closeQuietly(fileWriter);
                closeQuietly(bufdWriter);
                closeQuietly(printWriter);
            }
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ioe) {
                // ignore
            }
        }
    }

    /**
     * 获取应用程序名称
     *
     * @param context
     * @return
     */
    public static String getAPPInfoLabel(Context context) {
        return "Crash Log: " + context.getPackageManager().getApplicationLabel(context.getApplicationInfo());
    }

    /**
     * 收集设备参数信息
     *
     * @param context
     * @return
     */
    public static String buildBody(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(getAPPInfoLabel(context)).append('\n');
        sb.append("APPLICATION INFORMATION").append('\n');
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai = context.getApplicationInfo();
        sb.append("Application : ").append(pm.getApplicationLabel(ai)).append('\n');

        try {
            PackageInfo pi = pm.getPackageInfo(ai.packageName, 0);
            sb.append("Version Code: ").append(pi.versionCode).append('\n');
            sb.append("Version Name: ").append(pi.versionName).append('\n');
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        sb.append('\n').append("DEVICE INFORMATION").append('\n');
        sb.append("Board: ").append(Build.BOARD).append('\n');
        sb.append("BOOTLOADER: ").append(Build.BOOTLOADER).append('\n');
        sb.append("BRAND: ").append(Build.BRAND).append('\n');
        sb.append("CPU_ABI: ").append(Build.CPU_ABI).append('\n');
        sb.append("CPU_ABI2: ").append(Build.CPU_ABI2).append('\n');
        sb.append("DEVICE: ").append(Build.DEVICE).append('\n');
        sb.append("DISPLAY: ").append(Build.DISPLAY).append('\n');
        sb.append("FINGERPRINT: ").append(Build.FINGERPRINT).append('\n');
        sb.append("HARDWARE: ").append(Build.HARDWARE).append('\n');
        sb.append("HOST: ").append(Build.HOST).append('\n');
        sb.append("ID: ").append(Build.ID).append('\n');
        sb.append("MANUFACTURER: ").append(Build.MANUFACTURER).append('\n');
        sb.append("PRODUCT: ").append(Build.PRODUCT).append('\n');
        sb.append("TAGS: ").append(Build.TAGS).append('\n');
        sb.append("TYPE: ").append(Build.TYPE).append('\n');
        sb.append("USER: ").append(Build.USER).append('\n');

        return sb.toString();
    }
}
