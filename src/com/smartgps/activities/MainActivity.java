package com.smartgps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.smartgps.R;
import com.smartgps.activities.navigation.SetupNavigationActivity;
import com.smartgps.activities.settings.SettingsActivity;
import com.smartgps.adapters.ViewPagerAdapter;
import com.smartgps.utils.SessionManager;

public class MainActivity extends BaseActivity {

	public static final int SETTINGS_ACTIVITY = 1;
	
	private ViewPager pager;
	private ViewPagerAdapter adapter;
	private LinearLayout inflatedLayout;
	private int currentContentPage;
	private LinearLayout startNavigation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
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
		if(user.get(SessionManager.KEY_SESSION_ID) == null){
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
		adapter.addTab("b", getThirdPage());
		pager.setAdapter(adapter);

	}

	private LinearLayout getFirstPage() {
		inflatedLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.main_first_page, null);

		startNavigation = (LinearLayout) inflatedLayout
				.findViewById(R.id.start_navigation);

		startNavigation.setOnClickListener(new OnClickListener() {
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

//		startNavigation = (LinearLayout) inflatedLayout
//				.findViewById(R.id.start_navigation);
//
//		startNavigation.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(MainActivity.this,
//						SetupNavigationActivity.class);
//				startActivity(intent);
//			}
//		});

		currentContentPage = 2;
		return inflatedLayout;
	}
	
	private LinearLayout getThirdPage() {
		inflatedLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.main_third_page, null);

//		startNavigation = (LinearLayout) inflatedLayout
//				.findViewById(R.id.start_navigation);
//
//		startNavigation.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(MainActivity.this,
//						SetupNavigationActivity.class);
//				startActivity(intent);
//			}
//		});

		currentContentPage = 3;
		return inflatedLayout;
	}
}
