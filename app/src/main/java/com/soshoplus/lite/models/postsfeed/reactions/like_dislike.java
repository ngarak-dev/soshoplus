/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models.postsfeed.reactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.lite.models.apiErrors;

import java.io.Serializable;

public class like_dislike implements Serializable {
    
    @SerializedName("action")
    @Expose
    private String action;
    
    @SerializedName("api_status")
    @Expose
    private int apiStatus;
    
    @SerializedName("likes_data")
    @Expose
    private likeStatus likesData;
    
    @SerializedName("errors")
    @Expose
    private apiErrors errors;
    
    like_dislike () {
    
    }
    
    public like_dislike (String action, int apiStatus, likeStatus likesData, apiErrors errors) {
        this.action = action;
        this.apiStatus = apiStatus;
        this.likesData = likesData;
        this.errors = errors;
    }
    
    public String getAction () {
        return action;
    }
    
    public void setAction (String action) {
        this.action = action;
    }
    
    public int getApiStatus () {
        return apiStatus;
    }
    
    public void setApiStatus (int apiStatus) {
        this.apiStatus = apiStatus;
    }
    
    public likeStatus getLikesData () {
        return likesData;
    }
    
    public void setLikesData (likeStatus likesData) {
        this.likesData = likesData;
    }
    
    public apiErrors getErrors () {
        return errors;
    }
    
    public void setErrors (apiErrors errors) {
        this.errors = errors;
    }
}