package com.example.navigationdrawer.Model;

public class EmployeeListModel {
    Integer u_id;
    String u_username;
    String u_fname;
    String u_lname;

    public EmployeeListModel(Integer u_id, String u_username, String u_fname, String u_lname) {
        this.u_id = u_id;
        this.u_username = u_username;
        this.u_fname = u_fname;
        this.u_lname = u_lname;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public String getU_username() {
        return u_username;
    }

    public void setU_username(String u_username) {
        this.u_username = u_username;
    }

    public String getU_fname() {
        return u_fname;
    }

    public void setU_fname(String u_fname) {
        this.u_fname = u_fname;
    }

    public String getU_lname() {
        return u_lname;
    }

    public void setU_lname(String u_lname) {
        this.u_lname = u_lname;
    }
}
