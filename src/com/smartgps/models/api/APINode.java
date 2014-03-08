package com.smartgps.models.api;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class APINode implements Serializable{

	public static final String FOURSQUARE = "foursquare";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("latitude")
	private double latitude;
	
	@SerializedName("longitude")
	private double longitude;
	
	@SerializedName("id")
	private int id;
	
	@SerializedName("type")
	private String type;
	
	@SerializedName("title")
	private String title;
	
	@SerializedName("category")
	private String category;
	
	public APINode(){
		
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
