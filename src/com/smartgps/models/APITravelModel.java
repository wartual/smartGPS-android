package com.smartgps.models;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

@Table(name = "Travel")
public class APITravelModel extends Model implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	@Column(name="TravelId")
	private String travelId;

	@SerializedName("userId")
	@Column(name="UserId")
	private String userId;

	@SerializedName("departureAddress")
	@Column(name="DepartureAddress")
	private String departureAddress;

	@SerializedName("departureLatitude")
	@Column(name="DepartureLatitude")
	private double departureLatitude;

	@SerializedName("departureLongitude")
	@Column(name="DepartureLongitude")
	private double departureLongitude;

	@SerializedName("destinationAddress")
	@Column(name="DestinationAddress")
	private String destinationAddress;

	@SerializedName("destinationLatitude")
	@Column(name="DestinationLatitude")
	private double destinationLatitude;

	@SerializedName("destinationLongitude")
	@Column(name="DestinationLongitude")
	private double destinationLongitude;

	@SerializedName("currentLatitude")
	@Column(name="CurrentLatitude")
	private double currentLatitude;

	@SerializedName("currentLongitude")
	@Column(name="CurrentLongitude")
	private double currentLongitude;

	@SerializedName("time")
	@Column(name="Time")
	private double time;

	@SerializedName("distance")
	@Column(name="Distance")
	private double distance;

	@SerializedName("statusId")
	@Column(name="StatusId")
	private String statusId;
	
	@SerializedName("dateCreated")
	@Column(name="DateCrated")
	private long dateCreated;

	@SerializedName("dateUpdated")
	@Column(name="DateUpdated")
	private long dateUpdated;
	
	@SerializedName("directions")
	@Column(name="Directions")
	private String directions;
	
	public APITravelModel(){
		super();
	}

	public String getTravelId() {
		return travelId;
	}

	public void setTravelId(String travelId) {
		this.travelId = travelId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepartureAddress() {
		return departureAddress;
	}

	public void setDepartureAddress(String departureAddress) {
		this.departureAddress = departureAddress;
	}

	public double getDepartureLatitude() {
		return departureLatitude;
	}

	public void setDepartureLatitude(double departureLatitude) {
		this.departureLatitude = departureLatitude;
	}

	public double getDepartureLongitude() {
		return departureLongitude;
	}

	public void setDepartureLongitude(double departureLongitude) {
		this.departureLongitude = departureLongitude;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
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

	public double getCurrentLatitude() {
		return currentLatitude;
	}

	public void setCurrentLatitude(double currentLatitude) {
		this.currentLatitude = currentLatitude;
	}

	public double getCurrentLongitude() {
		return currentLongitude;
	}

	public void setCurrentLongitude(double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}

	public long getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(long dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}
	
	
}
