package com.soshoplus.timeline.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class postAction implements Serializable {

	@SerializedName("code")
	@Expose
	private int code;

	@SerializedName("action")
	@Expose
	private String action;

	@SerializedName("api_status")
	@Expose
	private int apiStatus;
	
	@SerializedName("errors")
	@Expose
	private apiErrors errors;
	
	postAction () {
	
	}
	
	public postAction (int code, String action, int apiStatus, apiErrors errors) {
		this.code = code;
		this.action = action;
		this.apiStatus = apiStatus;
		this.errors = errors;
	}
	
	public int getCode () {
		return code;
	}
	
	public void setCode (int code) {
		this.code = code;
	}
	
	public String getAction () {
		return action;
	}
	
	public void setAction (String action) {
		this.action = action;
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