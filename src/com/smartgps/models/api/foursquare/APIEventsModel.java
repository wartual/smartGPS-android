package com.smartgps.models.api.foursquare;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class APIEventsModel {
	
	@SerializedName("type")
	private String type;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("items")
	private List<APIItemsModel> items;
	
	public APIEventsModel(){
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<APIItemsModel> getItems() {
		return items;
	}

	public void setItems(List<APIItemsModel> items) {
		this.items = items;
	}
	
	

}
