package com.cjy.oukuweather.json;

import java.util.Date;

public class Update {

        private String loc;
        private String utc;

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
