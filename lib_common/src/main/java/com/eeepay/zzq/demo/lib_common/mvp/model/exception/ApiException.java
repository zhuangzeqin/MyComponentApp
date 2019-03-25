package com.eeepay.zzq.demo.lib_common.mvp.model.exception;

/**
 * 描述：api接口错误/异常统一处理类
 * 异常=[程序异常,网络异常,解析异常..]
 * 作者：zhuangzeqin
 * 时间: 2018/8/16-22:39
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class ApiException extends Exception {
    private int code;//错误码
    private String msg;//错误信息

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
