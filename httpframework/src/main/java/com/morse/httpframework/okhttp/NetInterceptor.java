package com.morse.httpframework.okhttp;

import android.content.Context;

import com.morse.httpframework.utils.NetworkConnectionUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.morse.httpframework.utils.HttpUtils.getUserAgent;

/**
 * 有网络时的缓存拦截器
 */

public class NetInterceptor implements Interceptor {

    private WeakReference<Context> mContext;

    public NetInterceptor(Context context) {
        mContext = new WeakReference<>(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 有网络时, 缓存1分钟, 最大保存时长为60s
        int maxAge = 60;
        Request request = chain.request();

        if (NetworkConnectionUtils.isNetworkConnected(mContext.get())) {
            request = request.newBuilder()
                    .removeHeader("User-Agent")
                    .header("User-Agent", getUserAgent(mContext.get()))
                    .build();

            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }
        return chain.proceed(request);
    }
}
