/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models.friends.suggested;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.lite.models.apiErrors;

import java.io.Serializable;
import java.util.List;

public class suggestedList implements Serializable {
    
    @SerializedName("data")
    @Expose
    private List<suggestedInfo> suggestedInfo;
    
    @SerializedName("api_status")
    @Expose
    private int apiStatus;
    
    @SerializedName("errors")
    @Expose
    private apiErrors errors;
    private final static long serialVersionUID = 1976144781905562118L;
    
    /**
     * No args constructor for use in serialization
     *
     */
    
    public suggestedList () {
    
    }
    
    /**
     *
     * @param suggestedInfo
     * @param apiStatus
     */
    
    public suggestedList (List<suggestedInfo> suggestedInfo, int apiStatus, apiErrors errors) {
        super();
        this.suggestedInfo = suggestedInfo;
        this.apiStatus = apiStatus;
        this.errors = errors;
    }
    
    public List<suggestedInfo> getSuggestedInfo () {
        return suggestedInfo;
    }
    
    public void setSuggestedInfo (List<suggestedInfo> suggestedInfo) {
        this.suggestedInfo = suggestedInfo;
    }
    
    public int getApiStatus () {
        return apiStatus;
    }
    
    public void setApiStatus (int apiStatus) {
        this.apiStatus = apiStatus;
    }
    
    public apiErrors getErrors () {
        return errors;
    }
    
    public void setErrors (apiErrors errors) {
        this.errors = errors;
    }
}