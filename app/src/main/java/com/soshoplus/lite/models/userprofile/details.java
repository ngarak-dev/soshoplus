/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models.userprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.lite.models.apiErrors;

import java.io.Serializable;

public class details implements Serializable {

    private final static long serialVersionUID = 109176243499019434L;
    @SerializedName("post_count")
    @Expose
    private String postCount;
    @SerializedName("album_count")
    @Expose
    private String albumCount;
    @SerializedName("following_count")
    @Expose
    private String followingCount;
    @SerializedName("followers_count")
    @Expose
    private String followersCount;
    @SerializedName("groups_count")
    @Expose
    private String groupsCount;
    @SerializedName("likes_count")
    @Expose
    private String likesCount;
    @SerializedName("mutual_friends_count")
    @Expose
    private Object mutualFriendsCount;  /*it an int*/
    @SerializedName("errors")
    @Expose
    private apiErrors errors;

    /**
     * No args constructor for use in serialization
     */
    public details() {
    }

    /**
     * @param postCount
     * @param albumCount
     * @param followingCount
     * @param followersCount
     * @param groupsCount
     * @param likesCount
     * @param mutualFriendsCount
     * @param errors
     */
    public details(String postCount, String albumCount, String followingCount, String followersCount, String groupsCount, String likesCount, int mutualFriendsCount, apiErrors errors) {
        super();
        this.postCount = postCount;
        this.albumCount = albumCount;
        this.followingCount = followingCount;
        this.followersCount = followersCount;
        this.groupsCount = groupsCount;
        this.likesCount = likesCount;
        this.mutualFriendsCount = mutualFriendsCount;
        this.errors = errors;
    }

    public String getPostCount() {
        return postCount;
    }

    public void setPostCount(String postCount) {
        this.postCount = postCount;
    }

    public details withPostCount(String postCount) {
        this.postCount = postCount;
        return this;
    }

    public String getAlbumCount() {
        return albumCount;
    }

    public void setAlbumCount(String albumCount) {
        this.albumCount = albumCount;
    }

    public details withAlbumCount(String albumCount) {
        this.albumCount = albumCount;
        return this;
    }

    public String getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(String followingCount) {
        this.followingCount = followingCount;
    }

    public details withFollowingCount(String followingCount) {
        this.followingCount = followingCount;
        return this;
    }

    public String getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(String followersCount) {
        this.followersCount = followersCount;
    }

    public details withFollowersCount(String followersCount) {
        this.followersCount = followersCount;
        return this;
    }

    public String getGroupsCount() {
        return groupsCount;
    }

    public void setGroupsCount(String groupsCount) {
        this.groupsCount = groupsCount;
    }

    public details withGroupsCount(String groupsCount) {
        this.groupsCount = groupsCount;
        return this;
    }

    public String getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(String likesCount) {
        this.likesCount = likesCount;
    }

    public details withLikesCount(String likesCount) {
        this.likesCount = likesCount;
        return this;
    }

    public Object getMutualFriendsCount() {
        return mutualFriendsCount;
    }

    public void setMutualFriendsCount(Object mutualFriendsCount) {
        this.mutualFriendsCount = mutualFriendsCount;
    }

    public details withMutualFriendsCount(int mutualFriendsCount) {
        this.mutualFriendsCount = mutualFriendsCount;
        return this;
    }

    public apiErrors getErrors() {
        return errors;
    }

    public void setErrors(apiErrors errors) {
        this.errors = errors;
    }
}
