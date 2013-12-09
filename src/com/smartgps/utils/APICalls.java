package com.smartgps.utils;

import java.net.URLEncoder;

public class APICalls {

	public static String SERVER_URL = "http://smartgps.somee.com/";
	public static String USER = "api/user";
	
	private static String USERNAME = "username";
	private static String PASSWORD = "password";
	
	public static String getLoginUrl(String username, String password){
		return SERVER_URL + USER + "?" + USERNAME + "=" + URLEncoder.encode(username) + "&" +
					PASSWORD + "=" + URLEncoder.encode(password);
	}
}
