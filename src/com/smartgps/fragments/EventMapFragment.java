package com.smartgps.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
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
import com.smartgps.models.SmartDestinationModel;
import com.smartgps.models.api.foursquare.APIItemsModel;
import com.smartgps.utils.ProjectConfig;
import com.smartgps.utils.Utilities;

public class EventMapFragment extends SherlockFragment implements OnMarkerClickListener{

	private final int MAP_INITIALIZED_CHECK_INTERVAL = 20;
	private APIItemsModel model;
	private MapView mMapView;
	private GoogleMap mMap;
	private Bundle mBundle;
	private TextView name;
	private TextView vicinity;
	private TextView type;
	private Location myLocation;
	private ViewGroup rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		try {
			MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			Log.d("MAPS", "Maps cannot be initialized");
		}

		mBundle = savedInstanceState;
		initUI(inflater, container);
		setupMapCurrentLocation();
		return rootView;
	}

	private void initUI(LayoutInflater inflater, ViewGroup container) {
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_event_map,
				container, false);
		
		name = (TextView) rootView.findViewById(R.id.name);
		vicinity = (TextView) rootView.findViewById(R.id.vicinity);
		type = (TextView) rootView.findViewById(R.id.type);

		name.setText(model.getVenue().getName());
		vicinity.setText(Utilities.getAddress(model.getVenue().getLocation()));

		String types = "";
		for (int i = 0; i < model.getVenue().getCategories().size(); i++) {
			types = types
					+ Utilities.firstLetterUpercase(Utilities.formatText(model.getVenue().getCategories().get(i).getName()));
			if (i < model.getVenue().getCategories().size() - 1) {
				types = types + ", ";
			}
		}
		type.setText(types);

		mMapView = (MapView) rootView.findViewById(R.id.mapview);
		mMapView.onCreate(mBundle);
		setupMapOptions();
		setupMapCurrentLocation();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
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
				View v = getActivity().getLayoutInflater()
						.inflate(R.layout.info_window, null);

				// Getting reference to the TextView to set title
				TextView title = (TextView) v.findViewById(R.id.title);
				TextView subtitle = (TextView) v.findViewById(R.id.subtitle);

				title.setText(model.getVenue().getName());
				subtitle.setText(Utilities.getAddress(model.getVenue().getLocation()));

				// Returning the view containing InfoWindow contents
				return v;
			}
		});
	}

	private void setupMapCurrentLocation() {
		final LatLng destination = new LatLng(model.getVenue().getLocation()
				.getLatitude(), model.getVenue().getLocation().getLongitude());

		CameraPosition cp = new CameraPosition.Builder().target(destination)
				.zoom(ProjectConfig.MAP_ZOOM_POI).build();
		mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
		String iconUrl = model.getVenue().getCategories().get(0).getIcon().getPrefix() + "bg_32.png";
		ImageLoader.getInstance().loadImage(iconUrl,
				new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
						Drawable image = Utilities.resize(loadedImage,
								getActivity());

						mMap.addMarker(new MarkerOptions()
								.position(destination)
								.icon(BitmapDescriptorFactory
										.fromBitmap(Utilities
												.drawableToBitmap(image)))
								.title(model.getVenue().getName()));
					}
				});
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		SmartDestinationModel m = new SmartDestinationModel();
		m.setAddress(Utilities.getAddress(model.getVenue().getLocation()));
		m.setCountry("");
		m.setLatitude(model.getVenue().getLocation().getLatitude());
		m.setLongitude(model.getVenue().getLocation().getLongitude());
		
		Utilities.buildStartNavigationDialog(m, model.getVenue().getName(), getActivity());
		return false;
	}

	public Location getMyLocation() {
		return myLocation;
	}

	public void setMyLocation(Location myLocation) {
		this.myLocation = myLocation;
	}

	public APIItemsModel getModel() {
		return model;
	}

	public void setModel(APIItemsModel model) {
		this.model = model;
	}
}
