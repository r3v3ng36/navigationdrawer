package com.example.navigationdrawer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnlineSessionModal {
    @SerializedName("ID")
    @Expose
    private int Id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("check_in")
    @Expose
    private String checkIn;
    @SerializedName("check_out")
    @Expose
    private String checkOut;
    @SerializedName("z_meetingId")
    @Expose
    private String zMeetingId;
    @SerializedName("z_meetingPass")
    @Expose
    private String zMeetingPass;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("video_link")
    @Expose
    private String videoLink;

    @SerializedName("is_visitor")
    @Expose
    private boolean isVistor;

    @SerializedName("course_id")
    @Expose
    private int courseId;

    public OnlineSessionModal(int id, String title, String date, String checkIn, String checkOut, String zMeetingId, String zMeetingPass, String signature, String status, String videoLink, boolean isVistor, int courseId) {
        Id = id;
        this.title = title;
        this.date = date;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.zMeetingId = zMeetingId;
        this.zMeetingPass = zMeetingPass;
        this.signature = signature;
        this.status = status;
        this.videoLink = videoLink;
        this.isVistor = isVistor;
        this.courseId = courseId;
    }


    public OnlineSessionModal(){

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getzMeetingId() {
        return zMeetingId;
    }

    public void setzMeetingId(String zMeetingId) {
        this.zMeetingId = zMeetingId;
    }

    public String getzMeetingPass() {
        return zMeetingPass;
    }

    public void setzMeetingPass(String zMeetingPass) {
        this.zMeetingPass = zMeetingPass;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public boolean isVistor() {
        return isVistor;
    }

    public void setVistor(boolean vistor) {
        isVistor = vistor;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
