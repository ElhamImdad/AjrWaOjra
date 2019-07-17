package com.example.smoot.ajerwaojra.Models;

public class ServiceInfo  {
    private String doerName, rating, omraName, date;
    private int noCompletedOrder, order_id ;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getNoCompletedOrder() {
        return noCompletedOrder;
    }

    public void setNoCompletedOrder(int noCompletedOrder) {
        this.noCompletedOrder = noCompletedOrder;
    }

    public String getDoerName() {
        return doerName;
    }

    public void setDoerName(String doerName) {
        this.doerName = doerName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOmraName() {
        return omraName;
    }

    public void setOmraName(String omraName) {
        this.omraName = omraName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
