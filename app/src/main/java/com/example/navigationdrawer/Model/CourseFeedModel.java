package com.example.navigationdrawer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseFeedModel {
    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("c_entry_no")
    @Expose
    private String cEntryNo;
    @SerializedName("c_idno")
    @Expose
    private String cIdno;
    @SerializedName("c_title")
    @Expose
    private String cTitle;
    @SerializedName("c_teacher_id")
    @Expose
    private String cTeacherId;
    @SerializedName("c_duration")
    @Expose
    private String cDuration;
    @SerializedName("c_fees")
    @Expose
    private String cFees;
    @SerializedName("c_startdate")
    @Expose
    private String cStartdate;
    @SerializedName("c_enddate")
    @Expose
    private String cEnddate;
    @SerializedName("c_status")
    @Expose
    private String cStatus;
    @SerializedName("c_current")
    @Expose
    private String cCurrent;
    @SerializedName("c_upcoming")
    @Expose
    private String cUpcoming;
    @SerializedName("t_name")
    @Expose
    private String tName;

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getCEntryNo() {
        return cEntryNo;
    }

    public void setCEntryNo(String cEntryNo) {
        this.cEntryNo = cEntryNo;
    }

    public String getCIdno() {
        return cIdno;
    }

    public void setCIdno(String cIdno) {
        this.cIdno = cIdno;
    }

    public String getCTitle() {
        return cTitle;
    }

    public void setCTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public String getCTeacherId() {
        return cTeacherId;
    }

    public void setCTeacherId(String cTeacherId) {
        this.cTeacherId = cTeacherId;
    }

    public String getCDuration() {
        return cDuration;
    }

    public void setCDuration(String cDuration) {
        this.cDuration = cDuration;
    }

    public String getCFees() {
        return cFees;
    }

    public void setCFees(String cFees) {
        this.cFees = cFees;
    }

    public String getCStartdate() {
        return cStartdate;
    }

    public void setCStartdate(String cStartdate) {
        this.cStartdate = cStartdate;
    }

    public String getCEnddate() {
        return cEnddate;
    }

    public void setCEnddate(String cEnddate) {
        this.cEnddate = cEnddate;
    }

    public String getCStatus() {
        return cStatus;
    }

    public void setCStatus(String cStatus) {
        this.cStatus = cStatus;
    }

    public String getCCurrent() {
        return cCurrent;
    }

    public void setCCurrent(String cCurrent) {
        this.cCurrent = cCurrent;
    }

    public String getCUpcoming() {
        return cUpcoming;
    }

    public void setCUpcoming(String cUpcoming) {
        this.cUpcoming = cUpcoming;
    }

    public String getTName() {
        return tName;
    }

    public void setTName(String tName) {
        this.tName = tName;
    }

    public CourseFeedModel(String c_id, String c_entry_no, String c_title, String c_fees, String c_duration) {
        this.cId = c_id;
        this.cEntryNo = c_entry_no;
        this.cTitle = c_title;
        this.cFees = c_fees;
        this.cDuration = c_duration;
    }
}
