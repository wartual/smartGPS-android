package com.smartgps.activities.events;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartgps.R;
import com.smartgps.activities.BaseActivity;
import com.smartgps.adapters.EventsAdapter;
import com.smartgps.models.SmartDestinationModel;
import com.smartgps.models.api.foursquare.APIItemsModel;
import com.smartgps.utils.APICalls;
import com.smartgps.utils.SessionManager;

public class EventsActivity extends BaseActivity{
	
	public static final String EVENT = "destination";
	public static final String LOCATION = "location";
	private ListView list;
	private SmartDestinationModel model;
	private int num = 10;
	private ArrayList<APIItemsModel> events;
	private EventsAdapter adapter;
	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		setActionbarTitle(getString(R.string.events));

		initUI();
		getData();
	}
	
	private void initUI() {
		list = (ListView) findViewById(R.id.list);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_events, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	
	private void getData() {
		model = (SmartDestinationModel) getIntent().getExtras().get(EVENT);
		location = (Location) getIntent().getExtras().get(LOCATION);
		url = APICalls.getEventsUrl(user.get(SessionManager.KEY_SESSION_ID),
				model.getLatitude(), model.getLongitude(), num);
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
				events = new ArrayList<APIItemsModel>(Arrays
						.asList(gson.fromJson(reader,
								APIItemsModel[].class)));
				if (events == null || events.size() == 0) {
					buildOkDialog(getString(R.string.no_events), false);
				} else {
					mapData();
				}
			}
		});
	}

	private void mapData() {
		adapter = new EventsAdapter(EventsActivity.this, events, location);
		list.setAdapter(adapter);
		hideLoadingOverlay();
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(EventsActivity.this, EventActivity.class);
				intent.putExtra(EventActivity.EVENT, events.get(position));
				intent.putExtra(EventActivity.MY_LOCATION, location);
				startActivity(intent);
			}
		});
	}
}
