package com.eeepay.zzq.demo.lib_common.bean;

import com.eeepay.zzq.demo.lib_common.adapter.NullToEmptyAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.HttpURLConnection;


/**
 * 描述：抽象出后台接口返回的数据格式
 * data 为 object 的情况{"status":200,"msg":"success","data":{}}
 * data 为 array  的情况{"status":200,"msg":"success","data":[]}
 * 作者：zhuangzeqin
 * 时间: 2018年8月16日20:34:56
 * 邮箱：zzq@eeepay.cn
 */
public class ResultCallBack implements Serializable {
    //Gson 处理Null 值的问题
    private static final Gson GSON = new GsonBuilder().registerTypeAdapterFactory(new NullToEmptyAdapterFactory()).create();
    private final static int SUCCESS_CODE = HttpURLConnection.HTTP_OK;//成功的code 200
    @SerializedName("status")
    public int status;//200 标识成功
    @SerializedName("msg")
    public String msg;//提示语
    @SerializedName("data")
    public JsonElement data;//泛型T 数据,这里定义了一个json 元素

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return getStatus() == SUCCESS_CODE;
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

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String response ="{\"status\": " + status + ",\"msg\":\"" + msg + "\",\"data\":" + GSON.toJson(data) + "}";
        return response;
    }
}
