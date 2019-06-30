package com.example.smoot.ajerwaojra.Helpers;

public class OmraInfo {
    String doerName;
    String umraPrayer;
    String status;

    public OmraInfo() {
    }

    public OmraInfo(String umraName, String umraPrayer) {
        this.doerName = umraName;
        this.umraPrayer = umraPrayer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoerName() {
        return doerName;
    }

    public void setDoerName(String umraName) {
        this.doerName = umraName;
    }

    public String getUmraPrayer() {
        return umraPrayer;
    }

    public void setUmraPrayer(String umraPrayer) {
        this.umraPrayer = umraPrayer;
    }
}
