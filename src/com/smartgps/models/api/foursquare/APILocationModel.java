package com.smartgps.models.api.foursquare;

import com.google.gson.annotations.SerializedName;

public class APILocationModel {

	@SerializedName("address")
	private String address;
	
	@SerializedName("lat")
	private double latitude;
	
	@SerializedName("lng")
	private double longitude;
	
	@SerializedName("distance")
	private double distance;
	
	@SerializedName("postalCode")
	private String postalCode;
	
	@SerializedName("cc")
	private String countryCode;
	
	@SerializedName("city")
	private String city;
	
	@SerializedName("state")
	private String state;
	
	@SerializedName("country")
	private String country;
	
	public APILocationModel(){
		
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
