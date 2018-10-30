package com.cjy.oukuweather.db;

import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;
import org.litepal.tablemanager.callback.DatabaseListener;

public class Province extends LitePalSupport{

    private int id;
    private  String provinceName;
    private  int provinceCode;

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", provinceName='" + provinceName + '\'' +
                ", provinceCode=" + provinceCode +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
