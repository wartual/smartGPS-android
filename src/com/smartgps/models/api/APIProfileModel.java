package com.smartgps.models.api;

import com.google.gson.annotations.SerializedName;

public class APIProfileModel {
	
	public APIProfileModel(){
		
	}

	@SerializedName("userId")
	private String userId;

	@SerializedName("username")
	private String username;

	@SerializedName("dateOfBirth")
	private long dateOfBirth;

	@SerializedName("gender")
	private Boolean gender;

	@SerializedName("address")
	private String address;

	@SerializedName("postalOffice")
	private String postalOffice;

	@SerializedName("email")
	private String email;

	@SerializedName("country")
	private String country;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("surname")
	private String surname;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalOffice() {
		return postalOffice;
	}

	public void setPostalOffice(String postalOffice) {
		this.postalOffice = postalOffice;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
}
