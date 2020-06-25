/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.models.postsfeed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class photoMulti implements Serializable {

    @SerializedName("image")
    private String image;

    @SerializedName("post_id")
    private String postId;

    @SerializedName("parent_id")
    private String parentId;

    @SerializedName("id")
    private String id;

    @SerializedName("image_org")
    private String imageOrg;
    
    public photoMulti () {
    
    }
    
    public photoMulti (String image, String postId, String parentId, String id, String imageOrg) {
        this.image = image;
        this.postId = postId;
        this.parentId = parentId;
        this.id = id;
        this.imageOrg = imageOrg;
    }
    
    public String getImage () {
        return image;
    }
    
    public void setImage (String image) {
        this.image = image;
    }
    
    public String getPostId () {
        return postId;
    }
    
    public void setPostId (String postId) {
        this.postId = postId;
    }
    
    public String getParentId () {
        return parentId;
    }
    
    public void setParentId (String parentId) {
        this.parentId = parentId;
    }
    
    public String getId () {
        return id;
    }
    
    public void setId (String id) {
        this.id = id;
    }
    
    public String getImageOrg () {
        return imageOrg;
    }
    
    public void setImageOrg (String imageOrg) {
        this.imageOrg = imageOrg;
    }
}