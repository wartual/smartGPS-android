package com.smartgps.activities.places;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartgps.R;
import com.smartgps.activities.BaseActivity;
import com.smartgps.adapters.PlacesAdapter;
import com.smartgps.models.SmartDestinationModel;
import com.smartgps.models.api.places.APILocationModel;
import com.smartgps.models.api.places.APIPlacesModel;
import com.smartgps.utils.APICalls;
import com.smartgps.utils.ProjectConfig;
import com.smartgps.utils.SessionManager;

public class PlacesActivity extends BaseActivity {

	public static final String PLACE = "destination";
	public static final String LOCATION = "location";
	private ListView list;
	private SmartDestinationModel model;
	private ArrayList<APIPlacesModel> places;
	private PlacesAdapter adapter;
	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		setActionbarTitle(getString(R.string.places));

		initUI();
		getData();
	}

	private void initUI() {
		list = (ListView) findViewById(R.id.list);
	}

	private void getData() {
		model = (SmartDestinationModel) getIntent().getExtras().get(PLACE);
		location = (Location) getIntent().getExtras().get(LOCATION);
		url = APICalls.getPlacesUrl(user.get(SessionManager.KEY_SESSION_ID),
				model.getLatitude(), model.getLongitude(), ProjectConfig.NUMBER_OF_ITEMS_TO_LOAD);
		showLoadingOverlay();
		Log.d("URL", url);
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
				places = new ArrayList<APIPlacesModel>(Arrays
						.asList(gson.fromJson(reader,
								APIPlacesModel[].class)));
				if (places == null || places.size() == 0) {
					buildOkDialog(getString(R.string.no_events), false);
				} else {
					mapData();
				}
			}
		});
	}

	private void mapData() {
		adapter = new PlacesAdapter(PlacesActivity.this, places, location);
		list.setAdapter(adapter);
		hideLoadingOverlay();
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(PlacesActivity.this, PlaceActivity.class);
				intent.putExtra(PlaceActivity.PLACE, places.get(position));
				intent.putExtra(PlaceActivity.MY_LOCATION, location);
				startActivity(intent);
			}
		});
	}
}
