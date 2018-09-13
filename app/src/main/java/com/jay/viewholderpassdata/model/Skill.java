package com.jay.viewholderpassdata.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jay on 13-09-2018.
 */

public class Skill {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("isSelected")
    @Expose
    private boolean isSelected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}