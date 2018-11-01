package com.cjy.oukuweather.db;

import org.litepal.crud.LitePalSupport;

public class City extends LitePalSupport {
    //    private String cid;
//    private  String location;
//    private  String admin_area;
//
//
//    @Override
//    public String toString() {
//        return "City{" +
//                "cid='" + cid + '\'' +
//                ", location='" + location + '\'' +
//                ", admin_area='" + admin_area + '\'' +
//                '}';
//    }
//
//    public String getAdmin_area() {
//        return admin_area;
//    }
//
//    public void setAdmin_area(String admin_area) {
//        this.admin_area = admin_area;
//    }
//
//
//
//    public String getCid() {
//        return cid;
//    }
//
//    public void setCid(String cid) {
//        this.cid = cid;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//
//
//
//    }
    private int id;
    private String cityName;
    private int cityCode;
    private int provinceId;

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", cityCode=" + cityCode +
                ", provinceId=" + provinceId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}