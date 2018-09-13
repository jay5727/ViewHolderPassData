package com.jay.viewholderpassdata.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jay on 13-09-2018.
 */

public class Data {
    @SerializedName("Skills")
    @Expose
    private List<CustomModel> skills = null;

    @SerializedName("Location")
    @Expose
    private List<CustomModel> location = null;



    @SerializedName("RecentlyAdded")
    @Expose
    private List<Object> recentlyAdded = null;


    @SerializedName("Gender")
    @Expose
    private List<Object> gender = null;

    public List<CustomModel> getSkills() {
        return skills;
    }

    public void setSkills(List<CustomModel> skills) {
        this.skills = skills;
    }

    public List<CustomModel> getLocation() {
        return location;
    }

    public void setLocation(List<CustomModel> location) {
        this.location = location;
    }

    public List<Object> getRecentlyAdded() {
        return recentlyAdded;
    }

    public void setRecentlyAdded(List<Object> recentlyAdded) {
        this.recentlyAdded = recentlyAdded;
    }

    public List<Object> getGender() {
        return gender;
    }

    public void setGender(List<Object> gender) {
        this.gender = gender;
    }
}
