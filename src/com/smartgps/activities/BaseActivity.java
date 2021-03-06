package com.smartgps.activities;

import java.util.HashMap;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartgps.R;
import com.smartgps.models.api.APIJsonResponseModel;
import com.smartgps.params.APIUpdateGcmIdParams;
import com.smartgps.utils.APICalls;
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
	protected SharedPreferences sharedPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(BaseActivity.this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	
	protected void setActionbarTitle(int resId) {
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setTitle(getString(resId));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	protected void setActionbarTitle(String title) {
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setTitle(title);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(null);
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

	protected void hideLoadingOverlay() {
		progressDialog.dismiss();
	}
	
	protected void buildOkDialog(String message,
			final boolean finishActivity) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);

		builder.setTitle(getString(R.string.app_name)).setMessage(message)
				.setCancelable(false)
				.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog,
							final int id) {
						dialog.cancel();
						if (finishActivity) {
							finish();
						}
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	protected View createTabView(String string) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(string);
		return view;
	}
	
	protected void updateGcmId(String gcmId){
		APIUpdateGcmIdParams updateGcmId = new APIUpdateGcmIdParams();
		updateGcmId.setUserId(user.get(SessionManager.KEY_SESSION_ID));
		updateGcmId.setGcmId(gcmId);
		
		client.post(APICalls.getUpdateGcmId(), updateGcmId.getRequestParams() ,new JsonHttpResponseHandler(){
			
			@Override
			public void onFailure(Throwable error, String content) {
				Log.d("GCM", "Gcm id not updated");
			}

			@Override
			public void onSuccess(JSONObject json) {
				Log.d("GCM", "Gcm id updated");
			}
		});
	}
}
