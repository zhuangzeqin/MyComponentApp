package com.eeepay.zzq.demo.lib_common.retrofit;

import com.eeepay.zzq.demo.lib_common.bean.LoginInfo;
import com.eeepay.zzq.demo.lib_common.bean.Result;
import com.eeepay.zzq.demo.lib_common.bean.ResultCallBack;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 描述：Api接口
 * 作者：zhuangzeqin
 * 时间: 2018/8/15-17:38
 * 邮箱：zzq@eeepay.cn
 * 备注:retrofit2.0后：BaseUrl要以/结尾；@GET 等请求不要以/开头；@Url: 可以定义完整url，不要以 / 开头。
 */
public interface Api {

    @FormUrlEncoded
    @POST("personalCenter/receive/{uuid}/")//  @Path：用于URL上占位符
    Observable<Result<LoginInfo.DataBean>> reqLonin(@Path("uuid") String uuid, @FieldMap Map<String, Object> request);

    @FormUrlEncoded
    @POST("personalCenter/receive/{uuid}/")//  @Path：用于URL上占位符
    Observable<Result<LoginInfo.DataBean>> reqLonin(@Path("uuid") String uuid,
                                                    @Field("mobile_username") String username,
                                                    @Field("mobile_password") String password);
    @FormUrlEncoded
    @POST("personalCenter/receive/{uuid}/")//  @Path：用于URL上占位符
    Observable<Result<LoginInfo.DataBean>> reqLonin(@Path("uuid") String uuid);
    /**
     * 一般通用的 GET请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @GET
    Observable<ResultCallBack> get(@Url String url, @QueryMap Map<String, Object> request);

    /**
     * 一般通用的 POST请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResultCallBack> post(@Url String url, @FieldMap Map<String, Object> request);


    //下载文件
    @Streaming
    @GET
    Observable<Result<ResultCallBack>> downLoadFile(@Url String fileUrl);

    // @DELETE
//    @PUT
//    @POST
//    @GET
    //常用的注解的使用
    //使用@Url注解，传入该Url地址就OK啦，跨过BaseUrl，直接访问该Url地址
    //如果直接用对象 @Body
//    @Query、@QueryMap：用于Http Get请求传递参数
//    @Field：用于Post方式传递参数,需要在请求接口方法上添加@FormUrlEncoded,即以表单的方式传递参数
//    @Body：用于Post,根据转换方式将实例对象转化为对应字符串传递参数.比如Retrofit添加GsonConverterFactory则是将body转化为gson字符串进行传递
//    @Path：用于URL上占位符
//    @Part：配合@Multipart使用,一般用于文件上传
//    @Header：添加http header
//    @Headers：跟@Header作用一样,只是使用方式不一样,@Header是作为请求方法的参数传入,@Headers是以固定方式直接添加到请求方法上
}
