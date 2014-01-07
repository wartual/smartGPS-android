package com.smartgps.models.api.foursquare;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class APIVenueModel {

	@SerializedName("id")
	private String id;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("location")
	private APILocationModel location;
	
	@SerializedName("categories")
	private List<APICategoryModel> categories;
	
	@SerializedName("verified")
	private boolean verified;
	
	@SerializedName("referralId")
	private String referralId;
	
	@SerializedName("url")
	private String url;
	
	@SerializedName("contact")
	private APIContactModel contact;
	
	@SerializedName("rating")
	private double rating;
	
	@SerializedName("hours")
	private APIHoursModel hours;
	
	@SerializedName("specials")
	private APISpecialsModel specials;

	public APIVenueModel(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public APILocationModel getLocation() {
		return location;
	}

	public void setLocation(APILocationModel location) {
		this.location = location;
	}

	public List<APICategoryModel> getCategories() {
		return categories;
	}

	public void setCategories(List<APICategoryModel> categories) {
		this.categories = categories;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getReferralId() {
		return referralId;
	}

	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public APIContactModel getContact() {
		return contact;
	}

	public void setContact(APIContactModel contact) {
		this.contact = contact;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public APIHoursModel getHours() {
		return hours;
	}

	public void setHours(APIHoursModel hours) {
		this.hours = hours;
	}

	public APISpecialsModel getSpecials() {
		return specials;
	}

	public void setSpecials(APISpecialsModel specials) {
		this.specials = specials;
	}
	
	
}
