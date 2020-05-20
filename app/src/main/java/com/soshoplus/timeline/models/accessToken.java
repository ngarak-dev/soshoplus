/*
 * Ngara K
 * Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 */

package com.soshoplus.timeline.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class accessToken implements Serializable {
    private final static long serialVersionUID = 5028850478321402508L;
    @SerializedName("api_status")
    @Expose
    private Integer apiStatus;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("errors")
    @Expose
    private apiErrors errors;
    
    public accessToken (Integer apiStatus, String timezone, String accessToken, String userId, apiErrors errors) {
        this.apiStatus = apiStatus;
        this.timezone = timezone;
        this.accessToken = accessToken;
        this.userId = userId;
        this.errors = errors;
    }
    
    
    public Integer getApiStatus () {
        return apiStatus;
    }
    
    public void setApiStatus (Integer apiStatus) {
        this.apiStatus = apiStatus;
    }
    
    public String getTimezone () {
        return timezone;
    }
    
    public void setTimezone (String timezone) {
        this.timezone = timezone;
    }
    
    public String getAccessToken () {
        return accessToken;
    }
    
    public void setAccessToken (String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getUserId () {
        return userId;
    }
    
    public void setUserId (String userId) {
        this.userId = userId;
    }
    
    public apiErrors getErrors () {
        return errors;
    }
    
    public void setErrors (apiErrors errors) {
        this.errors = errors;
    }
}
