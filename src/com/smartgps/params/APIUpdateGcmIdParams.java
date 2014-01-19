package com.smartgps.params;

public class APIUpdateGcmIdParams extends BaseParams{

	private final String USER_ID = "userId";
	private final String GCM_ID = "gcmId";
	
	public APIUpdateGcmIdParams(){
		
	}
	
	public void setUserId(String userId){
		params.put(USER_ID, userId);
	}
	
	public void setGcmId(String gcmId){
		params.put(GCM_ID, gcmId);
	}
}
