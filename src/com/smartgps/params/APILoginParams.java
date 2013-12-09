package com.smartgps.params;


public class APILoginParams extends BaseParams{
	
	private final String USERNAME = "username";
	private final String PASSWORD = "password";

	public APILoginParams(){
		
	}
	
	public void setUsername(String username){
		params.put(USERNAME, username);
	}
	
	public void setPassword(String password){
		params.put(PASSWORD, password);
	}
}
