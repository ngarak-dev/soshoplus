/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class follow_unfollow implements Serializable {

    @SerializedName("follow_status")
    @Expose
    private String followStatus;

    @SerializedName("api_status")
    @Expose
    private int apiStatus;

    @SerializedName("errors")
    @Expose
    private apiErrors errors;

    follow_unfollow() {

    }

    public follow_unfollow(String followStatus, int apiStatus, apiErrors errors) {
        this.followStatus = followStatus;
        this.apiStatus = apiStatus;
        this.errors = errors;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public int getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(int apiStatus) {
        this.apiStatus = apiStatus;
    }

    public apiErrors getErrors() {
        return errors;
    }

    public void setErrors(apiErrors errors) {
        this.errors = errors;
    }
}
