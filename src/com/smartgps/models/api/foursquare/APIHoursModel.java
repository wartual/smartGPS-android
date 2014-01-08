package com.smartgps.models.api.foursquare;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class APIHoursModel  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
