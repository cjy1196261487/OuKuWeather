package com.cjy.oukuweather.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SharePreferenceUtil(Context context,String file){
        sp=context.getSharedPreferences(file,Context.MODE_PRIVATE);
        editor=sp.edit();
    }


    public void setweatherid(String weatherid){
        editor.putString("weatherId",weatherid);
        editor.commit();
    }

    public String getweatherid(){
        return sp.getString("weatherId",null);
    }

}
