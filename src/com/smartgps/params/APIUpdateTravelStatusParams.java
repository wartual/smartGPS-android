package com.smartgps.params;

public class APIUpdateTravelStatusParams extends BaseParams {
	
	private final String USER_ID = "userId";
	private final String TRAVEL_ID = "travelId";
	private final String TRAVEL_STATUS_ID = "travelStatusId";

	public APIUpdateTravelStatusParams() {

	}

	public void setUserId(String userId) {
		params.put(USER_ID, userId);
	}

	public void setTravelId(String travelId) {
		params.put(TRAVEL_ID, travelId);
	}

	public void setTravelStatusId(String travelStatusId) {
		params.put(TRAVEL_STATUS_ID, travelStatusId);
	}
}
