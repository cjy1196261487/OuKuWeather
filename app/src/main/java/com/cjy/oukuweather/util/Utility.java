package com.cjy.oukuweather.util;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cjy.oukuweather.MyApplication;
import com.cjy.oukuweather.db.City;
import com.cjy.oukuweather.db.County;
import com.cjy.oukuweather.db.Division;
import com.cjy.oukuweather.db.Province;
import com.cjy.oukuweather.json.Heweather;
import com.cjy.oukuweather.json.Weather;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Utility {



    /*
     * 将返回的josn数据解析成实体类
     *
     *
     * */

    public static Heweather handleWeatherResponse(String response) {
        try {
            Log.e("",response);

            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Heweather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static boolean handlerprovinceRequset(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
//
//                JSONArray jsonArray=new JSONObject(response).getJSONArray("HeWeather6");
//                String weatherContent = jsonArray.getJSONObject(0).toString();
//                Gson gson=new Gson();
//                Division division=gson.fromJson(weatherContent,Division.class);
//                List<Division.BasicBean> allprovince=division.getBasic();
                JSONArray allprovince = new JSONArray(response);

                for (int i = 0, prolenth = allprovince.length(); i < prolenth; i++) {
                    JSONObject provinceObject = allprovince.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static boolean handlercityRequset(String response,int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
//                JSONArray cityjsonarry = new JSONArray(response);
//                JSONObject jsonObject=new JSONObject(response).getJSONObject("HeWeather6");
//                JSONArray cityjsonarry=jsonObject.getJSONArray("basic");

//                JSONArray jsonArray=new JSONObject(response).getJSONArray("HeWeather6");
//                String weatherContent = jsonArray.getJSONObject(0).toString();
//                Gson gson=new Gson();
//                Division division=gson.fromJson(weatherContent,Division.class);
//                List<Division.BasicBean> allcity=division.getBasic();


                    JSONArray cityjsonarry = new JSONArray(response);
                    for (int i = 0, citylen = cityjsonarry.length(); i < citylen; i++) {
                        JSONObject cityjosnobject = cityjsonarry.getJSONObject(i);
                        City city = new City();
                        city.setCityCode(cityjosnobject.getInt("id"));
                        city.setCityName(cityjosnobject.getString("name"));
                        city.setProvinceId(provinceId);
                        city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    public static boolean handlercountyRequset(String response, String cityId) {
        try {
           // JSONArray countryjsonarray = new JSONArray(response);

            JSONArray jsonArray=new JSONObject(response).getJSONArray("HeWeather6");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            Gson gson=new Gson();
            Division division=gson.fromJson(weatherContent,Division.class);
            List<Division.BasicBean> allcountry=division.getBasic();
            for (int i = 0, countrylen = allcountry.size(); i < countrylen; i++) {
               // JSONObject countryjsonobject = countryjsonarray.getJSONObject(i);
                County county = new County();
                county.setCid(allcountry.get(i).getCid());
                county.setLocation(allcountry.get(i).getLocation());
                county.setParent_city(allcountry.get(i).getParent_city());
                county.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }



//    public static void main(String[] args) {
//        String url = "http://guolin.tech/api/weather?cityid=CN101240508&key=2c07f3cfca7440a48d2b7c0b52975dd7";
//        HttpUtil.send0khttpRequest(url, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final Weather weather=Utility.handleWeatherResponse(response.body().string());
//               System.out.println(weather.toString());
//
//            }
//        });

 //   }
}