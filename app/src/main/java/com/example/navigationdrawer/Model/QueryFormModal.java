package com.example.navigationdrawer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryFormModal {


    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("lq")
    @Expose
    private String lq;
    @SerializedName("cins")
    @Expose
    private String cins;

    public QueryFormModal(String fname, String lname, String mobile, String course, String email, String lq, String cins) {
        this.fname = fname;
        this.lname = lname;
        this.mobile = mobile;
        this.course = course;
        this.email = email;
        this.lq = lq;
        this.cins = cins;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLq() {
        return lq;
    }

    public void setLq(String lq) {
        this.lq = lq;
    }

    public String getCins() {
        return cins;
    }

    public void setCins(String cins) {
        this.cins = cins;
    }
}
