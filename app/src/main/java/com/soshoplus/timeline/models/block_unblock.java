package com.soshoplus.timeline.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class block_unblock implements Serializable {

	@SerializedName("block_status")
	@Expose
	private String blockStatus;

	@SerializedName("api_status")
	@Expose
	private int apiStatus;
	
	@SerializedName("errors")
	@Expose
	private apiErrors errors;
	
	block_unblock () {
	
	}
	
	public block_unblock (String blockStatus, int apiStatus, apiErrors errors) {
		this.blockStatus = blockStatus;
		this.apiStatus = apiStatus;
		this.errors = errors;
	}
	
	public String getBlockStatus () {
		return blockStatus;
	}
	
	public void setBlockStatus (String blockStatus) {
		this.blockStatus = blockStatus;
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