package com.eeepay.zzq.demo.lib_common.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述：：IntDef 是Constant Annotation注解---代替java 枚举类型高效减少内存使用
 * 作者：zhuangzeqin
 * 时间: 2018/7/16-15:35
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class IntDefConstant {
    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    @IntDef({SUNDAY, MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WeekDays {}

}
