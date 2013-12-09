package com.smartgps.params;

import com.loopj.android.http.RequestParams;

public class BaseParams {

	public RequestParams params = new RequestParams();
	
	public RequestParams getRequestParams(){
		return params; 
	}
}
