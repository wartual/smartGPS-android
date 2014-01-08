package com.smartgps.utils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.smartgps.R;
import com.smartgps.activities.navigation.NavigationActivity;
import com.smartgps.activities.places.PlaceActivity;
import com.smartgps.models.SmartDestinationModel;
import com.smartgps.models.api.foursquare.APILocationModel;

public class Utilities {

	private static final int NUMBER_OF_DESTINATIONS = 10;

	public static ArrayList<SmartDestinationModel> getDestinationsFromAddress(
			Context context, String address) {
		Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
		ArrayList<SmartDestinationModel> list = new ArrayList<SmartDestinationModel>();

		List<Address> addresses;
		try {
			addresses = geoCoder.getFromLocationName(address,
					NUMBER_OF_DESTINATIONS);
			if (addresses.size() > 0) {
				SmartDestinationModel model = new SmartDestinationModel();
				String geoAddress = "";
				for (int i = 0; i < addresses.size(); i++) {
					for (int j = 0; j < addresses.get(i)
							.getMaxAddressLineIndex(); j++) {
						Log.d("J", j + "");
						geoAddress += addresses.get(i).getAddressLine(j);
						if (j != addresses.get(i).getMaxAddressLineIndex() - 1)
							geoAddress = geoAddress + ", ";
					}
					model.setAddress(geoAddress);
					model.setLatitude(addresses.get(i).getLatitude());
					model.setLongitude(addresses.get(i).getLongitude());
					model.setCountry(addresses.get(i).getCountryName());
					list.add(model);
					geoAddress = "";
				}
			} else {
				return null;
			}

		} catch (IOException e) {
			Log.d("Geocoder error", e.toString());
			return null;
		}

		return list;
	}

	public static SmartDestinationModel getDestinationFromGpsCoordinates(
			Context context, double latitude, double longitude) {

		if (latitude < -90 || latitude > 90 || longitude < -90
				|| longitude > 90)
			return null;

		Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
		List<Address> addresses;
		SmartDestinationModel model = new SmartDestinationModel();

		try {
			addresses = geoCoder.getFromLocation(latitude, longitude, 1);

			if (addresses.size() > 0) {
				String geoAddress = "";
				for (int i = 0; i < addresses.size(); i++) {
					for (int j = 0; j < addresses.get(i)
							.getMaxAddressLineIndex(); j++) {
						geoAddress += addresses.get(i).getAddressLine(j);
						if (j != addresses.get(i).getMaxAddressLineIndex() - 1)
							geoAddress = geoAddress + ", ";
					}
					model.setAddress(geoAddress);
					model.setLatitude(latitude);
					model.setLongitude(longitude);
					model.setCountry(addresses.get(i).getCountryName());
				}
			} else {
				return null;
			}

		} catch (IOException e) {
			Log.d("Geocoder error", e.toString());
			return null;
		}

		return model;
	}

	/**
	 * This method convets dp unit to equivalent device specific value in
	 * pixels.
	 * 
	 * @param dp
	 *            A value in dp(Device independent pixels) unit. Which we need
	 *            to convert into pixels
	 * @return An int value to represent Pixels equivalent to dp according to
	 *         device
	 */
	public static int dpToPixels(int dp, Context ctx) {
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				ctx.getResources().getDisplayMetrics());
		return Math.round(px);
	}

	public static double getDistance(Location firstLocation,
			Location secondLocation) {
		return firstLocation.distanceTo(secondLocation);
	}

	public static String formatDistance(double distance) {
		String output = "";
		DecimalFormat df = ProjectConfig.getDecimalFormat();
		
		if (distance > 1000) {
			distance = distance / 1000;
			output = df.format(distance) + " km";
		} else {
			output = df.format(distance) + " m";
		}

		return output;
	}

	public static String formatDuration(String duration) {
		try {
			double value = Double.parseDouble(duration);
			return formatDistance(duration);
		} catch (NumberFormatException e) {
			return "";
		}
	}

	public static String formatDuration(double duration) {
		String output = "";
		if (duration > 3600) {
			duration = duration / 3600;
			DecimalFormat df = ProjectConfig.getDecimalFormat();
			output = df.format(duration) + " h";
		} else if (duration > 60) {
			duration = duration / 60;
			DecimalFormat df = ProjectConfig.getDecimalFormat();
			output = df.format(duration) + " min";
		} else {
			output = duration + " sec";
		}

		return output;
	}

	public static String formatDistance(String distance) {
		try {
			double value = Double.parseDouble(distance);
			return formatDistance(distance);
		} catch (NumberFormatException e) {
			return "";
		}
	}

	public static boolean checkInternetConnection(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			return false;
		} else
			return true;
	}

	public static String formatText(String text){
		return text.replace("_", " ");
	}
	
	public static String firstLetterUpercase(String input) {
		input = input.toLowerCase();
		String[] arr = input.split(" ");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			sb.append(Character.toUpperCase(arr[i].charAt(0)))
					.append(arr[i].substring(1)).append(" ");
		}
		return sb.toString().trim();
	}

	public static boolean validateDate(Date date) {
		Date now = new Date();
		if (date.after(now)) {
			return false;
		} else {
			return true;
		}
	}
	
	public static Bitmap drawableToBitmap (Drawable drawable) {
	    if (drawable instanceof BitmapDrawable) {
	        return ((BitmapDrawable)drawable).getBitmap();
	    }

	    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap); 
	    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    drawable.draw(canvas);

	    return bitmap;
	}
	
	public static Drawable resize(Bitmap d , Activity activity) {
		Bitmap bitmapOrig = null;
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int bound = 20;
		
		switch (metrics.densityDpi) {
		case DisplayMetrics.DENSITY_LOW:
			bitmapOrig = Bitmap.createScaledBitmap(d, 20, 20, true);
			bound = 20;
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			bitmapOrig = Bitmap.createScaledBitmap(d, 40, 40, true);
			bound = 25;
			break;
		case DisplayMetrics.DENSITY_HIGH:
			bitmapOrig = Bitmap.createScaledBitmap(d, 60, 60, true);
			bound = 30;
			break;
		case 320:
			bitmapOrig = Bitmap.createScaledBitmap(d, 100, 100, true);
			bound = 35;
			break;
		default:
			bitmapOrig = Bitmap.createScaledBitmap(d, 40, 40, true);
			bound = 25;
			break;

		}
		Drawable scaled = new BitmapDrawable(bitmapOrig);
		scaled.setBounds(-bound, -bound, bound, bound);
		return scaled;
	}
	
	public static String getAddress(APILocationModel location){
		String address = "";
		if(location.getAddress() != null){
			address = address + location.getAddress();
		}
		
		if(location.getCity() != null){
			address = address + ", " + location.getCity();
		}
		
		if(location.getCountry() != null){
			address = address + ", " + location.getCountry();
		}
		
		return address;
	}
	
	public static void buildStartNavigationDialog(final SmartDestinationModel m, String name, final Context ctx) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		String title = String.format(
				ctx.getString(R.string.start_navigation_to),
				name);

		builder.setTitle(ctx.getString(R.string.app_name));
		builder.setMessage(title);

		builder.setPositiveButton(R.string.yes,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(ctx,
								NavigationActivity.class);
						intent.putExtra(NavigationActivity.DESTINATION, m);
						ctx.startActivity(intent);
					}
				});
		builder.setNegativeButton(R.string.no, null);
		builder.show();
	}

}
