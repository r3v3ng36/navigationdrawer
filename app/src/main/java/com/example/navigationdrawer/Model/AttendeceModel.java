
package com.example.navigationdrawer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendeceModel {

    @SerializedName("sa_id")
    @Expose
    private String saId;
    @SerializedName("sa_std_no")
    @Expose
    private String saStdNo;
    @SerializedName("sa_date")
    @Expose
    private String saDate;
    @SerializedName("sa_ontime")
    @Expose
    private String saOntime;
    @SerializedName("sa_offtime")
    @Expose
    private String saOfftime;
    @SerializedName("sa_checkin")
    @Expose
    private String saCheckin;
    @SerializedName("sa_checkout")
    @Expose
    private String saCheckout;
    @SerializedName("sa_late")
    @Expose
    private String saLate;
    @SerializedName("sa_early")
    @Expose
    private String saEarly;
    @SerializedName("sa_absent")
    @Expose
    private String saAbsent;
    @SerializedName("sa_totaltime")
    @Expose
    private String saTotaltime;
    @SerializedName("sa_courseid")
    @Expose
    private String saCourseid;
    @SerializedName("sa_extra_classid")
    @Expose
    private String saExtraClassid;

    public String getSaId() {
        return saId;
    }

    public void setSaId(String saId) {
        this.saId = saId;
    }

    public String getSaStdNo() {
        return saStdNo;
    }

    public void setSaStdNo(String saStdNo) {
        this.saStdNo = saStdNo;
    }

    public String getSaDate() {
        return saDate;
    }

    public void setSaDate(String saDate) {
        this.saDate = saDate;
    }

    public String getSaOntime() {
        return saOntime;
    }

    public void setSaOntime(String saOntime) {
        this.saOntime = saOntime;
    }

    public String getSaOfftime() {
        return saOfftime;
    }

    public void setSaOfftime(String saOfftime) {
        this.saOfftime = saOfftime;
    }

    public String getSaCheckin() {
        return saCheckin;
    }

    public void setSaCheckin(String saCheckin) {
        this.saCheckin = saCheckin;
    }

    public String getSaCheckout() {
        return saCheckout;
    }

    public void setSaCheckout(String saCheckout) {
        this.saCheckout = saCheckout;
    }

    public String getSaLate() {
        return saLate;
    }

    public void setSaLate(String saLate) {
        this.saLate = saLate;
    }

    public String getSaEarly() {
        return saEarly;
    }

    public void setSaEarly(String saEarly) {
        this.saEarly = saEarly;
    }

    public String getSaAbsent() {
        return saAbsent;
    }

    public void setSaAbsent(String saAbsent) {
        this.saAbsent = saAbsent;
    }

    public String getSaTotaltime() {
        return saTotaltime;
    }

    public void setSaTotaltime(String saTotaltime) {
        this.saTotaltime = saTotaltime;
    }

    public String getSaCourseid() {
        return saCourseid;
    }

    public void setSaCourseid(String saCourseid) {
        this.saCourseid = saCourseid;
    }

    public String getSaExtraClassid() {
        return saExtraClassid;
    }

    public void setSaExtraClassid(String saExtraClassid) {
        this.saExtraClassid = saExtraClassid;
    }

    public AttendeceModel(String saId, String saStdNo, String saDate, String saOntime, String saOfftime,
                          String saCheckin, String saCheckout, String saLate, String saEarly, String saAbsent,
                          String saTotaltime, String saCourseid, String saExtraClassid) {
        this.saId = saId;
        this.saStdNo = saStdNo;
        this.saDate = saDate;
        this.saOntime = saOntime;
        this.saOfftime = saOfftime;
        this.saCheckin = saCheckin;
        this.saCheckout = saCheckout;
        this.saLate = saLate;
        this.saEarly = saEarly;
        this.saAbsent = saAbsent;
        this.saTotaltime = saTotaltime;
        this.saCourseid = saCourseid;
        this.saExtraClassid = saExtraClassid;
    }
}
