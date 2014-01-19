package com.smartgps.activities;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartgps.R;
import com.smartgps.adapters.SmartFragmentAdapter;
import com.smartgps.fragments.TravelsListFragment;
import com.smartgps.models.APITravelModel;
import com.smartgps.models.APITravelStatusCategories;
import com.smartgps.models.dao.TravelCategoriesDao;
import com.smartgps.utils.APICalls;
import com.smartgps.utils.SessionManager;

public class TravelsActivity extends BaseActivity{

	private ArrayList<APITravelModel> travels;
	private ViewPager pager;
	private SmartFragmentAdapter adapter;
	private ArrayList<Fragment> fragments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travels);
		setActionbarTitle(APITravelStatusCategories.PAUSED);

		initUI();
	}
	
	private void initUI() {
		pager = (ViewPager) findViewById(R.id.pager);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_events, menu);

		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		getData();
	}

	private void getData(){
		url = APICalls.getTravelsForUser(user.get(SessionManager.KEY_SESSION_ID));
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
				travels = new ArrayList<APITravelModel>(Arrays
						.asList(gson.fromJson(reader,
								APITravelModel[].class)));
				if (travels == null || travels.size() == 0) {
					buildOkDialog(getString(R.string.no_events), false);
				} else {
					mapData();
				}
			}
		});
	}
	
	private void mapData() {
		fragments = new ArrayList<Fragment>();
		
		TravelsListFragment paused = new TravelsListFragment();
		paused.setTravels(getTravelsByStatus(APITravelStatusCategories.PAUSED));
		
		TravelsListFragment finished = new TravelsListFragment();
		finished.setTravels(getTravelsByStatus(APITravelStatusCategories.FINISHED));
		
		fragments.add(paused);
		fragments.add(finished);

		
		adapter = new SmartFragmentAdapter(getSupportFragmentManager(), fragments);
		pager.setAdapter(adapter);
		hideLoadingOverlay();
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				switch(position){
					case 0:
						setActionbarTitle(APITravelStatusCategories.PAUSED);
						break;
					case 1:
						setActionbarTitle(APITravelStatusCategories.FINISHED);
						break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	private ArrayList<APITravelModel> getTravelsByStatus(String status){
		ArrayList<APITravelModel> list = new ArrayList<APITravelModel>();
		
		for(APITravelModel item : travels){
			if(item.getStatusId().equals(TravelCategoriesDao.getByType(status).getStatusId()))
				list.add(item);
		}
		return list;
	}
}
