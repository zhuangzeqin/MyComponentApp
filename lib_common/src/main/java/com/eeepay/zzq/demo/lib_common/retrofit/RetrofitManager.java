package com.eeepay.zzq.demo.lib_common.retrofit;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.URLUtil;

import com.eeepay.zzq.demo.lib_common.adapter.NullToEmptyAdapterFactory;
import com.eeepay.zzq.demo.lib_common.constant.UrlConfig;
import com.eeepay.zzq.demo.lib_common.utils.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.ConnectionPool;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 描述：Retrofit封装管理类；采用的是单例模式
 * 初始化配置相关信息操作
 * 作者：zhuangzeqin
 * 时间: 2018/8/15-18:03
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public final class RetrofitManager {
    private static final String TAG = RetrofitManager.class.getSimpleName();
    //Gson 处理Null 值的问题
    private static final Gson GSON = new GsonBuilder().registerTypeAdapterFactory(new NullToEmptyAdapterFactory()).create();
    //设置连接超时的值
    private static final int TIMEOUT = 10;
    //默认的 api 服务类
    private Api api;
    //OkHttpClient 构建类
    private OkHttpClient.Builder builder;

    private RetrofitManager() {
        //初始化配置相关信息操作
        initOkHttpConfig();
    }

    private void initOkHttpConfig() {
        //实例化一个OkHttpClient.Builder
        builder = new OkHttpClient.Builder();
        //设置连接超时
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        //设置从主机读信息超时
        builder.readTimeout(TIMEOUT * 5, TimeUnit.SECONDS);
        //设置写信息超时
        builder.writeTimeout(TIMEOUT * 5, TimeUnit.SECONDS);
        //失败重连
        builder.retryOnConnectionFailure(true);
        // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里5个，和每个保持时间为30
        builder.connectionPool(new ConnectionPool(5, TIMEOUT, TimeUnit.SECONDS));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Log 日志拦截器
        builder.addInterceptor(interceptor);
        //让所有网络请求都附上你的 token  ***************根据业务需求需要*********************
//        builder.addNetworkInterceptor(mTokenInterceptor);
        //身份认证刷新 token ***************根据业务需求需要*********************
//        builder.authenticator(mAuthenticator);
//        ***************根据业务需求需要*********************
        //设置okhttp拦截器，这样做的好处是可以为你的每一个
        //retrofit2的网络请求都增加相同的head头信息，而不用每一个请求都写头信息 根据业务需求需要；
//       builder.addInterceptor(mHeader)
         /*  //新建一个文件用来缓存网络请求  ***************根据业务需求需要*********************
        File cacheDirectory = new File(App.getApplicationInstance()
                .getCacheDir().getAbsolutePath(), "HttpCache");
        //设置缓存文件
        builder.cache(new Cache(cacheDirectory, 10 * 1024 * 1024));*/
    }

    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Api getApi() {
        return api;
    }

    /**
     * ------注释说明--获取不同的服务的api------
     **/
    private <T> T getApiService(final Class<T> service, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder().client(builder.build()).
                addConverterFactory(GsonConverterFactory.create(GSON))//gson 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rxjava 适配器;
                .baseUrl(baseUrl)//设置baseurl，服务器地址，基础请求路径，最好以"/"结尾
                .build();
        return retrofit.create(service);
    }

    /**
     * 如果后期最好都配置在UrlConfig 里统一管理
     * 初始化默认的配置api相关信息操作
     */
    public void initConfig(@UrlConfig.UrlMode @NonNull final String baseUrl) {
        initCustomConfig(baseUrl);
    }

    /**
     * 自定义初始化配置不同的baseUrl相关信息操作
     */
    public void initCustomConfig(@NonNull final String baseUrl) {
        api = initCustomConfig(Api.class, baseUrl);
    }

    /**
     * add zhuangzeqin 二〇一八年八月二十一日 09:35:10 重载函数环环相扣；最后提供一个可以在外围创建不同的api 服务
     * 这里定义了泛型T 自定义初始化配置不同的服务的api相关信息操作
     * 如果需要跟某些开发人员调试只需要传不同的api class 即可  例如：initCustomConfig(Api.class, baseUrl);
     */
    public <T> T initCustomConfig(@NonNull final Class<T> service, @NonNull final String baseUrl) {
        if (TextUtils.isEmpty(baseUrl))
            throw new IllegalStateException("===baseUrl is null===");
        if (!URLUtil.isNetworkUrl(baseUrl))
            throw new IllegalStateException(baseUrl + "===The baseUrl is Illegal address.===");
        if (builder == null)
            throw new IllegalStateException("===initOkHttpConfig builder is null===");
        T apiService = getApiService(service, baseUrl);//创建api
        return apiService;
    }

    /**
     * Log 日志拦截器
     */
    final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            // 请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if ((message.startsWith("{") && message.endsWith("}"))
                    || (message.startsWith("[") && message.endsWith("]"))) {
                message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
            }
            mMessage.append(message.concat("\n"));
            // 响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                Logger.d(mMessage.toString());
            }
        }
    });
    //设置okhttp拦截器，这样做的好处是可以为你的每一个
    //retrofit2的网络请求都增加相同的head头信息，而不用每一个请求都写头信息 根据业务需求需要；
    final Interceptor mHeader = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //增加相同的head头信息
//            Request request = chain.request().newBuilder()
//                    .addHeader("Content-Type", "application/json; charset=UTF-8")
//                    .addHeader("Connection", "keep-alive")
//                    .addHeader("Accept", "*/*")
//                    .header("Cache-Control", String.format("public, max-age=%d", 60))
//                    .removeHeader("Pragma")
//                    .build();
            //一个典型应用场景是所有http请求需要加上api key,
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().addQueryParameter(
                    "key", "vaalue").build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        }
    };
    /**
     * ------注释说明--让所有网络请求都附上你的 token：------
     * 那个 if 判断意思是，如果你的 token 是空的，就是还没有请求到 token，
     * 比如对于登陆请求，是没有 token 的，只有等到登陆之后才有 token，这时候就不进行附着上 token。
     * 另外，如果你的请求中已经带有验证 header 了，比如你手动设置了一个另外的 token，那么也不需要再附着这一个 token.
     **/
    final Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
//            if ("Your.sToken" == null || alreadyHasAuthorizationHeader(originalRequest)) {
//                return chain.proceed(originalRequest);
//            }
            Request authorised = originalRequest.newBuilder()
                    .header("Authorization", "Your.sToken")
                    .build();
            return chain.proceed(authorised);
        }
    };
    /**
     * ------注释说明---如果你需要在遇到诸如 401 Not Authorised 的时候进行刷新 token，
     * 可以使用 Authenticator，这是一个专门设计用于当验证出现错误的时候，进行询问获取处理的拦截器：-----
     **/
    final Authenticator mAuthenticator = new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response)
                throws IOException {
//            Your.sToken = service.refreshToken();进行刷新 token
            return response.request().newBuilder()
                    .addHeader("Authorization", "Your.sToken")
                    .build();
        }
    };
}
