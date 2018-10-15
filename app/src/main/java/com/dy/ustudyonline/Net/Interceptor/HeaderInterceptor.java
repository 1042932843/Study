package com.dy.ustudyonline.Net.Interceptor;

import android.os.Build;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/15
 * @DESCRIPTION:
 */
public class HeaderInterceptor implements Interceptor {
    //保存cookie
    public static String cookie=null;
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        if(cookie!=null) {
            builder.addHeader("Cookie", cookie);
            if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
                builder.addHeader("Connection", "close");
            }
        }
        else{
            Log.e("Cookie","Cookie not found");
        }
        return chain.proceed(builder.build());
    }
}