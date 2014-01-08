package com.smartgps.models.api.foursquare;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class APITipsModel  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	public APITipsModel(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public String getCanonicalUrl() {
		return canonicalUrl;
	}

	public void setCanonicalUrl(String canonicalUrl) {
		this.canonicalUrl = canonicalUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public APIUserModel getUser() {
		return user;
	}

	public void setUser(APIUserModel user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
