package com.smartgps.models;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import android.location.Location;

public class SmartDestinationModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("departureLatitude")
	private double latitude;
	
	@SerializedName("departureLongitude")
	private double longitude;
	
	@SerializedName("destinationLatitude")
	private double destinationLatitude;
	
	@SerializedName("destinationLongitude")
	private double destinationLongitude;
	
	@SerializedName("destinationAddress")
	private String destinationAddress;
	
	@SerializedName("departureAddress")
	private String address;
	
	@SerializedName("country")
	private String country;
	
	public SmartDestinationModel(){
		
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getDestinationLatitude() {
		return destinationLatitude;
	}

	public void setDestinationLatitude(double destinationLatitude) {
		this.destinationLatitude = destinationLatitude;
	}

	public double getDestinationLongitude() {
		return destinationLongitude;
	}

	public void setDestinationLongitude(double destinationLongitude) {
		this.destinationLongitude = destinationLongitude;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
}
