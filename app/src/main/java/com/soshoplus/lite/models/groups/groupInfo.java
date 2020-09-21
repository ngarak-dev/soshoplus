/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models.groups;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class groupInfo implements Serializable {

    @SerializedName("group_title")
    @Expose
    private String groupTitle;

    @SerializedName("group_sub_category")
    @Expose
    private String groupSubCategory;

    @SerializedName("group_name")
    @Expose
    private String groupName;

    @SerializedName("sub_category")
    @Expose
    private String subCategory;

    @SerializedName("is_owner")
    @Expose
    private boolean isOwner;

    @SerializedName("about")
    @Expose
    private String about;

    @SerializedName("privacy")
    @Expose
    private String privacy;

    @SerializedName("active")
    @Expose
    private String active;

    @SerializedName("registered")
    @Expose
    private String registered;

    @SerializedName("is_joined")
    @Expose
    private boolean isJoined;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("cover")
    @Expose
    private String cover;

    @SerializedName("category_id")
    @Expose
    private String categoryId;

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("group_id")
    @Expose
    private String groupId;

    @SerializedName("members")
    @Expose
    private String members;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("join_privacy")
    @Expose
    private String joinPrivacy;

    @SerializedName("username")
    @Expose
    private String username;

    public groupInfo() {

    }

    public groupInfo(String groupTitle, String groupSubCategory, String groupName, String subCategory, boolean isOwner, String about, String privacy, String active, String registered, boolean isJoined, String avatar, String type, String url, String cover, String categoryId, String userId, String groupId, String members, String name, String id, String category, String joinPrivacy, String username) {
        this.groupTitle = groupTitle;
        this.groupSubCategory = groupSubCategory;
        this.groupName = groupName;
        this.subCategory = subCategory;
        this.isOwner = isOwner;
        this.about = about;
        this.privacy = privacy;
        this.active = active;
        this.registered = registered;
        this.isJoined = isJoined;
        this.avatar = avatar;
        this.type = type;
        this.url = url;
        this.cover = cover;
        this.categoryId = categoryId;
        this.userId = userId;
        this.groupId = groupId;
        this.members = members;
        this.name = name;
        this.id = id;
        this.category = category;
        this.joinPrivacy = joinPrivacy;
        this.username = username;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public String getGroupSubCategory() {
        return groupSubCategory;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public boolean isIsOwner() {
        return isOwner;
    }

    public String getAbout() {
        return about;
    }

    public String getPrivacy() {
        return privacy;
    }

    public String getActive() {
        return active;
    }

    public String getRegistered() {
        return registered;
    }

    public boolean isIsJoined() {
        return isJoined;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getCover() {
        return cover;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getJoinPrivacy() {
        return joinPrivacy;
    }

    public String getUsername() {
        return username;
    }
}