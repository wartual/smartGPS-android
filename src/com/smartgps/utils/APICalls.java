package com.smartgps.utils;


public class APICalls {

	public static String SERVER_URL = "http://smartgps.somee.com/";
	public static String LOGIN = "api/user/loginUser";
	public static String REGISTER = "api/user/registerUser";
	public static String GET_PROFILE = "api/user/getUser";
	public static String UPDATE_PROFILE = "api/user/updateProfile";
	public static String GET_EVENTS = "api/events/getEvents";
	public static String GET_PLACES = "api/places/getPlaces";
	
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
}
