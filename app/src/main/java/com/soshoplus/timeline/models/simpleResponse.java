/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class simpleResponse implements Serializable {

    @SerializedName("api_status")
    @Expose
    private int apiStatus;

    @SerializedName("errors")
    @Expose
    private apiErrors errors;

    simpleResponse () {

    }

    public simpleResponse(int apiStatus, apiErrors errors) {
        this.apiStatus = apiStatus;
        this.errors = errors;
    }

    public int getApiStatus() {
        return apiStatus;
    }

    public apiErrors getErrors() {
        return errors;
    }
}
