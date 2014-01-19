package com.smartgps.params;

public class APICreateNotificationParams extends BaseParams{

	private final String USER_ID = "userId";
	private final String TEXT = "text";
	private final String CATEGORY = "category";
	private final String LATITUDE = "latitude";
	private final String LONGITUDE = "longitude";
	private final String ADDRESS = "address";
	
	public APICreateNotificationParams(){
		
	}
	
	public void setUserId(String userId){
		params.put(USER_ID, userId);
	}
	
	public void setText(String text){
		params.put(TEXT, text);
	}

	public void setCategory(String category){
		params.put(CATEGORY, category);
	}
	
	public void setLatitude(double latitude){
		params.put(LATITUDE, latitude + "");
	}
	
	public void setLongitude(double longitude){
		params.put(LONGITUDE, longitude + "");
	}
	
	public void setAddress(String address){
		params.put(ADDRESS, address);
	}
}
