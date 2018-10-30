package com.cjy.oukuweather.json;



public class Daily_forecast
{
    private String date;

    private Cond cond;

    private Tmp tmp;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public Tmp getTmp() {
        return tmp;
    }

    public void setTmp(Tmp tmp) {
        this.tmp = tmp;
    }
}
