package com.smartgps.models.api.foursquare;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class APIIconModel  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
