package com.soshoplus.timeline.models.groups;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.timeline.models.apiErrors;

import java.util.List;

public class groupList {

	@SerializedName("data")
	@Expose
	private List<groupInfo> data;

	@SerializedName("api_status")
	@Expose
	private int apiStatus;
	
	@SerializedName("errors")
	@Expose
	private apiErrors errors;
	
	public groupList () {
	
	}
	
	public groupList (List<groupInfo> data, int apiStatus, apiErrors errors) {
		this.data = data;
		this.apiStatus = apiStatus;
		this.errors = errors;
	}
	
	public List<groupInfo> getData () {
		return data;
	}
	
	public void setData (List<groupInfo> data) {
		this.data = data;
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