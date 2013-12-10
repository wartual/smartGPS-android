package com.smartgps.activities.navigation;

import org.w3c.dom.Document;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;
import com.smartgps.R;
import com.smartgps.activities.BaseActivity;
import com.smartgps.models.SmartDestinationModel;
import com.smartgps.utils.GMapV2Direction;
import com.smartgps.utils.ProjectConfig;
import com.smartgps.utils.Utilities;

public class NavigationPreviewActivity extends BaseActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	public static final String DESTINATION = "destination";

	private GMapV2Direction mapDirection;
	private String mode;
	private LocationClient locationClient;
	private boolean currentLocationSet = false;
	private Location lastLocation;
	private TextView startAddress;
	private TextView endAddress;
	private TextView modeTv;
	private TextView duration;
	private TextView distance;
	private SmartDestinationModel model;
	private BootstrapButton regularNavigation;
	private BootstrapButton preview;
	private LatLng currentLocation;
	private LatLng destination;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation_preview);
		setActionbarTitle(getString(R.string.navigation_preview));
		
		checkGpsAndInternetConnection();
		
		getData();
		initUI();
		setupLocationClient();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 1, Menu.NONE, getString(R.string.driving))
				.setIcon(R.drawable.car)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		menu.add(Menu.NONE, 2, Menu.NONE, getString(R.string.walking))
				.setIcon(R.drawable.walking)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case 1:
			mode = GMapV2Direction.MODE_DRIVING;
			refreshData();
			return true;
		case 2:
			mode = GMapV2Direction.MODE_WALKING;
			refreshData();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void checkGpsAndInternetConnection(){
		LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);

	    if (!manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
	    	Log.d("GPS", "DISABLED");
	        Utilities.buildOkDialog(getString(R.string.gps_not_active), NavigationPreviewActivity.this, true);
	    }
	    
	    if(!Utilities.checkInternetConnection(NavigationPreviewActivity.this)){
	    	Log.d("INTERNET", "DISABLED");
	        Utilities.buildOkDialog(getString(R.string.no_internet_connection), NavigationPreviewActivity.this, true);
	    }
	}

	private void initUI() {
		startAddress = (TextView) findViewById(R.id.start_address);
		endAddress = (TextView) findViewById(R.id.end_address);
		duration = (TextView) findViewById(R.id.duration);
		distance = (TextView) findViewById(R.id.distance);
		modeTv = (TextView) findViewById(R.id.mode);
		regularNavigation = (BootstrapButton) findViewById(R.id.regular_navigation);
		preview = (BootstrapButton) findViewById(R.id.preview_on_map);
			
		regularNavigation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NavigationPreviewActivity.this,
						NavigationActivity.class);
				intent.putExtra(NavigationActivity.DESTINATION, model);
				startActivity(intent);
				finish();
			}
		});
		
		preview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NavigationPreviewActivity.this, PreviewMapActivity.class);
				intent.putExtra(PreviewMapActivity.DESTINATION, model);
				startActivity(intent);
			}
		});
	}

	private void getData() {
		model = (SmartDestinationModel) getIntent().getExtras()
				.get(DESTINATION);
		lastLocation = ProjectConfig.getDefaultLocation();
		destination = new LatLng(model.getLatitude(), model.getLongitude());
		mapDirection = new GMapV2Direction();
		mode = GMapV2Direction.MODE_DRIVING;
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

	private void setupLocationClient() {
		locationClient = new LocationClient(this, this, this);
		locationClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0) {
		if (locationClient.getLastLocation() == null) {
			lastLocation = ProjectConfig.getDefaultLocation();
		} else {
			lastLocation = locationClient.getLastLocation();
		}
		currentLocationSet = true;
		refreshData();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	private void refreshData() {
		// current location in LatLng
		currentLocation = new LatLng(lastLocation.getLatitude(),
				lastLocation.getLongitude());

		// mMap.clear();
		new Thread() {
			public void run() {

				// obtain xml document with directions from google maps
				final Document doc = mapDirection.getDocument(currentLocation,
						destination, mode);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						startAddress.setText(mapDirection.getStartAddress(doc));
						endAddress.setText(model.getAddress() + ", " + model.getCountry());
						duration.setText(Utilities.formatDuration(mapDirection.getDurationValue(doc)));
						distance.setText(Utilities.formatDistance(mapDirection.getDistanceValue(doc)));
						modeTv.setText(Utilities.firstLetterUpercase(mode));
					}
				});
			}
		}.start();
	}

}