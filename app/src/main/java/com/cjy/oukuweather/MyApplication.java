package com.cjy.oukuweather;

import android.content.Context;

import org.litepal.LitePalApplication;

public class MyApplication extends LitePalApplication {
    //继承litepalapplication 配置
    public static Context   context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
    public static Context getcontext(){
        return context;
    }


}
