package com.smartgps.activities;


import android.app.ProgressDialog;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.smartgps.R;
import com.smartgps.utils.DialogBuilder;

public class BaseActivity extends SherlockFragmentActivity{
	
	protected DialogBuilder dialogBuilder;
	protected ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle(getString(R.string.app_name));
		initVars();
	}
	
	private void initVars(){
		dialogBuilder = new DialogBuilder(BaseActivity.this);
		progressDialog = new ProgressDialog(BaseActivity.this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();

			return true;
		}

		return super.onOptionsItemSelected(item);
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
