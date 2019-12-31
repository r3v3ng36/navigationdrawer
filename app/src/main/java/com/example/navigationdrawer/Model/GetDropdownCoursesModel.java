package com.example.navigationdrawer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDropdownCoursesModel {

    @SerializedName("c_id")
    @Expose
    private int Id;
    @SerializedName("c_title")
    @Expose
    private String title;

    public GetDropdownCoursesModel(int id, String title) {
        Id = id;
        this.title = title;
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
}
