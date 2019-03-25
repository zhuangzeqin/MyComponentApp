package com.eeepay.zzq.demo.lib_common.bean;

import java.io.Serializable;
import java.net.HttpURLConnection;

/**
 * 描述：抽象出后台接口返回的数据格式
 * data 为 object 的情况{"status":200,"msg":"success","data":{}}
 * data 为 array  的情况{"status":200,"msg":"success","data":[]}
 * 作者：zhuangzeqin
 * 时间: 2017/11/22-9:29
 * 邮箱：zzq@eeepay.cn
 */
public class Result<T> implements Serializable {
    public int status;//200 标识成功
    public String msg;//提示语
    public T data;//泛型T 数据
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
