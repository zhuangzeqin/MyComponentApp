package com.eeepay.zzq.demo.lib_common.mvp.model.rxhelper;

import com.eeepay.zzq.demo.lib_common.adapter.NullToEmptyAdapterFactory;
import com.eeepay.zzq.demo.lib_common.bean.JsonHead;
import com.eeepay.zzq.demo.lib_common.bean.ResultCallBack;
import com.eeepay.zzq.demo.lib_common.mvp.model.exception.ApiException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 描述：服务器结果处理函数
 * 作者：zhuangzeqin
 * 时间: 2018/9/17-16:46
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class ServerResultFunction implements Function<ResultCallBack, Object> {
    //Gson 处理Null 值的问题
    public static final Gson GSON = new GsonBuilder().registerTypeAdapterFactory(new NullToEmptyAdapterFactory()).create();
    @Override
    public Object apply(@NonNull ResultCallBack response) throws Exception {
        //打印服务器回传结果
        Logger.d("HttpResponse:" + response.toString());
        //非200的情况
        if (!response.isSuccess()) {
            int status = response.status;//状态码
            String msg = response.msg;//错误信息
            JsonHead jsonHead = new JsonHead(status, msg);
            return jsonHead;
        } else if (response.isSuccess() && response.data == null) {  //200的情况 并且 data 为null
            //{"status":200,"msg":"找回密码成功","data":null}
            int status = response.status;//状态码
            String msg = response.msg;//错误信息
            JsonHead jsonHead = new JsonHead(status, msg);
            return jsonHead;
        } else {
            if (response.isSuccess())//成功
                return GSON.toJson(response.data);//返回jSON数据
        }
        throw new ApiException(-1001, "解析错误");//抛出服务器错误
    }
}