package com.smartgps.models.api.foursquare;

import com.google.gson.annotations.SerializedName;

public class APIIconModel {

	@SerializedName("prefix")
	private String prefix;
	
	@SerializedName("sufx")
	private String sufx;
	
	public APIIconModel(){
		
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSufx() {
		return sufx;
	}

	public void setSufx(String sufx) {
		this.sufx = sufx;
	}
	
	
}
