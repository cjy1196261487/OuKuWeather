package com.cjy.oukuweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    public static void send0khttpRequest(String url, okhttp3.Callback callback) {
//        网络访问工具


        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);


    }
}