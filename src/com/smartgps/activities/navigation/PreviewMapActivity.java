package com.smartgps.activities.navigation;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.smartgps.R;
import com.smartgps.activities.BaseActivity;
import com.smartgps.models.SmartDestinationModel;
import com.smartgps.utils.GMapV2Direction;
import com.smartgps.utils.ProjectConfig;
import com.smartgps.utils.Utilities;

public class PreviewMapActivity extends BaseActivity implements
		OnMarkerClickListener, GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	public static final String DESTINATION = "destination";

	private final int MAP_INITIALIZED_CHECK_INTERVAL = 20;
	private SmartDestinationModel model;
	private GMapV2Direction mapDirection;
	private String mode;
	private LatLng destination;
	private LatLng currentLocation;
	private MapView mMapView;
	private GoogleMap mMap;
	private Bundle mBundle;
	private PolylineOptions rectLine;
	private Polyline polylin;
	private LocationClient locationClient;
	private boolean currentLocationSet = false;
	private Location lastLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview_map);
		setActionbarTitle(getString(R.string.preview_on_map));

		try {
			MapsInitializer.initialize(PreviewMapActivity.this);
		} catch (GooglePlayServicesNotAvailableException e) {
			Log.d("MAPS", "Maps cannot be initialized");
		}

		getData();
		initUI();
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
			setupMapCurrentLocation();
			return true;
		case 2:
			mode = GMapV2Direction.MODE_WALKING;
			setupMapCurrentLocation();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void getData() {
		model = (SmartDestinationModel) getIntent().getExtras()
				.get(DESTINATION);
		destination = new LatLng(model.getDestinationLatitude(), model.getDestinationLongitude());
		mapDirection = new GMapV2Direction();
		mode = GMapV2Direction.MODE_DRIVING;
	}

	private void initUI() {
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.onCreate(mBundle);
		setupMapOptions();
	}

	private void setupMapCurrentLocation() {
		// current location in LatLng
		currentLocation = new LatLng(model.getLatitude(),
				model.getLongitude());

		new Thread() {
			public void run() {

				// obtain xml document with directions from google maps
				final Document doc = mapDirection.getDocument(currentLocation,
						destination, mode);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						ArrayList<LatLng> directionPoint = mapDirection
								.getDirection(doc);

						rectLine = new PolylineOptions().width(10).color(
								Color.parseColor("#E84E10"));

						// connect points with line
						for (int i = 0; i < directionPoint.size(); i++) {
							rectLine.add(directionPoint.get(i));
						}

						if (polylin != null) {
							polylin.remove();
						}
						polylin = mMap.addPolyline(rectLine);

						mMap.addMarker(new MarkerOptions()
								.position(destination).title(
										model.getAddress() + ", "
												+ model.getCountry()));

						// zoom out map so both markers are visible
						LatLngBounds.Builder builder = new LatLngBounds.Builder();
						builder.include(currentLocation);
						builder.include(destination);

						mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
								builder.build(), Utilities.dpToPixels(
										ProjectConfig.MAP_PADDING,
										PreviewMapActivity.this)));
					}
				});
			}
		}.start();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();

		if (locationClient == null) {
			setupLocationClient();
		}

		if (mMapView != null) {
			mMapView.onResume();
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		if (locationClient != null) {
			locationClient.disconnect();
			locationClient = null;
		}

		if (mMapView != null) {
			mMapView.onPause();
		}
	}

	@Override
	public void onDestroy() {
		if (mMapView != null)
			mMapView.onDestroy();
		super.onDestroy();
	};

	private void setupMapOptions() {
		mMap = mMapView.getMap();

		if (mMap == null) {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					setupMapOptions();
				}
			}, MAP_INITIALIZED_CHECK_INTERVAL);
		} else {
			mMap.getUiSettings().setMyLocationButtonEnabled(true);
			mMap.setMyLocationEnabled(true);
			mMap.setOnMarkerClickListener(this);
		}
		customInfoWindow();
		setupLocationClient();
	}

	private void setupLocationClient() {
		locationClient = new LocationClient(this, this, this);
		locationClient.connect();
	}

	private void customInfoWindow() {
		mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker marker) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				// Getting view from the layout file info_window_layout
				View v = getLayoutInflater()
						.inflate(R.layout.info_window, null);

				// Getting reference to the TextView to set title
				TextView title = (TextView) v.findViewById(R.id.title);
				TextView subtitle = (TextView) v.findViewById(R.id.subtitle);

				title.setText(model.getAddress());
				subtitle.setText(model.getCountry());

				// Returning the view containing InfoWindow contents
				return v;
			}
		});
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
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
		setupMapCurrentLocation();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}
}
