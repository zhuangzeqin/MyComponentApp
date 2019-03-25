package com.eeepay.zzq.demo.lib_common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zw on 2016/8/11 0011.
 * Preference工具类
 */
public class PreferenceUtils {
    public static final String PREFERENCE_CACHE_NAME = "super";
    private static SharedPreferences sharedPreferences;

    private static SharedPreferences.Editor shareEditor;

    private static PreferenceUtils preferenceUtils = null;

    private PreferenceUtils(Context context, String path) {
        sharedPreferences = context.getSharedPreferences(path,
                Context.MODE_PRIVATE);
        shareEditor = sharedPreferences.edit();
    }

    public static PreferenceUtils getInstance(Context context) {
        if (preferenceUtils == null) {
            synchronized (PreferenceUtils.class) {
                if (preferenceUtils == null) {
                    preferenceUtils = new PreferenceUtils(context, PREFERENCE_CACHE_NAME);
                }
            }
        }
        return preferenceUtils;
    }

    public static String getStringParam(String key) {
        return getStringParam(key, "");
    }

    public static String getStringParam(String key, String defaultString) {
        return sharedPreferences.getString(key, defaultString);
    }

    public static void saveParam(String key, String value) {
        shareEditor.putString(key, value).commit();
    }

    public static boolean getBooleanParam(String key) {
        return getBooleanParam(key, false);
    }

    public static boolean getBooleanParam(String key, boolean defaultBool) {
        return sharedPreferences.getBoolean(key, defaultBool);
    }

    public static void saveParam(String key, boolean value) {
        shareEditor.putBoolean(key, value).commit();
    }

    public static int getIntParam(String key) {
        return getIntParam(key, 0);
    }

    public static int getIntParam(String key, int defaultInt) {
        return sharedPreferences.getInt(key, defaultInt);
    }

    public static void saveParam(String key, int value) {
        shareEditor.putInt(key, value).commit();
    }

    public static long getLongParam(String key) {
        return getLongParam(key, 0);
    }

    public static long getLongParam(String key, long defaultInt) {
        return sharedPreferences.getLong(key, defaultInt);
    }

    public static void saveParam(String key, long value) {
        shareEditor.putLong(key, value).commit();
    }

    public static void removeKey(String key) {
        shareEditor.remove(key).commit();
    }

    public static void clear() {
        shareEditor.clear().commit();
    }
}
