package com.smartgps.models.api.places;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class APIGeometryModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public APIGeometryModel(){
		
	}
	
	@SerializedName("location")
	private APILocationModel location;

	public APILocationModel getLocation() {
		return location;
	}

	public void setLocation(APILocationModel location) {
		this.location = location;
	}
}
