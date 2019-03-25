package com.eeepay.zzq.demo.lib_common.constant;

/**
 * 描述：：IntDef 是Constant Annotation注解---代替java 枚举类型高效减少内存使用
 * 作者：zhuangzeqin
 * 时间: 2018/7/16-15:35
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class LongDefConstant {
    public static final long SUNDAY = 0;
    public static final long MONDAY = 1;
    public static final long TUESDAY = 2;
    public static final long WEDNESDAY = 3;
    public static final long THURSDAY = 4;
    public static final long FRIDAY = 5;
    public static final long SATURDAY = 6;

   /* @LongDef({SUNDAY, MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WeekDays {}*/

}
