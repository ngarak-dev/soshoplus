/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models.friends;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.lite.models.apiErrors;

import java.io.Serializable;

public class friends implements Serializable {

    @SerializedName("data")
    @Expose
    private friendsList friendsList;

    @SerializedName("api_status")
    @Expose
    private int apiStatus;

    @SerializedName("errors")
    @Expose
    private apiErrors errors;

    public friends() {

    }

    public friends(com.soshoplus.lite.models.friends.friendsList friendsList, int apiStatus, apiErrors errors) {
        this.friendsList = friendsList;
        this.apiStatus = apiStatus;
        this.errors = errors;
    }

    public com.soshoplus.lite.models.friends.friendsList getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(com.soshoplus.lite.models.friends.friendsList friendsList) {
        this.friendsList = friendsList;
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
