package com.eeepay.zzq.demo.lib_common.log;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 2016-4-5
 * @author wanghao
 *程序异常日志捕获
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{

	private Thread.UncaughtExceptionHandler mDefaultHandler;
	private static CrashHandler mCrashHandler;
	private Context mContext;
	private Map<String,String> informations=new HashMap<String, String>();
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	
	private CrashHandler(){
		
	}
	
	public static CrashHandler getInstance(){
		if(mCrashHandler==null){
			synchronized(CrashHandler.class){
				if(mCrashHandler==null){
					mCrashHandler=new CrashHandler();
				}
			}
		}
		return mCrashHandler;
	}
	
	/**
	 * 初始化
	 * @param context
	 */
	public void init(Context context){
		this.mContext=context;
		this.mDefaultHandler=Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}

	private boolean handleException(Throwable ex){
		collectDeviceInformation(mContext);
		saveExceptionInformationToFile(ex);
		return ex!=null;
	}
	
	/**
	 * 收集设备信息
	 * @param context
	 */
	private void collectDeviceInformation(Context context){
		try{
			PackageManager manager=context.getPackageManager();
			PackageInfo info=manager.getPackageArchiveInfo(context.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if(info!=null){
				String versionName=(info.versionName==null?"null":info.versionName);
				String versionCode=String.valueOf(info.versionCode);
				informations.put("versionName", versionName);
				informations.put("versionCode", versionCode);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Field[] fields=Build.class.getDeclaredFields();
		for(Field field:fields){
			try {
				field.setAccessible(true);
				informations.put(field.getName(), field.get(null).toString());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 保存异常信息
	 * @param ex
	 */
	private void saveExceptionInformationToFile(Throwable ex){
		StringBuilder builder=new StringBuilder();
		for(Map.Entry<String, String> entry:informations.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			builder.append(key + "=" + value + "\n");
		}
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		try {
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		builder.append(result);
		try {
			final String errorString = builder.toString();
			long timestamp = System.currentTimeMillis();
			String time = formatter.format(new Date());
			String fileName = "crash-(" + time + ")-" + timestamp + ".log";
			String root_path=FileTools.getSaveFilePath() + "/" + FileTools.SLEEP_CRASH_FOLDER_NAME;
			File dirBase = new File(root_path);
			if (!dirBase.exists()) {
				dirBase.mkdirs();
			}
			File pathCrashFile = new File(dirBase, fileName);
			pathCrashFile.createNewFile();
			
			FileOutputStream fos = new FileOutputStream(pathCrashFile);
			fos.write(errorString.getBytes());
			fos.close();
			/**
			 * 上传服务器
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
