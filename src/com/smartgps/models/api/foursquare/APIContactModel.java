package com.smartgps.models.api.foursquare;

import com.google.gson.annotations.SerializedName;

public class APIContactModel {

	@SerializedName("phone")
	private String phone;
	
	@SerializedName("formattedPhone")
	private String formattedPhone;
	
	@SerializedName("twitter")
	private String twitter;
	
	public APIContactModel(){
		
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFormattedPhone() {
		return formattedPhone;
	}

	public void setFormattedPhone(String formattedPhone) {
		this.formattedPhone = formattedPhone;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	
	
}
