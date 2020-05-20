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

public class apiErrors implements Serializable {
    private final static long serialVersionUID = 4236251484268891803L;
    @SerializedName("error_id")
    @Expose
    private String errorId;
    @SerializedName("error_text")
    @Expose
    private String errorText;
    
    public apiErrors (String errorId, String errorText) {
        this.errorId = errorId;
        this.errorText = errorText;
    }
    
    public String getErrorText () {
        return errorText;
    }
    
    public void setErrorText (String errorText) {
        this.errorText = errorText;
    }
    
    public String getErrorId () {
        return errorId;
    }
    
    public void setErrorId (String errorId) {
        this.errorId = errorId;
    }
}
