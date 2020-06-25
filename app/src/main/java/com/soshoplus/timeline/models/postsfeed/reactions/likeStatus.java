package com.soshoplus.timeline.models.postsfeed.reactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class likeStatus implements Serializable {

	@SerializedName("count")
	@Expose
	private String count;
	
	likeStatus () {
	
	}
	
	public likeStatus (String count) {
		this.count = count;
	}
	
	public String getCount () {
		return count;
	}
	
	public void setCount (String count) {
		this.count = count;
	}
}