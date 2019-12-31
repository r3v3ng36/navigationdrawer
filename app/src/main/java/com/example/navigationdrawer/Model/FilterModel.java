package com.example.navigationdrawer.Model;

public class FilterModel {
    private String employee;
    private String task_detail;
    private String recording;
    private String assign_date;
    private String action_date;
    private String t_status;

    public FilterModel(String employee, String task_detail, String recording, String assign_date, String action_date, String t_status) {
        this.employee = employee;
        this.task_detail = task_detail;
        this.recording = recording;
        this.assign_date = assign_date;
        this.action_date = action_date;
        this.t_status = t_status;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getTask_detail() {
        return task_detail;
    }

    public void setTask_detail(String task_detail) {
        this.task_detail = task_detail;
    }

    public String getRecording() {
        return recording;
    }

    public void setRecording(String recording) {
        this.recording = recording;
    }

    public String getAssign_date() {
        return assign_date;
    }

    public void setAssign_date(String assign_date) {
        this.assign_date = assign_date;
    }

    public String getAction_date() {
        return action_date;
    }

    public void setAction_date(String action_date) {
        this.action_date = action_date;
    }

    public String getT_status() {
        return t_status;
    }

    public void setT_status(String t_status) {
        this.t_status = t_status;
    }
}
