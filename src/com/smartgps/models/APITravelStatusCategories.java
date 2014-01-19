package com.smartgps.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

@Table(name="TravelStatus")
public class APITravelStatusCategories extends Model{
	
	public static final String ACTIVE = "Active";
	public static final String PAUSED = "Paused";
	public static final String FINISHED = "Finished";

	@SerializedName("id")
	@Column(name="StatusId")
	private String statusId;
	
	@SerializedName("type")
	@Column(name="Type")
	private String type;
	
	public APITravelStatusCategories(){
		super();
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
