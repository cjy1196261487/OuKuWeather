package com.cjy.oukuweather.db;

import org.litepal.crud.LitePalSupport;

public class County extends LitePalSupport{


    private String cid;
    private  String location;
    private  String  parent_city;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public String getParent_city() {
        return parent_city;
    }

    public void setParent_city(String parent_city) {
        this.parent_city = parent_city;
    }


    @Override
    public String toString() {
        return "County{" +
                "cid='" + cid + '\'' +
                ", location='" + location + '\'' +
                ", parent_city='" + parent_city + '\'' +
                '}';
    }
//    private int id;
//    private String countyName;
//    private String weatherId;
//    private int cityId;
//
//
//    @Override
//    public String toString() {
//        return "County{" +
//                "id=" + id +
//                ", countyName='" + countyName + '\'' +
//                ", weatherId='" + weatherId + '\'' +
//                ", cityId=" + cityId +
//                '}';
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getCountyName() {
//        return countyName;
//    }
//
//    public void setCountyName(String countyName) {
//        this.countyName = countyName;
//    }
//
//    public String getWeatherId() {
//        return weatherId;
//    }
//
//    public void setWeatherId(String weatherId) {
//        this.weatherId = weatherId;
//    }
//
//    public int getCityId() {
//        return cityId;
//    }
//
//    public void setCityId(int cityId) {
//        this.cityId = cityId;
//    }



}