package com.smartgps.activities;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.smartgps.R;
import com.smartgps.models.api.APIJsonResponseModel;
import com.smartgps.utils.DialogBuilder;
import com.smartgps.utils.ProjectConfig;
import com.smartgps.utils.SessionManager;

public class BaseActivity extends SherlockFragmentActivity{
	
	protected DialogBuilder dialogBuilder;
	protected ProgressDialog progressDialog;
	protected AsyncHttpClient client;
	protected HashMap<String, String> user;
	protected String url;
	protected GsonBuilder gsonBuilder = new GsonBuilder();
	protected Gson gson;
	protected String reader;
	protected APIJsonResponseModel response;
	protected SessionManager sessionManager;
	
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
		client = new AsyncHttpClient();
		client.setTimeout(ProjectConfig.CLIENT_TIMEOUT);
		user = new HashMap<String, String>();
		gsonBuilder.setDateFormat("M/d/yy hh:mm a");
		gson = gsonBuilder.create();
		sessionManager = new SessionManager(BaseActivity.this);
		user = sessionManager.getUserDetails();
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
	
	protected void showLoadingOverlay() {
		progressDialog.setTitle(getString(R.string.app_name));
		progressDialog.setTitle(getString(R.string.processing));
		progressDialog.setMessage(getString(R.string.please_wait));
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				progressDialog.show();
			}
		});
	}

	public void hideLoadingOverlay() {
		progressDialog.dismiss();
	}
}
