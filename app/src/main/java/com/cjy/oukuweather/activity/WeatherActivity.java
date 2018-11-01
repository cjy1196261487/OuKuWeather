package com.cjy.oukuweather.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cjy.oukuweather.R;
import com.cjy.oukuweather.json.Daily_forecast;
import com.cjy.oukuweather.json.Heweather;
import com.cjy.oukuweather.json.Weather;
import com.cjy.oukuweather.service.AutoupdateService;
import com.cjy.oukuweather.util.HttpUtil;
import com.cjy.oukuweather.util.SharePreferenceUtil;
import com.cjy.oukuweather.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherscrollview;
    private TextView titleCity,titleUpdateTime,degreeText,weatherInfo,aqiText,pm25Text,comfortText,carWashText,sportText,qutytext;
    private LinearLayout forcastLayout;

    public SwipeRefreshLayout swipeRefreshLayout;
    private ImageView backimg, citybutton;
    public DrawerLayout drawerLayout;
    private Weather weather;
    private Heweather heweather;
    private SharePreferenceUtil sputil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sputil=new SharePreferenceUtil(WeatherActivity.this,"saveweather");
//
            if(Build.VERSION.SDK_INT >= 21){
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_weather);
        initView();

        loadPic();
        requestWeather("CN101240508");
        initlisten();




    }

    private void loadPic() {
        final String picUrl="http://guolin.tech/api/bing_pic";
        HttpUtil.send0khttpRequest(picUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String picurl=response.body().string();
                Log.e("bing 每日一图 url",picurl);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(picurl).into(backimg);

                    }
                });



            }
        });



    }

    private void initlisten() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showWeather(heweather);

            }
        });

        citybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    public void requestWeather(String weatherId) {
        sputil.setweatherid(weatherId);
      //  String url="http://guolin.tech/api/weather?cityid="+weatherId+"&key=2c07f3cfca7440a48d2b7c0b52975dd7";
        String url="https://free-api.heweather.com/s6/weather?location="+weatherId+"&key=2c07f3cfca7440a48d2b7c0b52975dd7";
        HttpUtil.send0khttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String respond_text=response.body().string();
               // Log.e("++++",Utility.handleWeatherResponse(respond_text).toString());
//                if (Utility.handleWeatherResponse(respond_text)==null){
//                    swipeRefreshLayout.setRefreshing(false);
//
//                }else {
                heweather = Utility.handleWeatherResponse(respond_text);



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (heweather.getStatus().equals("ok")) {

                                showWeather(heweather);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                            else if(heweather.getStatus().equals("unknown city")) {

                                 Toast.makeText(WeatherActivity.this,"未知地区",Toast.LENGTH_SHORT).show();
                                swipeRefreshLayout.setRefreshing(false);

                            }
                        }
                    });


                }




        });






    }


    private void initView() {
//        初始化控件
        weatherscrollview=findViewById(R.id.weather_layout);
        titleCity=findViewById(R.id.title_city);
        titleUpdateTime=findViewById(R.id.title_updatetime);
        degreeText=findViewById(R.id.degree_text);
        weatherInfo=findViewById(R.id.weatherinfo_text);
        aqiText=findViewById(R.id.aqi_text);
        pm25Text=findViewById(R.id.pm25_text);
        qutytext=findViewById(R.id.quty_text);
        comfortText=findViewById(R.id.comfort_text);
        carWashText=findViewById(R.id.car_text);
        sportText=findViewById(R.id.sport_text);
        forcastLayout=findViewById(R.id.forecast_layout);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        backimg=findViewById(R.id.backgroun_pic);
        citybutton=findViewById(R.id.city_select);
        drawerLayout=findViewById(R.id.drawer_layout);





    }
    private void showWeather(Heweather heweather) {
        if (heweather!=null && "ok".equals(heweather.getStatus())){
            Intent intent=new Intent(this, AutoupdateService.class);
            intent.putExtra("cityId",heweather.getBasic().getCid());
            startService(intent);
        }else {
            Toast.makeText(WeatherActivity.this,"获取天气失败",Toast.LENGTH_SHORT).show();
        }
        titleCity.setText(heweather.getBasic().getLocation());
        String updatetime = heweather.getUpdate().getLoc();
        titleUpdateTime.setText(updatetime);
        degreeText.setText(heweather.getNow().getTmp() + "℃");
        weatherInfo.setText(heweather.getNow().getCond_txt());
//        aqiText.setText(weather.aqi.getCity().getAqi());
//        pm25Text.setText(weather.aqi.getCity().getPm25());
//        qutytext.setText(weather.aqi.getCity().getQlty());
        comfortText.setText("舒适度：" + heweather.getLifestyle().get(0).getTxt());
        carWashText.setText("洗车 ：" +heweather.getLifestyle().get(heweather.getLifestyle().size()-2).getTxt());
        sportText.setText("运动 ：" + heweather.getLifestyle().get(3).getTxt());
        forcastLayout.removeAllViews();

        for (Heweather.DailyForecastBean dailyForecas:heweather.getDaily_forecast()) {
            View view = LayoutInflater.from(this).inflate(R.layout.forcast_item, forcastLayout, false);
            TextView dateText = view.findViewById(R.id.date_info);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView max = view.findViewById(R.id.max_text);
            TextView min = view.findViewById(R.id.min_text);
            dateText.setText(dailyForecas.getDate());
            infoText.setText(dailyForecas.getCond_txt_d());
            max.setText(dailyForecas.getTmp_max()+"℃");
            min.setText(dailyForecas.getTmp_min()+"℃");
            forcastLayout.addView(view);
        }

        swipeRefreshLayout.setRefreshing(false);

    }



    }



