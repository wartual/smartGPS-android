package com.smartgps.params;

public class APIDeactivateNotificationParams extends BaseParams{
	
	private final String USER_ID = "userId";
	private final String NOTIFICATION_ID = "notificationId";

	public APIDeactivateNotificationParams(){
		
	}
	
	public void setUserId(String userId){
		params.put(USER_ID, userId);
	}
	
	public void setNotificationId(String notificationId){
		params.put(NOTIFICATION_ID, notificationId);
	}

}
