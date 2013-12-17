package com.smartgps.params;

import java.util.Date;

import android.text.Editable;

public class APIUpdateProfileParams extends BaseParams{
	
	private final String USER_ID = "userId";
	private final String USERNAME = "username";
	private final String DATE_OF_BIRTH = "dateofBirth";
	private final String GENDER = "gender";
	private final String ADDRESS = "address";
	private final String POSTAL_OFFICE = "postalOffice";
	private final String COUNTRY = "country";
	private final String EMAIL = "email";
	private final String PHONE = "phone";
	private final String NAME = "name";
	private final String SURNAME = "surname";
	

	public APIUpdateProfileParams(){
		
	}
	
	public void setUserId(String userId){
		params.put(USER_ID, userId);
	}
	
	public void setUsername(String username){
		params.put(USERNAME, username);
	}
	
	public void setDateOfBirth(long dateOfBirth){
		params.put(DATE_OF_BIRTH, dateOfBirth + "");
	}
	
	public void setDateOfBirth(Date dateOfBirth){
		params.put(DATE_OF_BIRTH, dateOfBirth.getTime() + "");
	}
	
	public void setGender(int gender){
		params.put(GENDER, gender + "");
	}
	
	public void setAddress(Editable address){
		if(address != null){
			params.put(ADDRESS, address.toString());	
		}
		else{
			params.put(ADDRESS, EMPTY_STRING);
		}
	}
	
	public void setCountry(String country){
		params.put(COUNTRY, country);
	}
	
	public void setPostalOffice(String postalOffice){
		params.put(POSTAL_OFFICE, postalOffice);
	}
	
	public void setEmail(String email){
		params.put(EMAIL, email);
	}
	
	public void setPhone(String phone){
		params.put(PHONE, phone);
	}
	
	public void setName(String name){
		params.put(NAME, name);
	}
	
	public void setSurname(String surname){
		params.put(SURNAME, surname);
	}
}
