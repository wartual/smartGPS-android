package com.smartgps.models.api.foursquare;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class APIPageModel  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("photo")
	private APIIconModel photo;
	
	@SerializedName("contact")
	private APIContactModel contact;
	
	@SerializedName("homecity")
	private String homecity;
	
	public APIPageModel(){
		
	}

	public APIIconModel getPhoto() {
		return photo;
	}

	public void setPhoto(APIIconModel photo) {
		this.photo = photo;
	}

	public APIContactModel getContact() {
		return contact;
	}

	public void setContact(APIContactModel contact) {
		this.contact = contact;
	}

	public String getHomecity() {
		return homecity;
	}

	public void setHomecity(String homecity) {
		this.homecity = homecity;
	}
	
	
	
}
