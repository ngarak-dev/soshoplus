/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models.postsfeed.sharepost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.lite.models.apiErrors;

import java.io.Serializable;

public class shareResponse implements Serializable {

	@SerializedName("data")
	@Expose
	private shareData data;

	@SerializedName("api_status")
	@Expose
	private int apiStatus;
	
	@SerializedName("errors")
	@Expose
	private apiErrors errors;
	
	shareResponse () {
	
	}
	
	public shareResponse (shareData data, int apiStatus, apiErrors errors) {
		this.data = data;
		this.apiStatus = apiStatus;
		this.errors = errors;
	}
	
	public shareData getData () {
		return data;
	}
	
	public void setData (shareData data) {
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