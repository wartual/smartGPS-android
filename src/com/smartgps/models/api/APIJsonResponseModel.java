package com.smartgps.models.api;

import com.google.gson.annotations.SerializedName;

public class APIJsonResponseModel {

	@SerializedName("status")
	private String status;
	
	@SerializedName("message")
	private String message;
	
	public APIJsonResponseModel(){
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return getStatus() + ", " + getMessage();
	}
	
	
}
