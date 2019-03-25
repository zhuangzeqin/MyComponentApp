package com.eeepay.zzq.demo.lib_common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：class describe
 * 作者：zhuangzeqin
 * 时间: 2018/8/17-14:14
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class ApiUtils {
    public static final String SERVICE_ADDRESS = "http://redeem.pay-world.cn/";//生成域名

    /**
     * 公共参数
     *
     * @return
     */
    public static Map<String, Object> getPubParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("osVersion", android.os.Build.VERSION.RELEASE);
        params.put("appName", "超级兑");
        params.put("platform", "android");
        params.put("clientVersion", "1.0.1");//app版本号
        params.put("oem_no", "OEM000011");
        params.put("nonceStr", Utils.getUUID());//长度为32位的UUID,必传
        params.put("device_id", "1001001001");//极光获取到的设备id
        return params;
    }
}
