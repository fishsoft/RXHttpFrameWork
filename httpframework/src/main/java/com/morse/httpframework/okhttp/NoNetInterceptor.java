package com.morse.httpframework.okhttp;

import android.content.Context;

import com.morse.httpframework.utils.AppUtils;
import com.morse.httpframework.utils.NetworkConnectionUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.morse.httpframework.utils.HttpUtils.getUserAgent;

/**
 * 无网络时的缓存拦截器
 */

public class NoNetInterceptor implements Interceptor {

    private WeakReference<Context> mContext;

    public NoNetInterceptor(Context context){
        mContext=new WeakReference<>(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 无网络时，设置超时为4周
        int maxStale = 60 * 60 * 24 * 28;
        Request request = chain.request();

        if (!NetworkConnectionUtils.isNetworkConnected(mContext.get())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .removeHeader("User-Agent")
                    .header("User-Agent", getUserAgent(mContext.get()))
                    .build();

            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }

        return chain.proceed(request);
    }
}
