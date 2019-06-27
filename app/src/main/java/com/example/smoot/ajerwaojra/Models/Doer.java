package com.example.smoot.ajerwaojra.Models;

public class Doer {

    private String doerName;
    private String doerEmail;
    private String doerPhone;
    private String howDoKnowUs;
    private String doerPassword;
    private String DoerToken;
    private String token;
    private  final  String role = "d" ;

    public String getRole() {
        return role;
    }

    public  Doer(String doerEmail, String doerName) {
        this.doerEmail=doerEmail;
        this.doerName=doerName;
    }
    public  Doer( String doerEmail,String doerName, String phone ){
        this.doerEmail=doerEmail;
        this.doerName=doerName;
        this.doerPhone= phone;
    }
    public Doer(String token) {
        this.DoerToken = token;
    }
    public String getDoerToken() {
        return DoerToken;
    }
    public void setDoerToken(String doerToken) {
        DoerToken = doerToken;
    }

    public String getDoerName() {
        return doerName;
    }

    public void setDoerName(String doerName) {
        this.doerName = doerName;
    }

    public String getDoerEmail() {
        return doerEmail;
    }

    public void setDoerEmail(String doerEmail) {
        this.doerEmail = doerEmail;
    }

    public String getDoerPhone() {
        return doerPhone;
    }

    public void setDoerPhone(String doerPhone) {
        this.doerPhone = doerPhone;
    }

    public String getHowDoKnowUs() {
        return howDoKnowUs;
    }

    public void setHowDoKnowUs(String howDoKnowUs) {
        this.howDoKnowUs = howDoKnowUs;
    }

    public String getDoerPassword() {
        return doerPassword;
    }

    public void setDoerPassword(String doerPassword) {
        this.doerPassword = doerPassword;
    }
}
