package com.eeepay.zzq.demo.lib_common.excation;

import java.io.File;

/**
 * 描述：定义一个接口，用于在保存好日志之后的回调
 * 作者：zhuangzeqin
 * 时间: 2018/7/10-15:52
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public interface CrashListener {
    /**
     * 保存异常的日志。
     *
     * @param file
     */
    public void afterSaveCrash(File file);

}
