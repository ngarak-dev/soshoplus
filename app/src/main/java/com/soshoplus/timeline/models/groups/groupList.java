package com.soshoplus.timeline.models.groups;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.timeline.models.apiErrors;

import java.util.List;

public class groupList {

	@SerializedName("data")
	@Expose
	private List<groupInfo> info;

	@SerializedName("api_status")
	@Expose
	private int apiStatus;
	
	@SerializedName("errors")
	@Expose
	private apiErrors errors;
	
	public groupList () {
	
	}
	
	public groupList (List<groupInfo> info, int apiStatus, apiErrors errors) {
		this.info = info;
		this.apiStatus = apiStatus;
		this.errors = errors;
	}
	
	public List<groupInfo> getInfo () {
		return info;
	}
	
	public void setInfo (List<groupInfo> info) {
		this.info = info;
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