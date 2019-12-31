package com.example.navigationdrawer.Model;

public class noticefeedModel {
    private Integer id;
    private Integer role;
    private Integer courseid;
    private String student;
    private String notice;
    private String date;
    private Integer checked;
    private Integer is_global;
    private String c_title;
    private String c_image;

    public String getC_image() {
        return c_image;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRole() {
        return role;
    }

    public Integer getCourseid() {
        return courseid;
    }

    public String getStudent() {
        return student;
    }

    public String getNotice() {
        return notice;
    }

    public String getDate() {
        return date;
    }

    public Integer getChecked() {
        return checked;
    }

    public Integer getIs_global() {
        return is_global;
    }

    public String getC_title() {
        return c_title;
    }

    public noticefeedModel(String notice) {
        this.notice = notice;
    }
}
