/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models.friends;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class friendsList implements Serializable {
    
    @SerializedName("followers")
	private List<followers> followers;

	@SerializedName("following")
	private List<following> following;
	
	public friendsList () {
	   
    }
    
    public friendsList (List<followers> followers, List<following> following) {
        this.followers = followers;
        this.following = following;
    }
    
    public List<followers> getFollowers () {
        return followers;
    }
    
    public void setFollowers (List<followers> followers) {
        this.followers = followers;
    }
    
    public List<following> getFollowing () {
        return following;
    }
    
    public void setFollowing (List<following> following) {
        this.following = following;
    }
}
