package com.smartgps.models.api.foursquare;

import com.google.gson.annotations.SerializedName;

public class APIUserModel {
	
	@SerializedName("id")
	private String id;
	
	@SerializedName("firstName")
	private String firstName;
	
	@SerializedName("gender")
	private String gender;
	
	@SerializedName("photo")
	private APIIconModel photo;
	
	public APIUserModel(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public APIIconModel getPhoto() {
		return photo;
	}

	public void setPhoto(APIIconModel photo) {
		this.photo = photo;
	}

	
}
