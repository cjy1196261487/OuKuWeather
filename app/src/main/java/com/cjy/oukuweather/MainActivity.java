package com.cjy.oukuweather;

import android.app.Activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.cjy.oukuweather.activity.BaseActivity;

import com.cjy.oukuweather.activity.Ganhuofragment;
import com.cjy.oukuweather.activity.WeatherActivity;
import com.cjy.oukuweather.activity.WeatherFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
    private RadioGroup rg;



    private WeatherFragment frag_01;
    private Ganhuofragment frag_02;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
       initListener();

    }




        private void initView() {

            FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
         rg=(RadioGroup) findViewById(R.id.rg);
        frag_01=new WeatherFragment();
        frag_02=new Ganhuofragment();
        transaction.add(R.id.frame,frag_01);
        transaction.add(R.id.frame,frag_02);
        transaction.show(frag_01);      //  默认选中的一页
        transaction.hide(frag_02);
        transaction.commit();  // 提交事务
        }
    private void initListener() {
        //设置一个容纳framement的构造方法
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case R.id.weather:
                        transaction1.show(frag_01);
                        transaction1.hide(frag_02);
                        break;
                    case R.id.ganhuo:
                        transaction1.hide(frag_01);
                        transaction1.show(frag_02);
                }
                transaction1.commit();
            }
        });
    }






}


