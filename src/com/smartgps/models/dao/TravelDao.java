package com.smartgps.models.dao;

import java.util.List;

import com.activeandroid.query.Select;
import com.smartgps.models.APITravelModel;
import com.smartgps.models.APITravelStatusCategories;

public class TravelDao {

	public static void addNew(APITravelModel model){
		model.save();
	}
	
	public static void createNew(String travelId, String userId, String departureAddress, double departureLatitude, 
							double departureLongitude, String destinationAddress, double destinationLatitude, double destinationLongitude,
							double currentLatitude, double currentLongitude, double time, double distance, String statusId, long dateCreated, long dateUpdated){
		APITravelModel model = new APITravelModel();
		
		model.setCurrentLatitude(currentLatitude);
		model.setCurrentLongitude(currentLongitude);
		model.setDepartureAddress(departureAddress);
		model.setDepartureLatitude(departureLatitude);
		model.setDepartureLongitude(departureLongitude);
		model.setDestinationAddress(destinationAddress);
		model.setDestinationLatitude(destinationLatitude);
		model.setDestinationLongitude(destinationLongitude);
		model.setDistance(distance);
		model.setStatusId(statusId);
		model.setTime(time);
		model.setTravelId(travelId);
		model.setUserId(userId);
		model.setDateCreated(dateCreated);
		model.setDateUpdated(dateUpdated);
		model.save();
	}
	
	public static List<APITravelModel> getAll(){
		return new Select().from(APITravelModel.class).execute();
	}
	
	public static APITravelModel getById(String travelId){
		return new Select().from(APITravelModel.class).where("TravelId = ?", travelId).executeSingle();
	}
	
	public static void updateCurrentLocation(String travelId, double latitude, double longitude, long dateUpdated){
		APITravelModel model = getById(travelId);
		
		model.setCurrentLatitude(latitude);
		model.setCurrentLongitude(longitude);
		model.setDateUpdated(dateUpdated);
		
		model.save();
	}
	
	public static void updateStatus(String travelId, String travelStatus, long dateUpdated){
		APITravelModel model = getById(travelId);
		model.setStatusId(travelStatus);
		model.setDateUpdated(dateUpdated);
		
		model.save();
	}
}
