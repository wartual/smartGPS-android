package com.smartgps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.smartgps.R;
import com.smartgps.adapters.ViewPagerAdapter;

public class MainActivity extends BaseActivity{

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
	
	private void initUI(){
		pager = (ViewPager) findViewById(R.id.main_pager);
		setupPager();
	}
	
	private void setupPager(){
		adapter = new ViewPagerAdapter();
		adapter.addTab("a", getFirstPage());
		
		pager.setAdapter(adapter);
		
	}
	
	private LinearLayout getFirstPage() {
		inflatedLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.main_first_page, null);
		
		startNavigation = (LinearLayout) inflatedLayout.findViewById(R.id.start_navigation);
		
		startNavigation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SetupNavigationActivity.class);
				startActivity(intent);
			}
		});
		
		currentContentPage = 1;
		return inflatedLayout;
	}
}
