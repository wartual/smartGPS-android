package com.smartgps.utils;

import java.text.DecimalFormat;

import android.location.Location;

public class ProjectConfig {

	public static int MAP_PADDING = 50;
	public static Location defaultLocation;
	public static DecimalFormat decimalFormat;
	public static int CLIENT_TIMEOUT = 10000;
	public static int MAP_ZOOM_POI = 15;
	public static final String FIRST_TIME = "first_time";
	public static final String GCM_REGISTRATION_ID = "registrationId";
	public static final String APPLICATION_ID = "680887826369"; 
	public static final int NUMBER_OF_ITEMS_TO_LOAD = 10;

	public static Location getDefaultLocation() {
		defaultLocation = new Location("");
		defaultLocation.setLatitude(46.05645);
		defaultLocation.setLongitude(14.50807);
		return defaultLocation;
	}

	public static DecimalFormat getDecimalFormat() {
		decimalFormat = new DecimalFormat("#.00");
		return decimalFormat;
	}
	
}
