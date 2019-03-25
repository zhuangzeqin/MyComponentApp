package com.eeepay.zzq.demo.lib_common.mvp.model.exception;

 /**
   * 描述：自定义服务器错误
   * 作者：zhuangzeqin
   * 时间: 2018/8/16-22:38
   * 邮箱：zzq@eeepay.cn
   * 备注:
   */
public class ServerException extends RuntimeException {
    private int code;
    private String msg;

    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
