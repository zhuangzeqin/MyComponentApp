package com.eeepay.zzq.demo.lib_common.utils;

import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 描述：assets通过key值读取properties资源配置信息文件
 * 作者：zhuangzeqin
 * 时间: 2018/9/28-16:15
 * 邮箱：zzq@eeepay.cn
 * 备注:
 * 可根据不同的方式来获取InputStream，如：
 * 1、通过当前类加载器的getResourceAsStream方法获取
 * InputStream inStream = TestProperties.class.getClassLoader().getResourceAsStream("test.properties"); 
 * 2、从文件方式获取
 * InputStream inStream = new FileInputStream(new File("filePath")); 
 * 3、通过类加载器获取
 * InputStream in = ClassLoader.getSystemResourceAsStream("filePath");
 * 4、在servlet中，可以通过context获取
 * InputStream in = context.getResourceAsStream("filePath"); 
 * 5、通过url获取
 * URL url = new URL("path");
 * InputStream inStream = url.openStream(); 
 */
public final class ConfigManager {
    private final static String CONFIGPATH = "/assets/config.properties";//文件路径地址
//    private final static String CONFIGPATH  = "file:///android_asset/config.properties";
    private static volatile ConfigManager mInstance = null;
    private Properties properties;//配置属性
    private ConfigManager() {
        initProperties();
    }

    public static ConfigManager getInstance() {
        if (mInstance == null) {
            synchronized (ConfigManager.class) {
                if (mInstance == null) {
                    mInstance = new ConfigManager();
                }
            }
        }
        return mInstance;
    }
    /** ------注释说明----初始化---- **/
    private void initProperties() {
        try {
            properties = new Properties();
            InputStream inputStream = new BufferedInputStream(ConfigManager.class.getResourceAsStream(CONFIGPATH));
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader bufReader = new BufferedReader(inputReader);
            properties.load(bufReader);
            if (bufReader!=null)
            {
                bufReader.close();
                bufReader = null;
            }
            if (inputReader!=null) {
                inputReader.close();
                inputReader = null;
            }if (inputStream!=null) {
                inputStream.close();
                inputStream = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /** ------注释说明--通过key值获得对应的配置信息------ **/
    public String getConfigValue(@NonNull String key) {
        if (properties == null) {
            initProperties();
        }
        return properties.getProperty(key,"");
    }
}
