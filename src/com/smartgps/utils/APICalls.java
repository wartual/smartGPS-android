package com.smartgps.utils;


public class APICalls {

	public static String SERVER_URL = "http://smartgps.somee.com/";
	public static String LOGIN = "api/user/loginUser";
	public static String REGISTER = "api/user/registerUser";
	
	public static String getLoginUrl(){
		return SERVER_URL + LOGIN;
	}
	
	public static String getRegisterUrl(){
		return SERVER_URL + REGISTER;
	}
}
