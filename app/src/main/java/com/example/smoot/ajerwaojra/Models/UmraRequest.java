package com.example.smoot.ajerwaojra.Models;
    public class UmraRequest {
        private String requesterName;
        private String country;
        private String date;
        private String countryFlagImagePath;
        private String doaa;
        private String umraOwner ;
        private  int id ;
        private String country_id;

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRequesterName() {
            return requesterName;
        }

        public void setRequesterName(String requesterName) {
            this.requesterName = requesterName;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCountryFlagImagePath() {
            return countryFlagImagePath;
        }

        public void setCountryFlagImagePath(String countryFlagImagePath) {
            this.countryFlagImagePath = countryFlagImagePath;
        }

        public String getDoaa() {
            return doaa;
        }

        public void setDoaa(String doaa) {
            this.doaa = doaa;
        }

        public String getUmraOwner() {
            return umraOwner;
        }

        public void setUmraOwner(String umraOwner) {
            this.umraOwner = umraOwner;
        }
    }

