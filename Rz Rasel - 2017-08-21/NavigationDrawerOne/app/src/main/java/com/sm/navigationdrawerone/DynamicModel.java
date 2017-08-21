package com.sm.navigationdrawerone;

/**
 * Created by Rz Rasel on 2017-08-21.
 */

public class DynamicModel {
    private String title;
    private String description;

    public DynamicModel() {
        //
    }

    public DynamicModel(String argTitle, String argDescription) {
        this.title = argTitle;
        this.description = argDescription;
    }

    public void setTitle(String argTitle) {
        this.title = argTitle;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String argDescription) {
        this.description = argDescription;
    }
}