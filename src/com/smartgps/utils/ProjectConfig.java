package com.smartgps.utils;

import java.text.DecimalFormat;

import android.location.Location;

public class ProjectConfig {

	public static int MAP_PADDING = 50;
	public static Location defaultLocation;
	public static DecimalFormat decimalFormat;
	public static int CLIENT_TIMEOUT = 10000;
	public static int MAP_ZOOM_POI = 15;

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
