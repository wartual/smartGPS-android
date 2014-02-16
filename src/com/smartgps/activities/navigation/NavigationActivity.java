package com.smartgps.activities.navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import android.R.bool;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.smartgps.R;
import com.smartgps.activities.BaseActivity;
import com.smartgps.activities.places.PlaceActivity;
import com.smartgps.interfaces.DialogTextCommunicator;
import com.smartgps.models.APINotificationCategories;
import com.smartgps.models.APINotificationsModel;
import com.smartgps.models.APITravelStatusCategories;
import com.smartgps.models.SmartDestinationModel;
import com.smartgps.models.SmartResponseTypes;
import com.smartgps.models.api.APIJsonResponseModel;
import com.smartgps.models.api.foursquare.APIItemsModel;
import com.smartgps.models.api.places.APIPlacesModel;
import com.smartgps.models.dao.NotificationCategoriesDao;
import com.smartgps.models.dao.TravelCategoriesDao;
import com.smartgps.params.APICreateNotificationParams;
import com.smartgps.params.APIDeactivateNotificationParams;
import com.smartgps.params.APIRateNotification;
import com.smartgps.params.APIUpdateTravelCurrentLocationParams;
import com.smartgps.params.APIUpdateTravelStatusParams;
import com.smartgps.utils.APICalls;
import com.smartgps.utils.GMapV2Direction;
import com.smartgps.utils.ProjectConfig;
import com.smartgps.utils.SessionManager;
import com.smartgps.utils.Utilities;

