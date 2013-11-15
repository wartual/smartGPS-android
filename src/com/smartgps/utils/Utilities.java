package com.smartgps.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.smartgps.R;
import com.smartgps.models.SmartDestinationModel;

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
				String geoAddress = null;
				for (int i = 0; i < addresses.size(); i++) {
//					for (int j = 0; i < addresses.get(i).getMaxAddressLineIndex(); j++) {
//						geoAddress += addresses.get(i).getAddressLine(j);
//						if (j != addresses.size())
//							geoAddress = geoAddress + ", ";
//					}
					Log.d("MAX ADDRESS LINE INDEX", addresses.get(i).getMaxAddressLineIndex() + "");
					model.setAddress("dd");
					model.setLatitude(addresses.get(i).getLatitude());
					model.setLongitude(addresses.get(i).getLongitude());
					model.setCountry(addresses.get(i).getCountryName());
					list.add(model);
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
		Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
		List<Address> addresses;
		SmartDestinationModel model = null;

		try {
			addresses = geoCoder.getFromLocation(latitude, longitude, 1);

			if (addresses.size() > 0) {
				String geoAddress = null;
				for (int i = 0; i < NUMBER_OF_DESTINATIONS; i++) {
					for (int j = 0; i < addresses.get(i)
							.getMaxAddressLineIndex(); i++) {
						geoAddress += addresses.get(i).getAddressLine(j);
						if (j != addresses.size())
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
	
	public static void buildOkDialog(String message, final Activity activity,
			final boolean finishActivity) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		builder.setTitle(activity.getString(R.string.app_name)).setMessage(message)
				.setCancelable(false)
				.setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog,
							final int id) {
						dialog.cancel();
						if (finishActivity) {
							activity.finish();
						}
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}
	
	public static void showLoadingOverlay(final ProgressDialog pd, Activity activity) {
		pd.setTitle(activity.getString(R.string.processing));
		pd.setMessage(activity.getString(R.string.please_wait));
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				pd.show();
			}
		});
	}

	public static void hideLoadingOverlay(ProgressDialog pd) {
		pd.dismiss();
	}
}
