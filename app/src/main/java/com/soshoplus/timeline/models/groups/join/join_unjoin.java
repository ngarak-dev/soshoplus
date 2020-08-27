/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.models.groups.join;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.timeline.models.apiErrors;

import java.io.Serializable;

public class join_unjoin implements Serializable {

	@SerializedName("api_status")
	@Expose
	private int apiStatus;

	@SerializedName("join_status")
	@Expose
	private String joinStatus;
	
	@SerializedName("errors")
	@Expose
	private apiErrors errors;
	
	join_unjoin () {
	
	}
	
	public join_unjoin (int apiStatus, String joinStatus, apiErrors errors) {
		this.apiStatus = apiStatus;
		this.joinStatus = joinStatus;
		this.errors = errors;
	}
	
	public int getApiStatus () {
		return apiStatus;
	}
	
	public void setApiStatus (int apiStatus) {
		this.apiStatus = apiStatus;
	}
	
	public String getJoinStatus () {
		return joinStatus;
	}
	
	public void setJoinStatus (String joinStatus) {
		this.joinStatus = joinStatus;
	}
	
	public apiErrors getErrors () {
		return errors;
	}
	
	public void setErrors (apiErrors errors) {
		this.errors = errors;
	}
}