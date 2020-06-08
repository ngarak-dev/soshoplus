package com.soshoplus.timeline.models.postsfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.timeline.models.apiErrors;

import java.io.Serializable;
import java.util.List;

public class postList implements Serializable {

	@SerializedName("data")
	private List<post> postList;

	@SerializedName("api_status")
	private int apiStatus;
	
	@SerializedName("errors")
	@Expose
	private apiErrors errors;
	
	public postList () {
	
	}
	
	public postList (List<post> postList, int apiStatus, apiErrors errors) {
		this.postList = postList;
		this.apiStatus = apiStatus;
		this.errors = errors;
	}
	
	public List<post> getPostList () {
		return postList;
	}
	
	public void setPostList (List<post> postList) {
		this.postList = postList;
	}
	
	public int getApiStatus () {
		return apiStatus;
	}
	
	public void setApiStatus (int apiStatus) {
		this.apiStatus = apiStatus;
	}
	
	public apiErrors getErrors () {
		return errors;
	}
	
	public void setErrors (apiErrors errors) {
		this.errors = errors;
	}
}