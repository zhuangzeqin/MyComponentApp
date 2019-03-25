package com.eeepay.zzq.demo.lib_common.bean;

import java.io.Serializable;
import java.net.HttpURLConnection;

/**
 * 描述：JsonHead 头部
 * 作者：zhuangzeqin
 * 时间: 2018/9/17-17:12
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class JsonHead implements Serializable{
    public JsonHead(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    private int status;//200 标识成功
    private String msg;//提示语
    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return getStatus() == HttpURLConnection.HTTP_OK;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
