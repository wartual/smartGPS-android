package com.smartgps.models;

import com.google.gson.annotations.SerializedName;

public class APINotificationsModel {
	
	@SerializedName("notificationId")
	private String notificationId;

	@SerializedName("userId")
	private String userId;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("longitude")
	private double longitude;

	@SerializedName("text")
	private String text;

	@SerializedName("category")
	private String category;

	@SerializedName("dateCreated")
	private long dateCreated;
	
	@SerializedName("username")
	private String username;
	
	@SerializedName("address")
	private String address;
	
	
	public APINotificationsModel(){
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
}
