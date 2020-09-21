package com.soshoplus.lite.models.postsfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.lite.models.apiErrors;

import java.io.Serializable;
import java.util.List;

public class commentsList implements Serializable {

    @SerializedName("api_status")
    @Expose
    private int apiStatus;

    @SerializedName("data")
    @Expose
    private List<postComments> postComments;

    @SerializedName("errors")
    @Expose
    private apiErrors errors;

    commentsList() {

    }

    public commentsList(int apiStatus, List<postComments> postComments, apiErrors errors) {
        this.apiStatus = apiStatus;
        this.postComments = postComments;
        this.errors = errors;
    }

    public int getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(int apiStatus) {
        this.apiStatus = apiStatus;
    }

    public List<postComments> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<postComments> postComments) {
        this.postComments = postComments;
    }

    public apiErrors getErrors() {
        return errors;
    }

    public void setErrors(apiErrors errors) {
        this.errors = errors;
    }
}