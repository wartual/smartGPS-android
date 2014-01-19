package com.smartgps.activities;

import java.io.IOException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.smartgps.R;
import com.smartgps.activities.events.SearchEventsActivity;
import com.smartgps.activities.navigation.SetupNavigationActivity;
import com.smartgps.activities.places.SearchPlacesActivity;
import com.smartgps.activities.settings.SettingsActivity;
import com.smartgps.adapters.ViewPagerAdapter;
import com.smartgps.models.dao.NotificationCategoriesDao;
import com.smartgps.models.dao.TravelCategoriesDao;
import com.smartgps.utils.ProjectConfig;
import com.smartgps.utils.SessionManager;
import com.smartgps.utils.Utilities;

public class MainActivity extends BaseActivity {

	public static final int SETTINGS_ACTIVITY = 1;
	public static final String IS_NOTIFICATION = "is_notification";

	private ViewPager pager;
	private ViewPagerAdapter adapter;
	private LinearLayout inflatedLayout;
	private int currentContentPage;
	private LinearLayout rootLayout;
	private GoogleCloudMessaging gcm;
	private String regid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initData();
		initUI();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Intent intent = new Intent(MainActivity.this,
					SettingsActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 1, Menu.NONE, getString(R.string.settings))
				.setIcon(R.drawable.actionbar_settings)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onResume() {
		super.onResume();
		user = sessionManager.getUserDetails();
		if (user.get(SessionManager.KEY_SESSION_ID) == null) {
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private void initUI() {
		pager = (ViewPager) findViewById(R.id.main_pager);
		setupPager();
	}

	private void setupPager() {
		adapter = new ViewPagerAdapter();
		adapter.addTab("a", getFirstPage());
		adapter.addTab("b", getSecondPage());
		adapter.addTab("c", getThirdPage());
		adapter.addTab("d", getFourthPage());
		pager.setAdapter(adapter);

	}

	private LinearLayout getFirstPage() {
		inflatedLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.main_first_page, null);

		rootLayout = (LinearLayout) inflatedLayout
				.findViewById(R.id.start_navigation);

		rootLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						SetupNavigationActivity.class);
				startActivity(intent);
			}
		});

		currentContentPage = 1;
		return inflatedLayout;
	}

	private LinearLayout getSecondPage() {
		inflatedLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.main_second_page, null);

		rootLayout = (LinearLayout) inflatedLayout
				.findViewById(R.id.start_history);
		
		rootLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						TravelsActivity.class);
				startActivity(intent);
			}
		});

		currentContentPage = 2;
		return inflatedLayout;
	}

	private LinearLayout getThirdPage() {
		inflatedLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.main_third_page, null);

		rootLayout = (LinearLayout) inflatedLayout
				.findViewById(R.id.search_events);

		rootLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						SearchPlacesActivity.class);
				startActivity(intent);
			}
		});

		currentContentPage = 3;
		return inflatedLayout;
	}

	private LinearLayout getFourthPage() {
		inflatedLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.main_fourh_page, null);

		rootLayout = (LinearLayout) inflatedLayout
				.findViewById(R.id.search_events);

		rootLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						SearchEventsActivity.class);
				startActivity(intent);
			}
		});

		currentContentPage = 4;
		return inflatedLayout;
	}

	private void checkDatabase() {
		if (sharedPrefs.getBoolean(ProjectConfig.FIRST_TIME, true)) {
			populatedDatabase();

			Editor editor = sharedPrefs.edit();
			editor.putBoolean(ProjectConfig.FIRST_TIME, false);
			editor.commit();
		}
	}

	private void populatedDatabase() {
		NotificationCategoriesDao.populate(user
				.get(SessionManager.KEY_SESSION_ID));
		TravelCategoriesDao.populate(user
				.get(SessionManager.KEY_SESSION_ID));
	}

	private void initData() {
		checkDatabase();

		if (!Utilities.checkPlayServices(MainActivity.this)) {
			Utilities
					.showGooglePlayServicesUnavailableDialog(MainActivity.this);
		}

		try {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = Utilities.getRegistrationId(MainActivity.this);
			if (regid == null) {
				registerGcm();
			} 
			else {
				updateGcmId(regid);
				Log.d("REGID", regid);
			}
		}
		catch(Exception e){
			
		}
	}
	
	private void registerGcm() {
		new AsyncTask() {

			@Override
			protected Object doInBackground(Object... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging
								.getInstance(MainActivity.this);
					}
					regid = gcm.register(ProjectConfig.APPLICATION_ID);
					msg = "Device registered, registration ID=" + regid;

					Log.d("REGISTER GCM", msg);
					updateGcmId(regid);
					SharedPreferences.Editor editor = sharedPrefs.edit();
					editor.putString(ProjectConfig.GCM_REGISTRATION_ID, regid);
					editor.commit();

				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Object result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}

		}.execute(null, null, null);
	}
}
