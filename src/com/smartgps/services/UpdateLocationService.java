package com.smartgps.services;

import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartgps.models.api.APIJsonResponseModel;
import com.smartgps.params.APIUpdateTravelCurrentLocationParams;
import com.smartgps.utils.APICalls;
import com.smartgps.utils.Utilities;

public class UpdateLocationService extends Service implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	public static final String USER_ID = "user_id";
	public static final String TRAVEL_ID = "travel_id";
	private String userId;
	private String travelId;
	private AsyncHttpClient client;
	private APIJsonResponseModel response;
	private LocationClient locationClient;
	private Location currentLocation;
	private LocationRequest mLocationRequest;
	private LocationManager locationManager;

	private static final long UPDATE_INTERVAL = 5000;
	private static final int FASTEST_INTERVAL = 5000;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("UPDATE LOCATION SERVICE", "ON START");
		client = new AsyncHttpClient();
		userId = intent.getStringExtra(USER_ID);
		travelId = intent.getStringExtra(TRAVEL_ID);
		locationClient = new LocationClient(this, this, this);
		mLocationRequest = LocationRequest.create();

		// Use high accuracy
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(UPDATE_INTERVAL);

		locationClient.connect();
		// startLocationUpdate();
		return Service.START_NOT_STICKY;
	}

	private void reportNewLocationToServer(Location lastLocation) {
		Log.d("UPDATE LOCATION SERVICE", "REPORT NEW LOCATION");
		String url = APICalls.getUpdateTravelCurrentLocationUrl();
		
		if(Utilities.checkInternetConnection(UpdateLocationService.this)){
			APIUpdateTravelCurrentLocationParams params = new APIUpdateTravelCurrentLocationParams();
			params.setLatitude(lastLocation.getLatitude());
			params.setLongitude(lastLocation.getLongitude());
			params.setTravelId(travelId);
			params.setUserId(userId);
			
			Log.d("LATITUDE", lastLocation.getLatitude()+"");

			Log.d("LATITUDE", lastLocation.getLongitude()+"");
			Log.d("LATITUDE", travelId);
			Log.d("LATITUDE", userId);
			client.put(APICalls.getUpdateTravelCurrentLocationUrl(), params.getRequestParams() ,new JsonHttpResponseHandler(){
				
				@Override
				public void onFailure(Throwable error, String content) {
					Log.d("FAILED TO UPDATE LOCATION", "API CLIENT ON FAILURE");
				}

				@Override
				public void onSuccess(JSONObject json) {
					GsonBuilder gsonBuilder = new GsonBuilder();
					gsonBuilder.setDateFormat("M/d/yy hh:mm a");
					Gson gson = gsonBuilder.create();
					String reader = json.toString();
					response = gson.fromJson(reader, APIJsonResponseModel.class);
					Log.d("RESPONSE FROM UPDATE LOCATION", response.getMessage());
			
				}
			});
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0) {
		if (locationClient.getLastLocation() != null) {
			currentLocation = locationClient.getLastLocation();
		}
		else{
			locationManager = (LocationManager)
					 getSystemService(Context.LOCATION_SERVICE);
			currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		
		if(currentLocation != null){
			reportNewLocationToServer(currentLocation);
		}
		locationClient.requestLocationUpdates(mLocationRequest, this);
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d("UPDATE LOCATION SERVICE", "LOCATION CHANGE");
//		if(currentLocation == null){
//			reportNewLocationToServer(location);
//		}
//		else if (Utilities.getDistance(currentLocation, location) > 20) {
			reportNewLocationToServer(currentLocation);
//		}
		
		currentLocation = location;
	}
}
