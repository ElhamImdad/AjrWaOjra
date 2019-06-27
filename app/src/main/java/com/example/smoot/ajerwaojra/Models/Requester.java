package com.example.smoot.ajerwaojra.Models;

public class Requester {

    private String name ;
    private String phoneNumber;
    private String email;
    private String country;
    private String hoeDoKnowUs ;
    private String password;
    private String token;


    public  Requester(String email, String name){
        this.email=email;
        this.name=name;
    }

    public Requester(String name, String token, String email) {
        this.name = name;
        this.token = token;
        this.email = email;
    }

    public Requester(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonNumber() {
        return phoneNumber;
    }

    public void setPhonNumber(String phonNumber) {
        this.phoneNumber = phonNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHoeDoKnowUs() {
        return hoeDoKnowUs;
    }

    public void setHoeDoKnowUs(String hoeDoKnowUs) {
        this.hoeDoKnowUs = hoeDoKnowUs;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
