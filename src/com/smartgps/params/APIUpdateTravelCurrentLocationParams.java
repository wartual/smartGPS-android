package com.smartgps.params;

public class APIUpdateTravelCurrentLocationParams extends BaseParams{
	
	private final String USER_ID = "userId";
	private final String TRAVEL_ID = "travelId";
	private final String LATITUDE = "latitude";
	private final String LONGITUDE = "longitude";

	public APIUpdateTravelCurrentLocationParams(){
		
	}
	
	public void setUserId(String userId){
		params.put(USER_ID, userId);
	}
	
	public void setTravelId(String travelId){
		params.put(TRAVEL_ID, travelId);
	}

	public void setLatitude(double latitude){
		params.put(LATITUDE, latitude+"");
	}
	
	public void setLongitude(double longitude){
		params.put(LONGITUDE, longitude+"");
	}
}
