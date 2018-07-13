package com.morse.httpframework;

import android.content.Context;

import com.morse.httpframework.okhttp.CacheInterceptor;
import com.morse.httpframework.okhttp.HttpCache;
import com.morse.httpframework.okhttp.TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitCreateHelper {

    private static RetrofitCreateHelper instance;
    private Retrofit retrofit;

    private RetrofitCreateHelper(Context context,String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //SSL证书
                .sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
//            .sslSocketFactory(TrustManager.getSafeOkHttpClient())
                .hostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                //打印日志
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                //设置Cache拦截器
                .addNetworkInterceptor(new CacheInterceptor(context))
                .addInterceptor(new CacheInterceptor(context))
                .cache(HttpCache.getCache(context))
                //time out
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                //失败重连
                .retryOnConnectionFailure(true)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ResponseConvertFactory.create())
                .build();
    }

    public static RetrofitCreateHelper getInstance(Context context, String baseUrl) {
        if (null == instance) {
            synchronized (RetrofitCreateHelper.class) {
                if (null == instance) {
                    instance = new RetrofitCreateHelper(context,baseUrl);
                }
            }
        }
        return instance;
    }

    public <T> T createApi(Class<T> clazz) {
        return retrofit.create(clazz);
    }
}

