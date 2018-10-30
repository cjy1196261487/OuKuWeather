package com.cjy.oukuweather.json;

import java.util.List;

public class Weather {

    public String status;

    public  Basic basic;

    public  Aqi aqi;

    public Now now;
    public  String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Suggestion suggestion;

    public List<Daily_forecast>daily_forecast;

    @Override
    public String toString() {
        return "Weather{" +
                "status='" + status + '\'' +
                ", basic=" + basic +
                ", aqi=" + aqi +
                ", now=" + now +
                ", suggestion=" + suggestion +
                ", daily_forecas=" + daily_forecast +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    public List<Daily_forecast> getDaily_forecas() {
        return daily_forecast;
    }

    public void setDaily_forecas(List<Daily_forecast> daily_forecas) {
        this.daily_forecast = daily_forecas;
    }

}
