package com.soshoplus.timeline.models.groups;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.timeline.models.apiErrors;

public class group {

	@SerializedName("group_data")
	@Expose
	private groupInfo groupInfo;

	@SerializedName("api_status")
	@Expose
	private int apiStatus;
	
	@SerializedName("errors")
	@Expose
	private apiErrors errors;
	
	public group () {
	
	}
	
	public group (groupInfo groupInfo, int apiStatus, apiErrors errors) {
		this.groupInfo = groupInfo;
		this.apiStatus = apiStatus;
		this.errors = errors;
	}
	
	public groupInfo getGroupInfo () {
		return groupInfo;
	}
	
	public void setGroupInfo (groupInfo groupInfo) {
		this.groupInfo = groupInfo;
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