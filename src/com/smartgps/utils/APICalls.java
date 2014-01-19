package com.smartgps.utils;


public class APICalls {

	private static String SERVER_URL = "http://smart-gps.somee.com/";
	private static String LOGIN = "api/user/loginUser";
	private static String REGISTER = "api/user/registerUser";
	private static String GET_PROFILE = "api/user/getUser";
	private static String UPDATE_PROFILE = "api/user/updateProfile";
	private static String GET_EVENTS = "api/events/getEvents";
	private static String GET_PLACES = "api/places/getPlaces";
	private static String GET_NOTIFICATION_CATEGORIES = "api/notification/getNotificationCategories";
	private static String NEW_NOTIFICATION = "api/notification/createNotification";
	private static String UPDATE_GCM_ID = "api/notification/updateGcmId";
	private static String GET_TRAVEL_STATUS_CATEGORIES = "api/travel/getTravelStatusCategories";
	private static String NEW_TRAVEL = "api/travel/newTravel";
	private static String UPDATE_TRAVEL_CURRENT_LOCATION = "api/travel/updateTravelsCurrentLocation";
	private static String UPDATE_TRAVEL_STATUS = "api/travel/updateTravelStatus";
	private static String GET_NOTIFICATIONS_NEAR_LOCATIONS = "api/notification/getNotificationsNearLocation";
	private static String GET_TRAVELS_FOR_USER = "api/travel/getTravelsForUser";
	private static String DEACTIVATE_NOTIFICATION = "api/notification/deactivateNotification";
	
	public static String getLoginUrl(){
		return SERVER_URL + LOGIN;
	}
	
	public static String getRegisterUrl(){
		return SERVER_URL + REGISTER;
	}
	
	public static String getProfileUrl(String userId){
		return SERVER_URL + GET_PROFILE + "?userId=" + userId;
	}
	
	public static String getUpadateProfileUrl(){
		return SERVER_URL + UPDATE_PROFILE;
	}
	
	public static String getPlacesUrl(String userId, double latitude, double longitude, int num){
		return SERVER_URL + GET_PLACES + "?userId=" + userId + "&latitude=" + latitude + "&longitude=" + longitude + "&num=" + num;
	}
	
	public static String getEventsUrl(String userId, double latitude, double longitude, int num){
		return SERVER_URL + GET_EVENTS + "?userId=" + userId + "&latitude=" + latitude + "&longitude=" + longitude + "&num=" + num;
	}
	
	public static String getNotificationCategoriesUrl(String userId){
		return SERVER_URL + GET_NOTIFICATION_CATEGORIES + "?userId=" + userId;
	}
	
	public static String getNewNotificationUrl(){
		return SERVER_URL + NEW_NOTIFICATION;
	}
	
	public static String getUpdateGcmId(){
		return SERVER_URL + UPDATE_GCM_ID;
	}
	
	public static String getTravelStatusCategoriesUrl(String userId){
		return SERVER_URL + GET_TRAVEL_STATUS_CATEGORIES + "?userId=" + userId;
	}
	
	public static String getNewTravelUrl(){
		return SERVER_URL + NEW_TRAVEL;
	}
	
	public static String getUpdateTravelCurrentLocationUrl(){
		return SERVER_URL + UPDATE_TRAVEL_CURRENT_LOCATION;
	}
	
	public static String getUpdateTravelStatusUrl(){
		return SERVER_URL + UPDATE_TRAVEL_STATUS;
	}
	
	public static String getNotificationsNearLocation(String userId, double latitude, double longitude){
		return SERVER_URL + GET_NOTIFICATIONS_NEAR_LOCATIONS + "?userId=" + userId + "&latitude=" + latitude + "&longitude=" + longitude;
	}
	
	public static String getTravelsForUser(String userId){
		return SERVER_URL + GET_TRAVELS_FOR_USER + "?userId=" + userId;
	}
	
	public static String getDeactivateNotificationUrl(){
		return SERVER_URL + DEACTIVATE_NOTIFICATION;
	}
}