public class NavigationActivity extends BaseActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		OnMyLocationChangeListener, OnMarkerClickListener,
		DialogTextCommunicator, OnMapClickListener {

	public static final String DESTINATION = "destination";
	public static final String TRAVEL_ID = "travelId";

	private final int MAP_INITIALIZED_CHECK_INTERVAL = 20;
	private SmartDestinationModel model;
	private LocationClient locationClient;
	private boolean currentLocationSet = false;
	private Location lastLocation;
	private MapView mMapView;
	private GoogleMap mMap;
	private Bundle mBundle;
	private String mode;
	private LatLng currentLocation;
	private GMapV2Direction mapDirection;
	private LatLng destination;
	private PolylineOptions rectLine;
	private Polyline polylin;
	private MenuItem notification;
	private MenuItem modeWalking;
	private MenuItem modeCar;
	private String travelId;
	private ArrayList<APINotificationsModel> notifications;
	private ArrayList<APIItemsModel> events;
	private ArrayList<APIPlacesModel> places;
	private static HashMap<Marker, APINotificationsModel> notificationMarkers;
	private static HashMap<Marker, APIItemsModel> eventMarkers;
	private static HashMap<Marker, APIPlacesModel> placesMarkers;
	private double distance;
	private Location lastLoadedMarkers;
	private APIPlacesModel place;
	private APIItemsModel event;
	private Dialog notificationDialog;
	private TextView thumbsUp, thumbsDown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		setActionbarTitle(getString(R.string.navigation));

		LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);

		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildOkDialog(getString(R.string.gps_not_active), true);
		}

		if (!Utilities.checkPlayServices(NavigationActivity.this)) {
			Utilities
					.showGooglePlayServicesUnavailableDialog(NavigationActivity.this);
		}

		try {
			MapsInitializer.initialize(NavigationActivity.this);
		} catch (GooglePlayServicesNotAvailableException e) {
			Log.d("MAPS", "Maps cannot be initialized");
		}

		initData();
		// startLocationUpdate();
		initUI();
	}

	private void initData() {
		model = (SmartDestinationModel) getIntent().getExtras()
				.get(DESTINATION);
		travelId = getIntent().getStringExtra(TRAVEL_ID);
		destination = new LatLng(model.getLatitude(), model.getLongitude());
		mode = GMapV2Direction.MODE_DRIVING;
		mapDirection = new GMapV2Direction();
		notificationMarkers = new HashMap<Marker, APINotificationsModel>();
		eventMarkers = new HashMap<Marker, APIItemsModel>();
		placesMarkers = new HashMap<Marker, APIPlacesModel>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_navigation, menu);
		notification = (MenuItem) menu.findItem(R.id.notification);
		modeWalking = (MenuItem) menu.findItem(R.id.mode_walking);
		modeCar = (MenuItem) menu.findItem(R.id.mode_car);

		notification.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				buildNotificationDialog(lastLocation);
				return false;
			}
		});

		modeWalking.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				mode = GMapV2Direction.MODE_WALKING;
				setupMapCurrentLocation(false);
				return false;
			}
		});

		modeCar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				mode = GMapV2Direction.MODE_DRIVING;
				setupMapCurrentLocation(false);
				return false;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		loadNotifications(true);
	}

	private void initUI() {
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.onCreate(mBundle);
		setupMapOptions();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {

		if (locationClient == null) {
			setupLocationClient();
		}

		if (mMapView != null) {
			mMapView.onResume();
		}

		super.onResume();
	}

	@Override
	public void onPause() {
		// stopLocationUpdate();
		if (locationClient != null) {
			locationClient.disconnect();
			locationClient = null;
		}

		if (mMapView != null) {
			mMapView.onPause();
		}
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		updateTravelStatus(APITravelStatusCategories.PAUSED);
		super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			updateTravelStatus(APITravelStatusCategories.PAUSED);
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
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
			mMap.setOnMyLocationChangeListener(this);
			mMap.setOnMarkerClickListener(this);
			mMap.setOnMapClickListener(this);
		}
		customInfoWindow();
		setupLocationClient();
	}

	private void customInfoWindow() {
		mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker marker) {
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

				if (notificationMarkers.get(marker) == null && eventMarkers.get(marker) == null && placesMarkers.get(marker) == null) {
					title.setText(model.getAddress());
					subtitle.setText(model.getCountry());
				} 
				else if (notificationMarkers.get(marker) != null && eventMarkers.get(marker) != null && placesMarkers.get(marker) == null){
					title.setText(notificationMarkers.get(marker).getText());
					subtitle.setText(notificationMarkers.get(marker)
							.getUsername());
				}
				else if (notificationMarkers.get(marker) == null && eventMarkers.get(marker) != null && placesMarkers.get(marker) == null){
					title.setText(eventMarkers.get(marker).getVenue().getName());
					subtitle.setText(Utilities.getAddress(eventMarkers.get(marker).getVenue().getLocation()));
				}
				else if (notificationMarkers.get(marker) == null && eventMarkers.get(marker) == null && placesMarkers.get(marker) != null){
					title.setText(placesMarkers.get(marker).getName());
					subtitle.setText(placesMarkers.get(marker)
							.getVicinity());
				}

				// Returning the view containing InfoWindow contents
				return v;
			}
		});
	}

	private void setupLocationClient() {
		locationClient = new LocationClient(this, this, this);
		locationClient.connect();
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		if (notificationMarkers.get(marker) != null) {
			buildNotificationDetailsDialog(notificationMarkers.get(marker),
					marker);
		}
		return false;
	}

	@Override
	public void onMyLocationChange(Location location) {
		distance = Utilities.getDistance(location, lastLocation);
		if (distance > 20) {
			lastLocation = location;
			currentLocation = new LatLng(location.getLatitude(),
					location.getLongitude());

			// TO DO
			// 1. Some filter when to report new location to server
			reportNewLocationToServer(location);
			handleLocationChange();
		}

		// if distance is greater than 10 km, refresh events, places and
		// notification
		distance = Utilities.getDistance(lastLoadedMarkers, location);
		if (distance > 10000) {
			loadNotifications(true);
			loadEvents(true);
			loadPlaces(true);
			lastLoadedMarkers = location;
		}
	}

	private void reportNewLocationToServer(Location lastLocation) {
		if (Utilities.checkInternetConnection(NavigationActivity.this)) {
			APIUpdateTravelCurrentLocationParams params = new APIUpdateTravelCurrentLocationParams();
			params.setLatitude(lastLocation.getLatitude());
			params.setLongitude(lastLocation.getLongitude());
			params.setTravelId(travelId);
			params.setUserId(user.get(SessionManager.KEY_SESSION_ID));

			client.put(APICalls.getUpdateTravelCurrentLocationUrl(),
					params.getRequestParams(), new JsonHttpResponseHandler() {

						@Override
						public void onFailure(Throwable error, String content) {
							Log.d("FAILED TO UPDATE LOCATION",
									"API CLIENT ON FAILURE");
						}

						@Override
						public void onSuccess(JSONObject json) {
							GsonBuilder gsonBuilder = new GsonBuilder();
							gsonBuilder.setDateFormat("M/d/yy hh:mm a");
							Gson gson = gsonBuilder.create();
							String reader = json.toString();
							response = gson.fromJson(reader,
									APIJsonResponseModel.class);
							Log.d("RESPONSE FROM UPDATE LOCATION",
									response.getMessage());

						}
					});
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
	}

	@Override
	public void onConnected(Bundle arg0) {
		if (locationClient.getLastLocation() == null) {
			lastLocation = ProjectConfig.getDefaultLocation();
		} else {
			lastLocation = locationClient.getLastLocation();
		}
		lastLoadedMarkers = lastLocation;
		currentLocationSet = true;
		setupMapCurrentLocation(true);
		loadNotifications(false);
		loadEvents(false);
		loadPlaces(false);
	}

	@Override
	public void onDisconnected() {
	}

	// show route on maps
	private void setupMapCurrentLocation(final boolean addMarker) {

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

						if (addMarker) {
							mMap.addMarker(new MarkerOptions().position(
									destination).title(
									model.getAddress() + ", "
											+ model.getCountry()));
						}
						// zoom out map so both markers are visible
						LatLngBounds.Builder builder = new LatLngBounds.Builder();
						builder.include(currentLocation);
						builder.include(destination);

						mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
								builder.build(), Utilities.dpToPixels(
										ProjectConfig.MAP_PADDING,
										NavigationActivity.this)));
					}
				});
			}
		}.start();
	}

	private void handleLocationChange() {
		new Thread() {
			public void run() {

				final Document doc = mapDirection.getDocument(currentLocation,
						destination, mode);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						ArrayList<LatLng> directionPoint = mapDirection
								.getDirection(doc);

						if (polylin != null && directionPoint != null) {
							polylin.setPoints(directionPoint);
						}
					}
				});
			}
		}.start();
	}

	private void buildNotificationDialog(final Location location) {
		Dialog dialog = new Dialog(NavigationActivity.this);
		AlertDialog.Builder builder = new AlertDialog.Builder(
				NavigationActivity.this);
		LayoutInflater inflater = NavigationActivity.this.getLayoutInflater();
		final LinearLayout customLayout = (LinearLayout) inflater.inflate(
				R.layout.dialog_notification, null);
		builder.setInverseBackgroundForced(true);

		final TextView category = (TextView) customLayout
				.findViewById(R.id.category);
		final EditText notification = (EditText) customLayout
				.findViewById(R.id.notification);
		category.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog dialog = dialogBuilder.buildDialog(
						Utilities.getNotificationCategories(),
						getString(R.string.choose_category), category);
				dialog.show();
			}
		});

		builder.setView(customLayout)

				.setPositiveButton(getResources().getString(R.string.send),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								if (category.getText().toString().isEmpty()
										|| notification.getText().toString()
												.isEmpty()) {
									buildOkDialog(
											getString(R.string.missing_input_data),
											false);
									dialog.cancel();
								} else if (notification.getText().toString()
										.length() > 199) {
									buildOkDialog(
											getString(R.string.invalid_notification_length),
											false);
									dialog.cancel();
								} else {
									sendNotification(category.getText()
											.toString(), notification.getText()
											.toString(), dialog, location);
								}
							}
						})
				.setNegativeButton(getResources().getString(R.string.cancel),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		dialog = builder.create();
		dialog.show();
	}

	private void sendNotification(String category, String text,
			final DialogInterface dialog, Location location) {
		APICreateNotificationParams createNotificationParams = new APICreateNotificationParams();
		createNotificationParams.setCategory(NotificationCategoriesDao
				.getByType(category).getCategoryId());
		createNotificationParams.setText(text);
		createNotificationParams.setUserId(user
				.get(SessionManager.KEY_SESSION_ID));
		createNotificationParams.setLatitude(location.getLatitude());
		createNotificationParams.setLongitude(location.getLongitude());
		createNotificationParams.setAddress(Utilities
				.getDestinationFromGpsCoordinates(NavigationActivity.this,
						location.getLatitude(), location.getLongitude())
				.getAddress());

		client.post(APICalls.getNewNotificationUrl(),
				createNotificationParams.getRequestParams(),
				new JsonHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error, String content) {
						if (error.getCause() instanceof ConnectTimeoutException) {
							buildOkDialog(
									getString(R.string.connection_timeout_has_occured),
									false);
						}
					}

					@Override
					public void onSuccess(JSONObject json) {
						reader = json.toString();
						response = gson.fromJson(reader,
								APIJsonResponseModel.class);
						buildOkDialog(response.getMessage(), false);
						dialog.cancel();
						hideLoadingOverlay();
					}
				});
	}

	private void updateTravelStatus(String travelStatus) {
		APIUpdateTravelStatusParams params = new APIUpdateTravelStatusParams();
		params.setTravelId(travelId);
		params.setUserId(user.get(SessionManager.KEY_SESSION_ID));
		params.setTravelStatusId(TravelCategoriesDao.getByType(travelStatus)
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
						Log.d("UPDATE TRAVEL STATUS", response.getStatus());
					}
				});
	}

	private void loadNotifications(final boolean clearMap) {
		url = APICalls.getNotificationsNearLocation(
				user.get(SessionManager.KEY_SESSION_ID),
				lastLocation.getLatitude(), lastLocation.getLongitude());
		Log.d("LOADAM NOTIFIKACIJE", url);

		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable error, String content) {
				if (error.getCause() instanceof ConnectTimeoutException) {
					buildOkDialog(
							getString(R.string.notifications_could_not_be_loaded),
							false);
				}
			}

			@Override
			public void onSuccess(JSONArray json) {
				reader = json.toString();
				notifications = new ArrayList<APINotificationsModel>(Arrays
						.asList(gson.fromJson(reader,
								APINotificationsModel[].class)));
				if (notifications != null && notifications.size() != 0)
					renderNotificationsOnMap(clearMap);
			}
		});
	}

	private void loadEvents(final boolean clearMap) {
		url = APICalls.getEventsUrl(user.get(SessionManager.KEY_SESSION_ID),
				lastLocation.getLatitude(), lastLocation.getLongitude(),
				ProjectConfig.NUMBER_OF_ITEMS_TO_LOAD);
		Log.d("LOADAM EVENTE", url);
		client.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				if (error.getCause() instanceof ConnectTimeoutException) {
					buildOkDialog(
							getString(R.string.connection_timeout_has_occured),
							false);
					hideLoadingOverlay();

				}
			}

			@Override
			public void onSuccess(JSONArray json) {
				reader = json.toString();
				events = new ArrayList<APIItemsModel>(Arrays.asList(gson
						.fromJson(reader, APIItemsModel[].class)));
				if (events != null && events.size() != 0)
					renderEventsOnMap(clearMap);
			}
		});
	}

	private void loadPlaces(final boolean clearMap) {
		url = APICalls.getPlacesUrl(user.get(SessionManager.KEY_SESSION_ID),
				lastLocation.getLatitude(), lastLocation.getLongitude(),
				ProjectConfig.NUMBER_OF_ITEMS_TO_LOAD);
		Log.d("LOADAM PLACES", url);
		client.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				if (error.getCause() instanceof ConnectTimeoutException) {
					buildOkDialog(
							getString(R.string.connection_timeout_has_occured),
							false);
					hideLoadingOverlay();

				}
			}

			@Override
			public void onSuccess(JSONArray json) {
				reader = json.toString();
				places = new ArrayList<APIPlacesModel>(Arrays.asList(gson
						.fromJson(reader, APIPlacesModel[].class)));
				if (places != null && places.size() != 0)
					renderPlacesOnMap(clearMap);
			}
		});
	}

	private void renderPlacesOnMap(boolean clearMap) {
		if (clearMap)
			mMap.clear();
		
		if (clearMap)
			mMap.clear();

		for (APIPlacesModel model : places) {
			LatLng location = new LatLng(model.getGeometry().getLocation()
					.getLatitude(), model.getGeometry().getLocation()
					.getLongitude());
			Marker marker = mMap.addMarker(new MarkerOptions()
					.position(location));

			if (placesMarkers.get(marker) == null) {
				placesMarkers.put(marker, model);
			}
		}
		
//		for (int i = 0; i< places.size(); i++) {
//			place = places.get(i);
//			ImageLoader.getInstance().loadImage(place.getIcon(),
//					new SimpleImageLoadingListener() {
//
//						@Override
//						public void onLoadingComplete(String imageUri,
//								View view, Bitmap loadedImage) {
//							super.onLoadingComplete(imageUri, view, loadedImage);
//							Drawable image = Utilities.resize(loadedImage,
//									NavigationActivity.this);
//							Log.d("LOCATION", place.getGeometry().getLocation().getLatitude() + ", " + place.getGeometry().getLocation().getLongitude());
//							LatLng location = new LatLng(place.getGeometry().getLocation().getLatitude(), place.getGeometry().getLocation().getLongitude());
//							Log.d("LAT LNG", location.toString());
//							Marker marker = mMap.addMarker(new MarkerOptions()
//									.position(location)
//									.icon(BitmapDescriptorFactory
//											.fromBitmap(Utilities
//													.drawableToBitmap(image)))
//									.title(place.getName()));
//
//							if (eventMarkers.get(marker) == null) {
//								eventMarkers.put(marker, event);
//							}
//						}
//					});
//		}
	}

	private void renderEventsOnMap(boolean clearMap) {
		if (clearMap)
			mMap.clear();

		for (APIItemsModel model : events) {
			LatLng location = new LatLng(model.getVenue().getLocation()
					.getLatitude(), model.getVenue().getLocation()
					.getLongitude());
			Marker marker = mMap.addMarker(new MarkerOptions()
					.position(location).title(model.getVenue().getName()));

			if (eventMarkers.get(marker) == null) {
				eventMarkers.put(marker, model);
			}
		}
		
//		for (int i = 0; i< events.size(); i++) {
//			event = events.get(i);
//			String iconUrl = event.getVenue().getCategories().get(0).getIcon().getPrefix() + "bg_32.png";
//			ImageLoader.getInstance().loadImage(iconUrl,
//					new SimpleImageLoadingListener() {
//
//						@Override
//						public void onLoadingComplete(String imageUri,
//								View view, Bitmap loadedImage) {
//							super.onLoadingComplete(imageUri, view, loadedImage);
//							Drawable image = Utilities.resize(loadedImage,
//									NavigationActivity.this);
//							Log.d("LOCATION", event.getVenue().getLocation().getLatitude() + ", " + event.getVenue().getLocation().getLongitude());
//							LatLng location = new LatLng(event.getVenue().getLocation().getLatitude(), event.getVenue().getLocation().getLongitude());
//							Log.d("LAT LNG", location.toString());
//							Marker marker = mMap.addMarker(new MarkerOptions()
//									.position(location)
//									.icon(BitmapDescriptorFactory
//											.fromBitmap(Utilities
//													.drawableToBitmap(image)))
//									.title(event.getVenue().getName()));
//
//							if (placesMarkers.get(marker) == null) {
//								placesMarkers.put(marker, place);
//							}
//						}
//					});
//		}
	}

	private void renderNotificationsOnMap(boolean clearMap) {
		if (clearMap)
			mMap.clear();

		for (APINotificationsModel model : notifications) {
			LatLng location = new LatLng(model.getLatitude(),
					model.getLongitude());
			Marker marker = mMap.addMarker(new MarkerOptions()
					.position(location).title(model.getText()));

			if (notificationMarkers.get(marker) == null) {
				notificationMarkers.put(marker, model);
			}
		}
	}

	@Override
	public void passTextToActivity(String text) {
	}

	@Override
	public void onMapClick(LatLng loc) {
		Location location = new Location("");
		location.setLatitude(loc.latitude);
		location.setLongitude(loc.longitude);
		buildNotificationDialog(location);
	}

	public void buildNotificationDetailsDialog(
			final APINotificationsModel model, final Marker marker) {
		notificationDialog = new Dialog(NavigationActivity.this);
		AlertDialog.Builder builder = new AlertDialog.Builder(
				NavigationActivity.this);
		LayoutInflater inflater = NavigationActivity.this.getLayoutInflater();
		final LinearLayout customLayout = (LinearLayout) inflater.inflate(
				R.layout.dialog_notification_details, null);
		builder.setInverseBackgroundForced(true);

		TextView text = (TextView) customLayout.findViewById(R.id.notification);
		TextView address = (TextView) customLayout.findViewById(R.id.address);
		TextView date = (TextView) customLayout.findViewById(R.id.date);
		TextView user = (TextView) customLayout.findViewById(R.id.user);
		thumbsUp = (TextView) customLayout.findViewById(R.id.thumps_up_num);
		thumbsDown = (TextView) customLayout.findViewById(R.id.thumps_down_num);
		LinearLayout thumbsUpPlaceholder = (LinearLayout) customLayout.findViewById(R.id.thumbs_up_placeholder);
		LinearLayout thumbsDownPlaceholder = (LinearLayout) customLayout.findViewById(R.id.thumbs_down_placeholder);
		
		thumbsUp.setText(model.getThumbsUp() + "");
		thumbsDown.setText(model.getThumbsDown() + "");
		text.setText(model.getText());
		address.setText(model.getAddress());
		date.setText(new Date(model.getDateCreated()).toLocaleString());
		user.setText(model.getUsername());

		builder.setView(customLayout)
				.setPositiveButton(getResources().getString(R.string.close),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		
		thumbsUpPlaceholder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				long current = Long.parseLong(thumbsUp.getText().toString());
				rateNotification(true, model, thumbsUp, current + 1);
			}
		});
		
		thumbsDownPlaceholder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				long current = Long.parseLong(thumbsDown.getText().toString());
				rateNotification(true, model, thumbsDown, current + 1);
			}
		});
		
		notificationDialog = builder.create();
		notificationDialog.show();
	}
	
	private void rateNotification(Boolean thumbsUp, APINotificationsModel model, final TextView text, final long value){
		APIRateNotification params = new APIRateNotification();
		params.setNotificationId(model.getNotificationId());
		params.setUserId(user.get(SessionManager.KEY_SESSION_ID));
		
		if(thumbsUp){
			params.setThumbsDown(false);
			params.setThumbsUp(true);
		}
		else{
			params.setThumbsDown(true);
			params.setThumbsUp(false);
		}
		
		client.put(APICalls.getRateNotification(),
				params.getRequestParams(), new JsonHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error, String content) {
						if (error.getCause() instanceof ConnectTimeoutException) {
							buildOkDialog(
									getString(R.string.connection_timeout_has_occured),
									false);
						}
					}

					@Override
					public void onSuccess(JSONObject json) {
						reader = json.toString();
						response = gson.fromJson(reader,
								APIJsonResponseModel.class);
						Log.d("RESPONSE", response.getMessage());
						text.setText(value + "");
						buildOkDialog(response.getMessage(), false);
					}
				});
	}
}
