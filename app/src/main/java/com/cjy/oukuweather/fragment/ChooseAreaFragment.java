package com.cjy.oukuweather.fragment;

import android.app.Fragment;
import android.bluetooth.le.AdvertiseData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cjy.oukuweather.MainActivity;
import com.cjy.oukuweather.R;
import com.cjy.oukuweather.activity.WeatherActivity;
import com.cjy.oukuweather.db.City;
import com.cjy.oukuweather.db.County;
import com.cjy.oukuweather.db.Province;
import com.cjy.oukuweather.json.Weather;
import com.cjy.oukuweather.util.HttpUtil;
import com.cjy.oukuweather.util.SharePreferenceUtil;
import com.cjy.oukuweather.util.Utility;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment{

    public static  final  int LEVEL_PROVINCE=0;
    public static  final  int LEVEL_CITY=1;
    public static  final  int LEVEL_COUNTY=2;
    private  TextView title_Text;
    private Button backbutton;
    private ListView listView;
    private ArrayAdapter<String>arrayAdapter;
    private List<String>datalist=new ArrayList<>();
    /*省数据*/
    private List<Province>provinceList;
     /*市列表*/
     private List<City>cityList;
     /*县列表*/
     private List<County>countyList;
     /*当前选中的级别*/
     private  int currentlevel;
     /*选中的省份*/
     private  Province selectProvice;

    /*选择的城市*/
     private City selectCity;
     /*选择的县*/
     private County selectCounty;
    private SharePreferenceUtil sputil;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.choose_area,container,false);
        title_Text=view.findViewById(R.id.title_text);
        backbutton=view.findViewById(R.id.back_button);
        listView=view.findViewById(R.id.list_view);
        arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,datalist);
        listView.setAdapter(arrayAdapter);
        sputil = new SharePreferenceUtil(getActivity(), "saveweather");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryProvince();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentlevel==LEVEL_PROVINCE){
                    selectProvice=provinceList.get(i);
                    queryCity();
                }else if (currentlevel==LEVEL_CITY) {
                    selectCity = cityList.get(i);
                    queryCounty();
                }else if (currentlevel==LEVEL_COUNTY){
                    String weatherid=countyList.get(i).getWeatherId();
                  //  sputil.setweatherid(weatherid);

                    //判断该fragment是否是在MianActivity
                    if (getActivity()instanceof MainActivity){
                        Intent intent=new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id",weatherid);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (getActivity()instanceof WeatherActivity){
                        WeatherActivity activity=(WeatherActivity) getActivity();
                        activity.drawerLayout.closeDrawers();
                        activity.swipeRefreshLayout.setRefreshing(true);
                        activity.requestWeather(weatherid);

                    }
                }


            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentlevel==LEVEL_COUNTY){
                    queryCity();
                }else if (currentlevel==LEVEL_CITY){
                    queryProvince();
                }
            }
        });



    }

    private void queryCity() {
        title_Text.setText(selectProvice.getProvinceName());
        backbutton.setVisibility(View.VISIBLE);
        cityList=LitePal.where("provinceId =?",String.valueOf(selectProvice.getId())).find(City.class);
        if (cityList.size()>0){
            datalist.clear();
            for (int i = 0,citylen=cityList.size(); i <citylen ; i++) {
                datalist.add(cityList.get(i).getCityName());
            }
            Log.i("city is",cityList.toString());
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentlevel=LEVEL_CITY;
        }else {
            int provinceCode=selectProvice.getProvinceCode();
            String address="http://guolin.tech/api/china/"+provinceCode;
            queryFromserver(address,"city");
        }




    }
    private void queryCounty() {
        title_Text.setText(selectCity.getCityName());
        backbutton.setVisibility(View.VISIBLE);
        countyList=LitePal.where("cityid = ?",String.valueOf(selectCity.getId())).find(County.class);
        if (countyList.size()>0){
            datalist.clear();
            for (int i = 0,counlen=countyList.size(); i <counlen ; i++) {
                datalist.add(countyList.get(i).getCountyName());
            }
            Log.i("county is",countyList.toString());
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentlevel=LEVEL_COUNTY;

        }else {
            int provinceCode=selectProvice.getProvinceCode();
            int cityCode=selectCity.getCityCode();
            String address="http://guolin.tech/api/china/"+provinceCode+"/"+cityCode;
            queryFromserver(address,"country");
        }
    }
    private void queryProvince() {
        title_Text.setText("中国");
        backbutton.setVisibility(View.GONE);
        provinceList= LitePal.findAll(Province.class);
        Log.i("provincelist is :",provinceList.toString());
        if (provinceList.size()>0){
            datalist.clear();

            for (int i = 0,prolen=provinceList.size(); i <prolen ; i++) {
                datalist.add(provinceList.get(i).getProvinceName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentlevel=LEVEL_PROVINCE;
        }else {
            String address="http://guolin.tech/api/china";
            //从网络获得
            queryFromserver(address,"province");
        }

    }

    private void queryFromserver(String address, final String type) {
        HttpUtil.send0khttpRequest(address, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean result=false;
                String strresponse=response.body().string();

                if ("province".equals(type)){
                    result= Utility.handlerprovinceRequset(strresponse);
                }else if ("city".equals(type)){
                    result=Utility.handlercityRequset(strresponse,selectProvice.getId());
                }else  if ("country".equals(type)){
                    result=Utility.handlercountyRequset(strresponse,selectCity.getId());

                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("province".equals(type)){
                                queryProvince();
                            }else  if ("city".equals(type)){
                                queryCity();
                            }else if ("country".equals(type)){
                                queryCounty();
                            }

                        }
                    });
                }

          }
        });
    }
}
