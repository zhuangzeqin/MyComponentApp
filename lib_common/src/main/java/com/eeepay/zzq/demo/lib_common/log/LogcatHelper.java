package com.eeepay.zzq.demo.lib_common.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;


/**
 * 日志类输出保存类
 * Created by zhuangzeqin on 2016/5/27.
 */
public class LogcatHelper {
	
	private final static String TAG = "LogcatHelper";
	
	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * log存放的目录
	 **/
	private String LOG_PATH = null;
	/**
	 * 标识是否为调试
	 */
	private final boolean DEBUG = true;
	private final boolean WRITE = true;
	/**
	 * 日志保存10天
	 */
	private final int SAVEFILEDAYS =10;
	/**
	 * 文件日志记录时间文件名
	 **/
	private final String FILELOG_SAVE_TIME = "fileLog_save_time";
	/**
	 * 文件日志记录时间key名
	 **/
	private final String KEY_FILELOGNAME = "fileLogTime";

	/**
	 * 文件日志记录时间存放在本地
	 **/
	private SharedPreferences sharedPreferences = null;

	/**
	 * 单例对象
	 */
	private static LogcatHelper instance = null;

	/**
	 * 存储数据日志
	 * Vector 是同步的
	 */
	private Vector<String> buffer = new Vector<String>();

	private static Context mContext;  

	/**
	 * 构造函数
	 */
	private LogcatHelper() {
		
		init();
	}

	/**
	 * 获取日志存放路径
	 *
	 * @return
	 */
	@SuppressLint("NewApi")
	private String getLog_Path(Context context) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			/**获取缓存路径:/sdcard/Android/data/<application package>/cache  */
			cachePath = context.getExternalCacheDir().getPath() + File.separator + "szxys#LogCacheDir";
		} else {
			/**获取缓存路径:/data/data/<application package>/cache   */
			cachePath = context.getCacheDir().getPath() + File.separator + "szxys#LogCacheDir";
		}
		return cachePath;
	}

	/**
	 * 获取日志保存多少天后的时间戳 比如10天
	 *
	 * @return
	 */
	private long getSaveLongTime(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());// 这里时区需要设置一下，不然会有8个小时的时间差
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		calendar.add(Calendar.DATE, day);
		return calendar.getTimeInMillis();
	}

	/**
	 * 是否有效时间范围里
	 *
	 * @return
	 */
	private boolean isAvailableTime() {
		long fileLogTime = sharedPreferences.getLong(KEY_FILELOGNAME, 0);
		long currentTime = System.currentTimeMillis();//当前时间戳
		if (fileLogTime == 0)//首次
		{
			long saveTime = getSaveLongTime(SAVEFILEDAYS);
			sharedPreferences.edit().putLong(KEY_FILELOGNAME, saveTime).commit();//存放进来
			return true;
		} else if ((currentTime - fileLogTime) <= 0)//有效时间段内
		{
			return true;
		} else {
			sharedPreferences.edit().clear().commit();//清空
			return false;
		}
	}

	/**
	 * 通过递归调用删除一个文件夹及下面的所有文件
	 *
	 * @param file
	 */
	private void deleteFile(File file) {
		if (file.isFile()) {//表示该文件不是文件夹
			file.delete();
		} else {
			//首先得到当前的路径
			String[] childFilePaths = file.list();
			for (String childFilePath : childFilePaths) {//遍历删除子文件
				
				File childFile = new File(file.getAbsolutePath() + "\\" + childFilePath);
				
				long modifiedLongTime = childFile.lastModified();//文件最后一次被修改的时间
				
				long befoutime = getSaveLongTime(-SAVEFILEDAYS);//10天之前的时间戳
				
				if(modifiedLongTime<befoutime)//文件最后一次被修改的时间<10天之前的时间戳
					deleteFile(childFile);
			}
//			file.delete();删除目录
		}
	}

	/**
	 * 获取单例对象
	 *
	 * @return
	 */
	public static LogcatHelper getInstance(Context context) {
		mContext = context;
		if (instance == null) {
			synchronized (LogcatHelper.class) {
				if (instance == null) {
					instance = new LogcatHelper();
				}
			}
		}
		return instance;
	}

	/**
	 * 初始化
	 */
	private void init() {
		sharedPreferences = mContext.getSharedPreferences(FILELOG_SAVE_TIME, Context.MODE_PRIVATE);
		//获取日志存放路径
		LOG_PATH = getLog_Path(mContext);
		boolean availableTime = isAvailableTime();
		if (!availableTime) {
			deleteFile(new File(LOG_PATH));//开始删除日志文件
		}
		//--------------------------------------------
		if (buffer != null) {
			buffer.clear();
		} else {
			buffer = new Vector<String>();
		}
	}

	/**
	 * 输出日志
	 *
	 * @param tag  标签
	 * @param logs 日志内容
	 */
	public synchronized void logcat(String tag, String logs) {
		if (DEBUG) {
			Log.e(tag, logs);
		}
		if (WRITE) {
			
			String stores = tag + ":" + logs + "\n";
			
			addDatasToBuffer(stores);
			
		} else {
			return;
		}
	}

	/**
	 * 保存日志到本地文件
	 *
	 * @param
	 */
	private synchronized void writeLogsToFile() {
		if (buffer == null || buffer.size() == 0) {
			return;
		}
		RandomAccessFile file = null;
		try {
			File root_file = new File(LOG_PATH);
			if (!root_file.exists()) {
				root_file.mkdirs();//创建目录
			}
			file = new RandomAccessFile(LOG_PATH + "/" + getTimeStr() + ".txt", "rw");//创建文件
			String stores = getStoreLogs();
			if (!stores.equals("")) {
				if (file.length() == 0) {
					file.write(stores.getBytes());
				} else {
					file.seek(file.length());
					file.write(stores.getBytes());
				}
			}
			buffer.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 程序退出时，把缓存中的所有日志写入文件
	 */
	public synchronized void writeLogs() {
		if (WRITE) {
			writeLogsToFile();
		}
	}

	/**
	 * 拼接写入的日志
	 *
	 * @param
	 * @return
	 */
	private String getStoreLogs() {
		if (buffer == null || buffer.size() == 0) {
			return "";
		} else {
			StringBuilder builder = new StringBuilder();
			/** 拼接写入的日志 **/
			for (int i = 0; i < buffer.size(); i++) {
				builder.append(buffer.get(i));
			}
		     
			return builder.toString();
		}
	}

	/**
	 * 获取时间字符窜
	 *
	 * @return
	 */
	private String getTimeStr() {
		
		Date date = new Date(System.currentTimeMillis());
		
		return format.format(date);
	}

	/**
	 * 存入数据到buffer
	 *
	 * @param logs
	 */
	private synchronized void addDatasToBuffer(String logs) {
		buffer.add(logs);
	}

}
