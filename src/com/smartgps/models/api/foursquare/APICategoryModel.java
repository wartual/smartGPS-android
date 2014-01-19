package com.smartgps.models.api.foursquare;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class APICategoryModel  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	private String id;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("pluralName")
	private String pluralName;
	
	@SerializedName("shortName")
	private String shortName;
	
	@SerializedName("primary")
	private boolean primary;
	
	@SerializedName("icon")
	private APIIconModel icon;
	
	public APICategoryModel(){
		
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

	public String getPluralName() {
		return pluralName;
	}

	public void setPluralName(String pluralName) {
		this.pluralName = pluralName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public APIIconModel getIcon() {
		return icon;
	}

	public void setIcon(APIIconModel icon) {
		this.icon = icon;
	}
	
	
}
