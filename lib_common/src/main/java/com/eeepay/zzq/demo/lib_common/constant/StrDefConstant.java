package com.eeepay.zzq.demo.lib_common.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述：StringDef 是Constant Annotation注解 代替java 枚举类型高效减少内存使用
 * StrDefConstant.KEY_FENSI
 * 使用的时候可以约束指定类型方法里参数 @StrDefConstant.CollectionType final String recordStatus
 * 作者：zhuangzeqin
 * 时间: 2018/7/16-15:33
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class StrDefConstant {
    public final static String KEY_BAODAN = "BAODAN";//报单
    public final static String KEY_DAILI = "DAILI";//代理
    public final static String KEY_INOUT = "INOUT";//收益明细
    public final static String KEY_WITHDRAW = "WITHDRAW";//提现明细
    public final static String KEY_FENSI = "FENSI";//粉丝
    public final static String KEY_PTYH = "PTYH";//普通用户

    @StringDef({KEY_BAODAN, KEY_DAILI, KEY_INOUT, KEY_WITHDRAW, KEY_FENSI, KEY_PTYH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CollectionType {
    }
}
