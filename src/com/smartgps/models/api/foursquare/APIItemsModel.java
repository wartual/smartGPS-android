package com.smartgps.models.api.foursquare;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class APIItemsModel {

	@SerializedName("venue")
	private APIVenueModel venue;
	
	@SerializedName("tips")
	private List<APITipsModel> tips;
	
	@SerializedName("referralId")
	private String referralId;
	
	public APIItemsModel(){
		
	}

	public APIVenueModel getVenue() {
		return venue;
	}

	public void setVenue(APIVenueModel venue) {
		this.venue = venue;
	}

	public List<APITipsModel> getTips() {
		return tips;
	}

	public void setTips(List<APITipsModel> tips) {
		this.tips = tips;
	}

	public String getReferralId() {
		return referralId;
	}

	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}
	
	
	
}
