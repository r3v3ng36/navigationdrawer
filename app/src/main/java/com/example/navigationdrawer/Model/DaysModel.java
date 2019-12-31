
package com.example.navigationdrawer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DaysModel {

    @SerializedName("cd_days")
    @Expose
    private String cdDays;
    @SerializedName("cd_checkin")
    @Expose
    private String cdCheckin;
    @SerializedName("cd_checkout")
    @Expose
    private String cdCheckout;

    public String getCdDays() {
        return cdDays;
    }

    public void setCdDays(String cdDays) {
        this.cdDays = cdDays;
    }

    public String getCdCheckin() {
        return cdCheckin;
    }

    public void setCdCheckin(String cdCheckin) {
        this.cdCheckin = cdCheckin;
    }

    public String getCdCheckout() {
        return cdCheckout;
    }

    public void setCdCheckout(String cdCheckout) {
        this.cdCheckout = cdCheckout;
    }

    public DaysModel(String cdDays, String cdCheckin, String cdCheckout) {
        this.cdDays = cdDays;
        this.cdCheckin = cdCheckin;
        this.cdCheckout = cdCheckout;
    }
}
