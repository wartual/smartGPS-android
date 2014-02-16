package com.smartgps.params;

public class APIRateNotification extends BaseParams{
	
	private final String USER_ID = "userId";
	private final String NOTIFICATION_ID = "notificationId";
	private final String THUMBS_UP = "thumbsUp";
	private final String THUMBS_DOWN = "thumbsDown";

	public APIRateNotification(){
		
	}
	
	public void setUserId(String userId){
		params.put(USER_ID, userId);
	}
	
	public void setNotificationId(String notificationId){
		params.put(NOTIFICATION_ID, notificationId);
	}
	
	public void setThumbsUp(Boolean thumbsUp){
		params.put(THUMBS_UP, thumbsUp + "");
	}
	
	public void setThumbsDown(Boolean thumbsDown){
		params.put(THUMBS_DOWN, thumbsDown + "");
	}
}
