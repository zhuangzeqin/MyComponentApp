package com.eeepay.zzq.demo.lib_common.log;

import android.annotation.SuppressLint;
import android.os.Environment;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 统计Web页面加载耗时<br/>
 * Created by ZJUN on 2016/8/11.
 */
public class TimeStatisticsUtils {
    private static final String TAG = "TimeStatisticsUtils";

    private static final String FILE_NAME = "WebPageLoadStatistics.txt";
    private boolean IS_AVAILABLE = false; // 记录开关

    private static TimeStatisticsUtils instance;
    private String title;
    private long startTime;
    private long endTime;

    private TimeStatisticsUtils() {

    }

    public static TimeStatisticsUtils getInstance() {
        if (instance == null) {
            synchronized (TimeStatisticsUtils.class) {
                if (instance == null) {
                    instance = new TimeStatisticsUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 设置开始时间
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * 设置结束时间
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * 记录日志
     */
    public void record(String url) {
        if (!IS_AVAILABLE) {
            return;
        }

        String info = new StringBuilder().append(title).append("\r\n").append(url)
                .append("\r\n开始时间:").append(format(startTime)).append("  结束时间:")
                .append(format(endTime)).append("  耗时:").append(endTime - startTime)
                .append("ms\r\n\r\n").toString();

        append(getFilePath() + FILE_NAME, info);
    }

    @SuppressLint("SimpleDateFormat")
    private String format(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return format.format(new Date(time));
    }

    /**
     * 追加文件
     */
    private void append(String filePath, String info) {
        RandomAccessFile accessFile;
        try {
            accessFile = new RandomAccessFile(filePath, "rw");
            long fileLength = accessFile.length(); // 获取文件的长度即字节数
            accessFile.seek(fileLength); // 将写文件指针移到文件尾
            accessFile.write(info.getBytes()); // 写数据
            accessFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取目录
     */
    private String getFilePath() {
        String path = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            path = Environment.getExternalStorageDirectory().getPath() + "/NetHospital/TimeStatistics/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        return path;
    }
}
