package com.example.navigationdrawer.Model;

public class AllNotiModel {
    public  Integer id;
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


    public String getNotices() {
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


    public AllNotiModel(String notice) {
        this.notice = notice;
    }
}
