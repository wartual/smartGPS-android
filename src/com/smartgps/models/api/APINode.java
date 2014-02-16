package com.smartgps.models.api;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class APINode implements Serializable{

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
	
	
}
