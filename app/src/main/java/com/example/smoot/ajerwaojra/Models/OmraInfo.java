package com.example.smoot.ajerwaojra.Models;

import java.util.ArrayList;

public class OmraInfo {
    String status, umraName, doerOmraName, doaa,date,time, isStartOmra;
    ArrayList<String> photos;
    int id;

    public OmraInfo() {
    }

    public OmraInfo(String umraName, String doaa) {
        this.umraName = umraName;
        this.doaa = doaa;
    }

    public String getIsStartOmra() {
        return isStartOmra;
    }

    public void setIsStartOmra(String isStartOmra) {
        this.isStartOmra = isStartOmra;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDoaa() {
        return doaa;
    }

    public void setDoaa(String doaa) {
        this.doaa = doaa;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
