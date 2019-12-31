
package com.example.navigationdrawer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MagzineModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("file_title")
    @Expose
    private String fileTitle;
    @SerializedName("file_link")
    @Expose
    private String fileLink;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("c_title")
    @Expose
    private String cTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getCTitle() {
        return cTitle;
    }

    public void setCTitle(String cTitle) {
        this.cTitle = cTitle;
    }

//    public MagzineModel(String fileTitle, String date, String cTitle) {
//        this.fileTitle = fileTitle;
//        this.date = date;
//        this.cTitle = cTitle;
//    }


    public MagzineModel(String fileTitle, String date, String cTitle, String path, String courseId, String id, String cId, String fileLink) {
        this.id = id;
        this.fileTitle = fileTitle;
        this.fileLink = fileLink;
        this.path = path;
        this.courseId = courseId;
        this.date = date;
        this.cId = cId;
        this.cTitle = cTitle;
    }
}
