package com.example.navigationdrawer.Model;

public class ProcessTaskModel {
    private String employee;
    private String task_detail;
    private String recording;
    private String assign_date;
    private String inprogress_date;
    private String status;

    public ProcessTaskModel(String employee, String task_detail, String recording, String assign_date, String inprogress_date, String status) {
        this.employee = employee;
        this.task_detail = task_detail;
        this.recording = recording;
        this.assign_date = assign_date;
        this.inprogress_date = inprogress_date;
        this.status = status;
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

    public String getInprogress_date() {
        return inprogress_date;
    }

    public void setInprogress_date(String inprogress_date) {
        this.inprogress_date = inprogress_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
