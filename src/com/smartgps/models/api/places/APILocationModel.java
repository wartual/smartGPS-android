package com.smartgps.models.api.places;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class APILocationModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public APILocationModel(){
		
	}

	@SerializedName("lat")
	private double latitude;
	
	@SerializedName("lng")
	private double longitude;

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
}
