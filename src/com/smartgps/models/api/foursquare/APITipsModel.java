package com.smartgps.models.api.foursquare;

import com.google.gson.annotations.SerializedName;

public class APITipsModel {

	@SerializedName("id")
	private String id;
	
	@SerializedName("createdAt")
	private long createdAt;
	
	@SerializedName("canonicalUrl")
	private String canonicalUrl;
	
	@SerializedName("text")
	private String text;
	
	@SerializedName("user")
	private APIUserModel user;
}
