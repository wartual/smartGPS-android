package com.smartgps.activities.places;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.smartgps.R;
import com.smartgps.activities.BaseActivity;
import com.smartgps.activities.navigation.NavigationPreviewActivity;
import com.smartgps.interfaces.DialogTextCommunicator;
import com.smartgps.models.SmartDestinationModel;
import com.smartgps.utils.ProjectConfig;
import com.smartgps.utils.Utilities;

public class SearchPlacesActivity extends BaseActivity implements
		DialogTextCommunicator, GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private TextView navigationTypeTv;
	private ArrayList<String> navigationTypes;
	private Dialog dialog;
	private LocationClient locationClient;
	private boolean currentLocationSet = false;
	private Location lastLocation;
	private EditText latitude;
	private EditText longitude;
	private EditText address;
	private TextView destination;
	private TextView destinationTitle;
	private LinearLayout gpsCoordinatesLayout;
	private BootstrapButton findDestination;
	private BootstrapButton continueButton;
	private SmartDestinationModel model;
	private List<SmartDestinationModel> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_places);
		setActionbarTitle(getString(R.string.search_places));
		initArrays();
		initUI();
	}

	@Override
	public void onResume() {
		super.onResume();

		if (locationClient == null) {
			setupLocationClient();
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		if (locationClient != null) {
			locationClient.disconnect();
			locationClient = null;
		}
	}
	
	private void initArrays() {
		navigationTypes = new ArrayList<String>();

		navigationTypes.add(getString(R.string.address));
		navigationTypes.add(getString(R.string.gps_coordinates));
	}

	private void initUI() {
		navigationTypeTv = (TextView) findViewById(R.id.navigation_type);
		latitude = (EditText) findViewById(R.id.latitude);
		longitude = (EditText) findViewById(R.id.longitude);
		address = (EditText) findViewById(R.id.address);
		gpsCoordinatesLayout = (LinearLayout) findViewById(R.id.gps_layout);
		findDestination = (BootstrapButton) findViewById(R.id.find_navigation);
		destination = (TextView) findViewById(R.id.destination);
		destinationTitle = (TextView) findViewById(R.id.destination_title);
		continueButton = (BootstrapButton) findViewById(R.id.continue_button);

		navigationTypeTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog = dialogBuilder.buildDialog(navigationTypes,
						getString(R.string.navigation_type), navigationTypeTv);
				dialog.show();
			}
		});

		findDestination.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showLoadingOverlay();
				getDestinations();
			}
		});

		continueButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(destination.getText())) {
					buildOkDialog(
							getString(R.string.destination_cannot_be_obtained),
							false);
				} else {
					startSearchForEvents();
				}
			}
		});
	}

	private void setupLocationClient() {
		locationClient = new LocationClient(this, this, this);
		locationClient.connect();
	}
	
	private void startSearchForEvents() {
		Intent intent = new Intent(SearchPlacesActivity.this,
				PlacesActivity.class);
		if (navigationTypeTv.getText().toString()
				.equalsIgnoreCase(getString(R.string.address))) {
			for (int i = 0; i < list.size(); i++) {
				if (destination.getText().toString()
						.startsWith(list.get(i).getAddress()))
					model = list.get(i);
			}
		}
		intent.putExtra(PlacesActivity.PLACE, model);
		intent.putExtra(PlacesActivity.LOCATION, lastLocation);
		startActivity(intent);
		finish();
	}

	private void getDestinations() {
		if (address.getVisibility() == View.VISIBLE) {
			list = Utilities.getDestinationsFromAddress(
					SearchPlacesActivity.this, address.getText().toString());
			if (list == null || list.size() == 0) {
				buildOkDialog(
						getString(R.string.destination_cannot_be_obtained),
						false);
			} else {
				ArrayList<String> obtainedAddresses = new ArrayList<String>();
				for (int i = 0; i < list.size(); i++) {
					obtainedAddresses.add(list.get(i).getAddress() + ", "
							+ list.get(i).getCountry());
				}

				dialog = dialogBuilder.buildDialog(obtainedAddresses,
						getString(R.string.destination), destination);
				dialog.show();
			}
		} else {
			if (TextUtils.isEmpty(latitude.getText().toString())
					|| TextUtils.isEmpty(longitude.getText().toString())) {
				buildOkDialog(
						getString(R.string.destination_cannot_be_obtained),
						false);
			} else {
				model = Utilities.getDestinationFromGpsCoordinates(
						SearchPlacesActivity.this,
						Double.parseDouble(latitude.getText().toString()),
						Double.parseDouble(longitude.getText().toString()));

				if (model == null) {
					buildOkDialog(
							getString(R.string.destination_cannot_be_obtained),
							false);
				} else {
					destinationTitle.setVisibility(View.VISIBLE);
					destination.setVisibility(View.VISIBLE);
					destination.setText(model.getAddress() + ", "
							+ model.getCountry());
				}
			}
		}

		hideLoadingOverlay();
	}

	@Override
	public void passTextToActivity(String text) {
		if (text.equalsIgnoreCase(getString(R.string.gps_coordinates))) {
			gpsCoordinatesLayout.setVisibility(View.VISIBLE);
			address.setVisibility(View.GONE);
			findDestination.setVisibility(View.VISIBLE);
			destinationTitle.setVisibility(View.GONE);
			destination.setVisibility(View.GONE);
		} else if (text.equalsIgnoreCase(getString(R.string.address))) {
			gpsCoordinatesLayout.setVisibility(View.GONE);
			address.setVisibility(View.VISIBLE);
			findDestination.setVisibility(View.VISIBLE);
			destinationTitle.setVisibility(View.GONE);
			destination.setVisibility(View.GONE);
		} else {
			destinationTitle.setVisibility(View.VISIBLE);
			destination.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		if(locationClient.getLastLocation() == null){
			lastLocation = ProjectConfig.getDefaultLocation();
		}
		else{
			lastLocation = locationClient.getLastLocation();
		}
		currentLocationSet = true;
		
		SmartDestinationModel model  = Utilities.getDestinationFromGpsCoordinates(SearchPlacesActivity.this, lastLocation.getLatitude(), lastLocation.getLongitude());
		
		address.setText(model.getAddress() + ", " + model.getCountry());
		latitude.setText(lastLocation.getLatitude() + "");
		longitude.setText(lastLocation.getLongitude() + "");
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
}
