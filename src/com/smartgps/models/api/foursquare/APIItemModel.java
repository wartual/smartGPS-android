package com.smartgps.models.api.foursquare;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class APIItemModel  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	private String id;
	
	@SerializedName("type")
	private String type;
	
	@SerializedName("message")
	private String message;
	
	@SerializedName("description")
	private String description;
	
	@SerializedName("title")
	private String title;
	
	@SerializedName("page")
	private APIPageModel page;
	
	public APIItemModel(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public APIPageModel getPage() {
		return page;
	}

	public void setPage(APIPageModel page) {
		this.page = page;
	}
	
	
}
