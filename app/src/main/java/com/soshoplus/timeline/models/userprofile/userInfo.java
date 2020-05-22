/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.models.userprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.timeline.models.apiErrors;

import java.io.Serializable;
import java.util.List;

public class userInfo implements Serializable {
    
    @SerializedName("api_status")
    @Expose
    private int apiStatus;
    @SerializedName("user_data")
    @Expose
    private userData userData;
    @SerializedName("errors")
    @Expose
    private apiErrors errors;
    @SerializedName("liked_pages")
    @Expose
    private List<Object> likedPages = null;
    @SerializedName("joined_groups")
    @Expose
    private List<Object> joinedGroups = null;
    @SerializedName("family")
    @Expose
    private List<Object> family = null;
    private final static long serialVersionUID = -520039212955416670L;
    
    /**
     * No args constructor for use in serialization
     *
     */
    public userInfo() {
    }
    
    /**
     *
     * @param apiStatus
     * @param userData
     * @param errors
     * @param likedPages
     * @param joinedGroups
     * @param family
     */
    public userInfo (int apiStatus, userData userData, apiErrors errors, List<Object> likedPages, List<Object> joinedGroups, List<Object> family) {
        super();
        this.apiStatus = apiStatus;
        this.userData = userData;
        this.errors = errors;
        this.likedPages = likedPages;
        this.joinedGroups = joinedGroups;
        this.family = family;
    }
    
    public int getApiStatus() {
        return apiStatus;
    }
    
    public void setApiStatus(int apiStatus) {
        this.apiStatus = apiStatus;
    }
    
    public userInfo withApiStatus(int apiStatus) {
        this.apiStatus = apiStatus;
        return this;
    }
    
    public userData getUserData() {
        return userData;
    }
    
    public void setUserData(userData userData) {
        this.userData = userData;
    }
    
    public userInfo withUserData(userData userData) {
        this.userData = userData;
        return this;
    }
    
    public List<Object> getLikedPages() {
        return likedPages;
    }
    
    public void setLikedPages(List<Object> likedPages) {
        this.likedPages = likedPages;
    }
    
    public userInfo withLikedPages(List<Object> likedPages) {
        this.likedPages = likedPages;
        return this;
    }
    
    public List<Object> getJoinedGroups() {
        return joinedGroups;
    }
    
    public void setJoinedGroups(List<Object> joinedGroups) {
        this.joinedGroups = joinedGroups;
    }
    
    public userInfo withJoinedGroups(List<Object> joinedGroups) {
        this.joinedGroups = joinedGroups;
        return this;
    }
    
    public List<Object> getFamily() {
        return family;
    }
    
    public void setFamily(List<Object> family) {
        this.family = family;
    }
    
    public userInfo withFamily(List<Object> family) {
        this.family = family;
        return this;
    }
    
    public apiErrors getErrors () {
        return errors;
    }
    
    public void setErrors (apiErrors errors) {
        this.errors = errors;
    }
}
