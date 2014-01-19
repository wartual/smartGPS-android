package com.smartgps.params;

public class APICreateTravelParams extends BaseParams{

	private final String USER_ID = "userId";
	private final String ID = "id";
	private final String DEPARTURE_ADDRESS = "departureAddress";
	private final String DEPARTURE_LATITUDE = "departureLatitude";
	private final String DEPARTURE_LONGITUDE = "departureLongitude";
	private final String DESTINATION_ADDRESS = "destinationAddress";
	private final String DESTINATION_LATITUDE = "destinationLatitude";
	private final String DESTINATION_LONGITUDE = "destinationLongitude";
	private final String CURRENT_LATITUDE = "currentLatitude";
	private final String CURRENT_LONGITUDE = "currentLongitude";
	private final String STATUS_ID = "statusId";
	private final String TIME = "time";
	private final String DISTANCE = "distance";
	
	public void setUserId(String userId){
		params.put(USER_ID, userId);
	}
	
	public void setId(String id){
		params.put(ID, id);
	}
	
	public void setDepartureAddress(String departureAddress){
		params.put(DEPARTURE_ADDRESS, departureAddress);
	}
	
	public void setDepartureLatitude(double departureLatitude){
		params.put(DEPARTURE_LATITUDE, departureLatitude + "");
	}
	
	public void setDepartureLongitude(double departudeLongitude){
		params.put(DEPARTURE_LONGITUDE, departudeLongitude + "");
	}
	
	public void setDestinationAddress(String destinationAddress){
		params.put(DESTINATION_ADDRESS, destinationAddress);
	}
	
	public void setDestinationLatitude(double destinationLatitude){
		params.put(DESTINATION_LATITUDE, destinationLatitude+"");
	}
	
	public void setDestinationLongitude(double destinationLongitude){
		params.put(DESTINATION_LONGITUDE, destinationLongitude + "");
	}
	
	
	public void setCurrentLatitude(double currentLatitude){
		params.put(CURRENT_LATITUDE, currentLatitude+"");
	}
	
	public void setCurrentLongitude(double currentLongitude){
		params.put(CURRENT_LONGITUDE, currentLongitude+"");
	}
	
	public void setStatusId(String statusId){
		params.put(STATUS_ID, statusId);
	}
	
	public void setTime(double time){
		params.put(TIME, time + "");
	}
	
	public void setDistance(double distance){
		params.put(DISTANCE, distance+"");
	}
}
