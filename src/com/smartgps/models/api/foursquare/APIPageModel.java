package com.smartgps.models.api.foursquare;

import com.google.gson.annotations.SerializedName;

public class APIPageModel {

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
