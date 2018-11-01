package com.cjy.oukuweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cjy.oukuweather.MyApplication;
import com.cjy.oukuweather.activity.WeatherActivity;
import com.cjy.oukuweather.json.Weather;
import com.cjy.oukuweather.util.HttpUtil;
import com.cjy.oukuweather.util.SharePreferenceUtil;
import com.cjy.oukuweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoupdateService extends Service{
    private Weather weather;
    private SharePreferenceUtil sputil;
    int i=0;
    String weatherid;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sputil=new SharePreferenceUtil(MyApplication.getContext(),"saveweather");
      weatherid=sputil.getweatherid();


        updateWeather(weatherid);
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        //        一小时的毫秒数
        int anHour=1*60*1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;//获取当前时间
        Intent i=new Intent(this,AutoupdateService.class);
        //延迟的intent
        PendingIntent pi=PendingIntent.getService(this,0,i,0);
        alarmManager.cancel(pi);
        //类型 alarm的参数
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);







        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather(String weatherid ) {


//        String weatherUrl="http://guolin.tech/api/weather?cityid="+weatherid+"&key=2c07f3cfca7440a48d2b7c0b52975dd7";
//        HttpUtil.send0khttpRequest(weatherUrl, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            public void onResponse(Call call, Response response) throws IOException {
//                final String respond_text=response.body().string();
//                Log.e("server reponse",response.toString());
////                if (Utility.handleWeatherResponse(respond_text)==null){
////                    swipeRefreshLayout.setRefreshing(false);
////
////                }else {
//                weather = Utility.handleWeatherResponse(respond_text);
//
//            }
//        });
    }
}
