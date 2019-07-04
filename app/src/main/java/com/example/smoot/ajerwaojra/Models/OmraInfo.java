package com.example.smoot.ajerwaojra.Models;

public class OmraInfo {
    String umraName;
    String umraPrayer;
    String status;
    String doerOmraName;

    public OmraInfo() {
    }

    public OmraInfo(String umraName, String umraPrayer) {
        this.umraName = umraName;
        this.umraPrayer = umraPrayer;
    }

    public String getDoerOmraName() {
        return doerOmraName;
    }

    public void setDoerOmraName(String doerOmraName) {
        this.doerOmraName = doerOmraName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUmraName() {
        return umraName;
    }

    public void setUmraName(String umraName) {
        this.umraName = umraName;
    }

    public String getUmraPrayer() {
        return umraPrayer;
    }

    public void setUmraPrayer(String umraPrayer) {
        this.umraPrayer = umraPrayer;
    }
}
