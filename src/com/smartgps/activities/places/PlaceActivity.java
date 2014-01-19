package com.smartgps.activities.places;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.smartgps.R;
import com.smartgps.activities.BaseActivity;
import com.smartgps.models.SmartDestinationModel;
import com.smartgps.models.api.places.APIPlacesModel;
import com.smartgps.utils.ProjectConfig;
import com.smartgps.utils.Utilities;

public class PlaceActivity extends BaseActivity implements OnMarkerClickListener{

	public static final String PLACE = "place";
	public static final String MY_LOCATION = "my_location";

	private final int MAP_INITIALIZED_CHECK_INTERVAL = 20;
	private APIPlacesModel model;
	private MapView mMapView;
	private GoogleMap mMap;
	private Bundle mBundle;
	private TextView name;
	private TextView vicinity;
	private TextView type;
	private Location myLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);

		try {
			MapsInitializer.initialize(PlaceActivity.this);
		} catch (GooglePlayServicesNotAvailableException e) {
			Log.d("MAPS", "Maps cannot be initialized");
		}

		model = (APIPlacesModel) getIntent().getExtras().get(PLACE);
		myLocation = (Location) getIntent().getExtras().get(MY_LOCATION);
		setActionbarTitle(model.getName());

		initUI();
	}

	private void initUI() {
		name = (TextView) findViewById(R.id.name);
		vicinity = (TextView) findViewById(R.id.vicinity);
		type = (TextView) findViewById(R.id.type);

		name.setText(model.getName());
		vicinity.setText(model.getVicinity());

		String types = "";
		for (int i = 0; i < model.getTypes().size(); i++) {
			types = types
					+ Utilities.firstLetterUpercase(Utilities.formatText(model.getTypes().get(i)));
			if (i < model.getTypes().size() - 1) {
				types = types + ", ";
			}
		}
		type.setText(types);

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
			mMap.setOnMarkerClickListener(this);
		}

		customInfoWindow();
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

				title.setText(model.getName());
				subtitle.setText(model.getVicinity());

				// Returning the view containing InfoWindow contents
				return v;
			}
		});
	}

	private void setupMapCurrentLocation() {
		final LatLng destination = new LatLng(model.getGeometry().getLocation()
				.getLatitude(), model.getGeometry().getLocation()
				.getLongitude());

		CameraPosition cp = new CameraPosition.Builder().target(destination)
				.zoom(ProjectConfig.MAP_ZOOM_POI).build();
		mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));

		ImageLoader.getInstance().loadImage(model.getIcon(),
				new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
						Drawable image = Utilities.resize(loadedImage,
								PlaceActivity.this);

						mMap.addMarker(new MarkerOptions()
								.position(destination)
								.icon(BitmapDescriptorFactory
										.fromBitmap(Utilities
												.drawableToBitmap(image)))
								.title(model.getName()));
					}
				});
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		SmartDestinationModel m = new SmartDestinationModel();
		m.setAddress(model.getVicinity());
		m.setCountry("");
		m.setLatitude(model.getGeometry().getLocation().getLatitude());
		m.setLongitude(model.getGeometry().getLocation().getLongitude());
		
		Utilities.buildStartNavigationDialog(m, model.getName(), PlaceActivity.this);
		return false;
	}
}
