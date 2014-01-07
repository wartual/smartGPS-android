package com.smartgps.models.api.foursquare;

import com.google.gson.annotations.SerializedName;

public class APIHoursModel {

	@SerializedName("isOpen")
	private boolean isOpen;
	
	public APIHoursModel(){
		
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	
}
