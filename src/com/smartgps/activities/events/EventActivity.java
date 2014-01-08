package com.smartgps.activities.events;

import java.util.ArrayList;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.MapsInitializer;
import com.smartgps.R;
import com.smartgps.activities.BaseActivity;
import com.smartgps.adapters.SmartFragmentAdapter;
import com.smartgps.custom.StepPagerStrip;
import com.smartgps.custom.StepPagerStrip.OnPageSelectedListener;
import com.smartgps.fragments.EventMapFragment;
import com.smartgps.fragments.EventSpecialsFragment;
import com.smartgps.fragments.EventTipsFragment;
import com.smartgps.models.api.foursquare.APIItemsModel;

public class EventActivity extends BaseActivity{
	
	public static final String EVENT = "event";
	public static final String MY_LOCATION = "my_location";
	
	private APIItemsModel model;
	private Location myLocation;
	private ViewPager pager;
	private StepPagerStrip stepPager;
	private ArrayList<Fragment> fragments;
	private SmartFragmentAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);

		try {
			MapsInitializer.initialize(EventActivity.this);
		} catch (GooglePlayServicesNotAvailableException e) {
			Log.d("MAPS", "Maps cannot be initialized");
		}

		model = (APIItemsModel) getIntent().getExtras().get(EVENT);
		myLocation = (Location) getIntent().getExtras().get(MY_LOCATION);
		setActionbarTitle(model.getVenue().getName());

		initUI();
	}
	
	private void initUI(){
		pager = (ViewPager) findViewById(R.id.pager);
		stepPager = (StepPagerStrip) findViewById(R.id.strip);
		
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				stepPager.setCurrentPage(pager.getCurrentItem());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		stepPager.setOnPageSelectedListener(new OnPageSelectedListener() {
			
			@Override
			public void onPageStripSelected(int position) {
				pager.setCurrentItem(position);
			}
		});
		
		setupFragments();
	}
	
	private void setupFragments(){
		EventMapFragment mapFragment = new EventMapFragment();
		mapFragment.setMyLocation(myLocation);
		mapFragment.setModel(model);
		
		EventTipsFragment tipsFragment = new EventTipsFragment();
		tipsFragment.setTips(model.getTips());
		
		EventSpecialsFragment specialsFragment = new EventSpecialsFragment();
		specialsFragment.setSpecials(model.getVenue().getSpecials().getItems());
		
		fragments = new ArrayList<Fragment>();
		fragments.add(mapFragment);
		fragments.add(tipsFragment);
		fragments.add(specialsFragment);
		
		adapter = new SmartFragmentAdapter(getSupportFragmentManager(),
				fragments);
		pager.setAdapter(adapter);
		stepPager.setPageCount(3);
	}
}
