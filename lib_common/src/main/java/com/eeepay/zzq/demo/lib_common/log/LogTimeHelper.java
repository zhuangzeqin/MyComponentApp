package com.eeepay.zzq.demo.lib_common.log;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录程序运行耗时打印日志类---单例类
 * Created by zhuangzeqin on 2016/6/14.
 */
public class LogTimeHelper {

	/** tag 过滤标签 **/
	private static final String TAG = "LogTimeHelper";
	/** 日期格式化 **/
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

	/** 存放开始时间的map集合 **/
	private Map<String,Long> longMap = null;

	/**
	 * 将程序开始时间放进来
	 * @param key 程序开始时间的标志
	 * @param value 一般是当前系统时间
	 */
	public void putStartTime(String key, Long value) {
		if (longMap.containsKey(key))//如果存在；先移除后填进来
		{
			longMap.remove(key);
		}
		Log.i(TAG,key+" 开始时间:"+df.format(new Date(value)));
		longMap.put(key,value);
	}

	/**
	 * 私有的构造函数
	 */
	private LogTimeHelper() {
		longMap = new HashMap<String,Long>();
	}


	/**
	 * 获取耗时时间
	 * @param startTag 开始标签时间key
	 * @param EndTime 结束时的longtime 一般为系统时间
	 * @return
	 */
	public long getTimeConsuming(String startTag , long EndTime)
	{
		if (!longMap.containsKey(startTag))//如果不存在直接返回0
			return 0;

		long startTime = longMap.get(startTag);//开始时间

		Date endDate = new Date(EndTime);

		long time = getTimeSimpleDate(new Date(startTime),endDate);

		Log.i(TAG,startTag+" 结束时间:"+df.format(endDate)+";----耗时:"+time+" ms");
		
		if (longMap!=null && longMap.size()>0)
			longMap.remove(startTag);//计算完结果之后；移除掉

		return time;//计算2个时间差
	}

	private static class SingletonHolder {

		private static final LogTimeHelper INSTANCE = new LogTimeHelper();

	}
	/** 获取实例 **/
	public static LogTimeHelper getInstance() {

		return SingletonHolder.INSTANCE;

	}

	/**
	 * 在程序退出的时候；调用一下清除
	 */
	public void cleadTimeMapData()
	{
		if (longMap!=null && longMap.size()>0)
		{
			longMap.clear();
		}
	}

	/**
	 * 计算2个时间差
	 *
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @return 时间差的毫秒数
	 */
	private long getTimeSimpleDate(Date starttime, Date endtime) {
		long diffTime = 0L;
		try {
			diffTime = endtime.getTime() - starttime.getTime();// 毫秒
			// long days = diff / (1000 * 60 * 60 * 24);
			return Math.abs(diffTime);// 取绝对值
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diffTime;
	}
}
