package com.example.smoot.ajerwaojra.Models;

public class Requester {

    private String name ;
    private int phoneNumber;
    private String email;
    private String country;
    private String hoeDoKnowUs ;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhonNumber() {
        return phoneNumber;
    }

    public void setPhonNumber(int phonNumber) {
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
