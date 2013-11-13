package com.smartgps.activities;


import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.smartgps.R;
import com.smartgps.utils.DialogBuilder;

public class BaseActivity extends SherlockFragmentActivity{
	
	protected DialogBuilder dialogBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle(getString(R.string.app_name));
		
		initVars();
	}
	
	private void initVars(){
		dialogBuilder = new DialogBuilder(BaseActivity.this);
	}
	
	protected void setActionbarTitle(int resId) {
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
		getSupportActionBar().setTitle(getString(resId));
	}

	protected void setActionbarTitle(String title) {
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
		getSupportActionBar().setTitle(title);
	}
}
