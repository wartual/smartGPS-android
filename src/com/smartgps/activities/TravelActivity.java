package com.smartgps.activities;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;
import org.w3c.dom.Document;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartgps.R;
import com.smartgps.activities.navigation.NavigationActivity;
import com.smartgps.models.APITravelModel;
import com.smartgps.models.APITravelStatusCategories;
import com.smartgps.models.SmartDestinationModel;
import com.smartgps.models.SmartResponseTypes;
import com.smartgps.models.api.APIJsonResponseModel;
import com.smartgps.models.dao.TravelCategoriesDao;
import com.smartgps.params.APIUpdateTravelStatusParams;
import com.smartgps.utils.APICalls;
import com.smartgps.utils.GMapV2Direction;
import com.smartgps.utils.ProjectConfig;
import com.smartgps.utils.SessionManager;
import com.smartgps.utils.Utilities;

public class TravelActivity extends BaseActivity {

	public static final String TRAVEL = "travel";
	private final int MAP_INITIALIZED_CHECK_INTERVAL = 20;
	private APITravelModel model;
	private MapView mMapView;
	private GoogleMap mMap;
	private Bundle mBundle;
	private TextView departure;
	private TextView destination;
	private TextView distance;
	private TextView dateCreated;
	private TextView dateUpadated;
	private TextView status;
	private LatLng currentLocation;
	private LatLng departureLocation;
	private LatLng destinationLocation;
	private GMapV2Direction mapDirection;
	private PolylineOptions rectLine;
	private Polyline polylin;
	private MenuItem optionContinue;
	private MenuItem optionFinish;
	private MenuItem optionRepeat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travel);
		setActionbarTitle(getString(R.string.travel));
		
		if (!Utilities.checkPlayServices(TravelActivity.this)) {
			Utilities
					.showGooglePlayServicesUnavailableDialog(TravelActivity.this);
		}

		try {
			MapsInitializer.initialize(TravelActivity.this);
		} catch (GooglePlayServicesNotAvailableException e) {
			Log.d("MAPS", "Maps cannot be initialized");
		}

		model = (APITravelModel) getIntent().getExtras().get(TRAVEL);
		getData();
		initUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_travel, menu);
		optionContinue = (MenuItem) menu.findItem(R.id.option_continue);
		optionFinish = (MenuItem) menu.findItem(R.id.option_finish);
		optionRepeat = (MenuItem) menu.findItem(R.id.option_repeat);
		
		if(model.getStatusId().equals(TravelCategoriesDao.getByType(APITravelStatusCategories.FINISHED).getStatusId())){
			optionFinish.setVisible(false);
			optionContinue.setVisible(false);
			optionRepeat.setVisible(true);
		}
		else{
			optionFinish.setVisible(true);
			optionContinue.setVisible(true);
			optionRepeat.setVisible(false);
		}
		
		optionContinue.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				changeStatus(APITravelStatusCategories.ACTIVE);
				return false;
			}
		});

		optionFinish.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				changeStatus(APITravelStatusCategories.FINISHED);
				return false;
			}
		});

		optionRepeat.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				changeStatus(APITravelStatusCategories.ACTIVE);
				return false;
			}
		});
		
		return super.onCreateOptionsMenu(menu);
	}

	private void changeStatus(final String status) {
		APIUpdateTravelStatusParams params = new APIUpdateTravelStatusParams();
		params.setTravelId(model.getTravelId());
		params.setUserId(user.get(SessionManager.KEY_SESSION_ID));
		params.setTravelStatusId(TravelCategoriesDao.getByType(status)
				.getStatusId());

		client.put(APICalls.getUpdateTravelStatusUrl(),
				params.getRequestParams(), new JsonHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error, String content) {
						Log.d("UPDATE TRAVEL STATUS", "ERROR");
					}

					@Override
					public void onSuccess(JSONObject json) {
						reader = json.toString();
						response = gson.fromJson(reader,
								APIJsonResponseModel.class);
						
						if(response.getStatus().equals(SmartResponseTypes.RESPONSE_OK)){
							Log.d("STATUS", status);
							if(status.equalsIgnoreCase(APITravelStatusCategories.FINISHED)){
								buildOkDialog(response.getMessage(), true);
							}
							else{
								Intent intent = new Intent(TravelActivity.this,
										NavigationActivity.class);
								SmartDestinationModel smartModel = new SmartDestinationModel();
								smartModel.setAddress(model.getDestinationAddress());
								smartModel.setCountry("");
								smartModel.setLatitude(model.getDestinationLatitude());
								smartModel.setLongitude(model.getDestinationLongitude());
								intent.putExtra(NavigationActivity.DESTINATION, smartModel);
								intent.putExtra(NavigationActivity.TRAVEL_ID, model.getTravelId());
								startActivity(intent);
								finish();
							}
						}
						else{
							buildOkDialog(response.getMessage(), true);
						}
					}
				});
	}

	private void getData() {
		currentLocation = new LatLng(model.getCurrentLatitude(),
				model.getCurrentLongitude());
		departureLocation = new LatLng(model.getDepartureLatitude(),
				model.getDepartureLongitude());
		destinationLocation = new LatLng(model.getDestinationLatitude(),
				model.getDestinationLongitude());

		mapDirection = new GMapV2Direction();
	}

	private void initUI() {
		departure = (TextView) findViewById(R.id.departure);
		destination = (TextView) findViewById(R.id.destination);
		distance = (TextView) findViewById(R.id.distance);
		dateCreated = (TextView) findViewById(R.id.date_created);
		dateUpadated = (TextView) findViewById(R.id.date_updated);
		status = (TextView) findViewById(R.id.status);

		departure.setText(model.getDepartureAddress());
		destination.setText(model.getDestinationAddress());
		distance.setText(Utilities.formatDistance(model.getDistance()));
		dateCreated.setText(new Date(model.getDateCreated()).toLocaleString());
		dateUpadated.setText(new Date(model.getDateUpdated()).toLocaleString());
		status.setText(TravelCategoriesDao.getByStatusId(model.getStatusId())
				.getType());

		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.onCreate(mBundle);
		setupMapOptions();
		setupMapCurrentLocation();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();

		if (mMapView != null) {
			mMapView.onResume();
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		if (mMapView != null) {
			mMapView.onPause();
		}
	}

	@Override
	public void onDestroy() {
		if (mMapView != null)
			mMapView.onDestroy();
		super.onDestroy();
	}

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
		}
	}

	// show route on maps
	private void setupMapCurrentLocation() {

		new Thread() {
			public void run() {

				// obtain xml document with directions from google maps
				
				final Document doc = mapDirection.getDocument(
						departureLocation, destinationLocation,
						mapDirection.MODE_DRIVING);

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

						mMap.addMarker(new MarkerOptions().position(
								departureLocation).title(
								model.getDepartureAddress()));

						mMap.addMarker(new MarkerOptions().position(
								destinationLocation).title(
								model.getDestinationAddress()));

						mMap.addMarker(new MarkerOptions().position(
								currentLocation).title(
								getString(R.string.last_known_location)));

						// zoom out map so both markers are visible
						LatLngBounds.Builder builder = new LatLngBounds.Builder();
						builder.include(departureLocation);
						builder.include(destinationLocation);

						mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
								builder.build(), Utilities.dpToPixels(
										ProjectConfig.MAP_PADDING,
										TravelActivity.this)));
					}
				});
			}
		}.start();
	}
}
