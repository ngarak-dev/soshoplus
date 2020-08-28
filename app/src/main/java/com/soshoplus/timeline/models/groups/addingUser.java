/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.models.groups;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.timeline.models.apiErrors;

import java.io.Serializable;

public class addingUser implements Serializable {

	@SerializedName("api_status")
	@Expose
	private int apiStatus;

	@SerializedName("message")
	@Expose
	private String message;

	@SerializedName("errors")
	@Expose
	private apiErrors errors;

	addingUser () {

	}

	public addingUser(int apiStatus, String message, apiErrors errors) {
		this.apiStatus = apiStatus;
		this.message = message;
		this.errors = errors;
	}

	public int getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(int apiStatus) {
		this.apiStatus = apiStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public apiErrors getErrors() {
		return errors;
	}

	public void setErrors(apiErrors errors) {
		this.errors = errors;
	}
}