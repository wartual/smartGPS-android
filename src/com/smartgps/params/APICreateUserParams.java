package com.smartgps.params;

public class APICreateUserParams extends  BaseParams{
	
	private final String USERNAME = "username";
	private final String PASSWORD = "password";
	private final String NAME = "name";
	private final String SURNAME = "surname";

	public APICreateUserParams(){
		
	}
	
	public void setUsername(String username){
		params.put(USERNAME, username);
	}
	
	public void setPassword(String password){
		params.put(PASSWORD, password);
	}

	public void setName(String name){
		params.put(NAME, name);
	}
	
	public void setSurname(String surname){
		params.put(SURNAME, surname);
	}
}
