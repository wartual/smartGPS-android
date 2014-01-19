package com.smartgps.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

@Table(name="NotifyCategories")
public class APINotificationCategories extends Model{

	@SerializedName("id")
	@Column(name="CategoryId")
	private String categoryId;
	
	@SerializedName("type")
	@Column(name="Type")
	private String Type;
	
	public APINotificationCategories(){
		super();
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}
	
	
}
