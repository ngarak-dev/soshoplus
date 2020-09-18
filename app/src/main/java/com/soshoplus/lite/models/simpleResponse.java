/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class simpleResponse implements Serializable {

    @SerializedName("api_status")
    @Expose
    private int apiStatus;

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("errors")
    @Expose
    private apiErrors errors;

    simpleResponse () {

    }

    public simpleResponse(int apiStatus, int code, apiErrors errors) {
        this.apiStatus = apiStatus;
        this.code = code;
        this.errors = errors;
    }

    public int getApiStatus() {
        return apiStatus;
    }

    public apiErrors getErrors() {
        return errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}