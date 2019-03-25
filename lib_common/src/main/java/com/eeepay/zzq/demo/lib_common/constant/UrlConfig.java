package com.eeepay.zzq.demo.lib_common.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述：环境信息的配置
 * 作者：zhuangzeqin
 * 时间: 2018/8/16-10:28
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class UrlConfig {
    //gitHub 免费开放的请求数据接口
    public static final String GITHUB_BASEURL = "https://api.github.com/";
    //测试的请求数据接口
    public static final String TEST_BASEURL = "https://api.github.com/";
    //准生产的请求数据接口
    public static final String QUASIPRODUCTION_BASEURL = "https://api.github.com/";
    //正式的请求数据接口
    public static final String OFFICIAL_BASEURL = "https://api.github.com/";

    /** ------注释说明--start------ **/

    // ***********************可以新增不同的url 地址； 比如链接不同的开发人员的的后台地址*************

    /**
     * ------注释说明---end-----
     **/
    @StringDef({GITHUB_BASEURL, TEST_BASEURL, QUASIPRODUCTION_BASEURL, OFFICIAL_BASEURL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UrlMode {
    }
}
