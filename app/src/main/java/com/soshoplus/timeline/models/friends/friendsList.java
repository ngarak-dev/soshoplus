/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.models.friends;

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
    
    public friendsList (List<com.soshoplus.timeline.models.friends.followers> followers, List<com.soshoplus.timeline.models.friends.following> following) {
        this.followers = followers;
        this.following = following;
    }
    
    public List<com.soshoplus.timeline.models.friends.followers> getFollowers () {
        return followers;
    }
    
    public void setFollowers (List<com.soshoplus.timeline.models.friends.followers> followers) {
        this.followers = followers;
    }
    
    public List<com.soshoplus.timeline.models.friends.following> getFollowing () {
        return following;
    }
    
    public void setFollowing (List<com.soshoplus.timeline.models.friends.following> following) {
        this.following = following;
    }
}
