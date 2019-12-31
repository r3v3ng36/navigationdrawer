package com.example.navigationdrawer.Model;

public class PendingTaskModel {
    private String employee;
    private String task_detail;
    private String recording;
    private String assign_date;
    private String completion_date;
    private String status;

    public PendingTaskModel(String employee, String task_detail, String recording, String assign_date, String completion_date, String status) {
        this.employee = employee;
        this.task_detail = task_detail;
        this.recording = recording;
        this.assign_date = assign_date;
        this.completion_date = completion_date;
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

    public String getCompletion_date() {
        return completion_date;
    }

    public void setCompletion_date(String completion_date) {
        this.completion_date = completion_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
