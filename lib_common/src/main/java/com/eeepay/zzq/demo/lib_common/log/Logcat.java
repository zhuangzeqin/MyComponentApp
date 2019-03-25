package com.eeepay.zzq.demo.lib_common.log;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * @since 2016-5-3
 * @author wanghao
 *日志类
 */
public class Logcat {
	/**
	 * 标识是否为调试
	 */
	private final boolean DEBUG=false;

	/**
	 * 单例对象
	 */
	private static Logcat instance=null;
	
	/**
	 * 存储数据日志
	 */
	private Vector<String> buffer=new Vector<String>();
	/**
	 * 时间戳
	 */
	private long time=0l;
	private static final long DEFUALT_TIME=30*1000l;
	private long writeTime=0l;
	/**
	 * 记录位置
	 */
	private int position=0;

	
	/**
	 * 构造函数
	 */
	private Logcat(){
	
	}
	
	/**
	 * 获取单例对象
	 * @return
	 */
	public static Logcat getInstance(){
		if(instance==null){
			synchronized (Logcat.class) {
				if(instance==null){
					instance=new Logcat();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 初始化
	 */
	public synchronized void init(){
		writeTime=System.currentTimeMillis();
		position=0;
		time=0l;
		if(buffer!=null){
			buffer.clear();
		}else{
			buffer=new Vector<String>();
		}
	}
	
	/**
	 * 输出日志
	 * @param tag 标签
	 * @param logs 日志内容
	 */
	public synchronized void logcat(String tag,String logs){
		if(DEBUG){
			Log.e(tag, logs);
			if(time==0l){
				time=System.currentTimeMillis();
			}
			String stores=tag + ":" + logs + "\n";
			addDatasToBuffer(stores);
			long tmp_time=System.currentTimeMillis();
			if(time-tmp_time<DEFUALT_TIME){
				return ;
			}else{
				time=tmp_time;
				List<String> listDatas=getDatasFromBuffer();
				writeLogsToFile(listDatas);
			}
		}else{
			return ;
		}
	}
	
	/**
	 * 保存日志到本地文件
	 * @param datas
	 */
	private synchronized void writeLogsToFile(List<String> datas){
		if(datas==null||datas.size()==0){
			return ;
		}
		String root=FileTools.getSaveFilePath() + "/" + FileTools.SLEEP_LOGS;
		RandomAccessFile file=null;
		try{
			File root_file=new File(root);
			if(!root_file.exists()){
				root_file.mkdirs();
			}
			file=new RandomAccessFile(root + "/" + getTimeStr() + ".txt","rw");
			String stores=getStoreLogs(datas);
			if(file.length()==0){
				file.write(stores.getBytes());
			}else{
				file.seek(file.length());
				file.write(stores.getBytes());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(file!=null){
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
	public synchronized void writeLogs(){
		if(DEBUG){
			List<String> datas= getDatasFromBuffer();
			time=System.currentTimeMillis();
			writeLogsToFile(datas);
		}
	}
	
	/**
	 * 拼接写入的日志
	 * @param datas
	 * @return
	 */
	private String getStoreLogs(List<String> datas){
		if(datas==null||datas.size()==0){
			return "";
		}else{
			StringBuilder builder=new StringBuilder();
			for(int i=0;i<datas.size();i++){
				builder.append(datas.get(i));
			}
			return builder.toString();
		}
	}
	
	/**
	 * 获取时间字符窜
	 * @return
	 */
	private String getTimeStr(){
		if(writeTime==0){
			return "0";
		}else{
			Date date=new Date(writeTime);
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(date);
		}
	}
	
	/**
	 * 存入数据到buffer
	 * @param logs
	 */
	private synchronized void addDatasToBuffer(String logs){
		buffer.add(logs);
	}
	
	/**
	 * 从buffer中读取数据
	 * @return
	 */
	public synchronized List<String> getDatasFromBuffer(){
		if(buffer.size()<=position){
			return null;
		}else{
			List<String> list=buffer.subList(position, buffer.size());
			position=buffer.size();
			return list;
		}
	}
	
	/**
	 * 清理数据
	 */
	public synchronized void clear(){
		position=0;
		time=0l;
		writeTime=0l;
		if(buffer!=null){
			buffer.clear();
		}
	}
}
