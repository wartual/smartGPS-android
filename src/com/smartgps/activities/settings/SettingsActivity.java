package com.smartgps.activities.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.smartgps.R;
import com.smartgps.activities.BaseActivity;

public class SettingsActivity extends BaseActivity{
	
	private LinearLayout logout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setActionbarTitle(R.string.settings);
		initUI();
	}
	
	private void initUI(){
		logout = (LinearLayout) findViewById(R.id.logout_layout);
		
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buildLogoutDialog();
			}
		});
	}
	
	private void buildLogoutDialog() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);

		builder.setTitle(getString(R.string.app_name)).setMessage(getString(R.string.logout_question))
				.setCancelable(false)
				.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog,
							final int id) {
						sessionManager.logoutUser();
						dialog.cancel();
						finish();
					}
				})
				.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener()  {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}	
}
