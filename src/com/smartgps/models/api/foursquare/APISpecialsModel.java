package com.smartgps.models.api.foursquare;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class APISpecialsModel  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("count")
	private int count;
	
	@SerializedName("Items")
	private List<APIItemModel> items; 
	
	public APISpecialsModel(){
		
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<APIItemModel> getItems() {
		return items;
	}

	public void setItems(List<APIItemModel> items) {
		this.items = items;
	}
	
	
}
