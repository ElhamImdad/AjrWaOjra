package com.example.smoot.ajerwaojra.Models;
    public class UmraRequest {
        private String requesterName;
        private String country;
        private String date;
        private String countryFlagImagePath;

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
    }

