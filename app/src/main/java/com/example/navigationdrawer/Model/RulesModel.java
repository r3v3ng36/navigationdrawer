
package com.example.navigationdrawer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RulesModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rules")
    @Expose
    private String rules;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

}
