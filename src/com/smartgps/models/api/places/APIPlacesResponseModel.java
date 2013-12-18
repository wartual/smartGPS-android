package com.smartgps.models.api.places;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class APIPlacesResponseModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public APIPlacesResponseModel(){
		
	}

	@SerializedName("icon")
	private String icon;
	
	@SerializedName("geometry")
	private APIGeometryModel geometry;
	
	@SerializedName("id")
	private String id;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("reference")
	private String reference;
	
	@SerializedName("vicinity")
	private String vicinity;
	
	@SerializedName("types")
	private List<String> types;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public APIGeometryModel getGeometry() {
		return geometry;
	}

	public void setGeometry(APIGeometryModel geometry) {
		this.geometry = geometry;
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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}
}
