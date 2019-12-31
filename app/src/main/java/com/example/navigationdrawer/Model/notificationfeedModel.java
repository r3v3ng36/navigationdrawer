package com.example.navigationdrawer.Model;

public class notificationfeedModel {

    private Integer n_id;
    private String n_message;
    private String n_time;

    public Integer getN_id() {
        return n_id;
    }

    public String getN_message() {
        return n_message;
    }

    public String getN_time() {
        return n_time;
    }


    public notificationfeedModel(String n_message, String n_time) {
        this.n_message = n_message;
        this.n_time = n_time;
    }
}
