package com.eeepay.zzq.demo.lib_common.log;

import android.os.Environment;

import java.io.File;

/**
 * @since 2016-3-30
 * @author wanghao
 *文件工具
 */
public class FileTools {
	private static final String TAG=FileTools.class.getName();
	
	/**
	 * 文件根目录
	 */
	public static final String ROOT_FOLDER_NAME = "xys_sleep";
	/**
	 * 睡眠记录文件
	 */
	public static final String SLEEP_DETAIL_FOLDER_NAME="sleep_detail";
	/**
	 * 异常信息记录文件
	 */
	public static final String SLEEP_CRASH_FOLDER_NAME="crash_logs";
	public static final String SLEEP_LOGS="sleep_logs";
	
	/**
	 * 获取sd卡根目录
	 * @return
	 * 
	 *部分手机自带SD卡，自带的SD卡文件夹命名为sdcard-ext或其它，用系统自带方法无法检测出
	 *返回格式为 "/mnt/sdcard/" 或 "/mnt/sdcard-ext/"
	 */
	public static String getSDCardRootPath(){
		String path="";
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath() 
							+ "/" + ROOT_FOLDER_NAME);
			if(!file.exists()){
				file.mkdirs();
			}
			path=file.getAbsolutePath();
		}	
		if (path==null||"".equals(path)) {
			File mntFile = new File("/mnt");
			File[] mntFileList = mntFile.listFiles();
			if (mntFileList != null) {
				for (int i = 0; i < mntFileList.length; i++) {
					String mmtFilePath = mntFileList[i].getAbsolutePath();
					String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
					if (!mmtFilePath.equals(sdPath)
							&& mmtFilePath.contains(sdPath)) {
						File file = new File(mmtFilePath + "/"+ ROOT_FOLDER_NAME);
						if (!file.exists()) {
							file.mkdirs();
						}
						path = file.getAbsolutePath();
					}
				}
			}
		}
		return path;
	}
	
	/** 检测是否插入SD卡或是否有自带SD卡
	 * 
	 * @return
	 */
	public static boolean checkSdCard() {
		if (getSDCardRootPath().length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取保存文件路径
	 * 有SD，或自带SD卡时返回SD卡的路径，无SD卡时返回手机存储路径
	 * @return
	 */
	public static String getSaveFilePath() {
		String path = getSDCardRootPath();
		if (path.length() == 0) {
			path = "/data/data/"+ "com.szxys.bledemo";
		}
		return path;
	}	
}
