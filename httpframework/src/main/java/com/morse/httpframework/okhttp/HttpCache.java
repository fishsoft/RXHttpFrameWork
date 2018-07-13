package com.morse.httpframework.okhttp;

import android.content.Context;

import com.morse.httpframework.utils.AppUtils;

import java.io.File;

import okhttp3.Cache;

public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache(Context context) {
        return new Cache(new File(context.getCacheDir().getAbsolutePath() + File
                .separator + "data/NetCache"), HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
