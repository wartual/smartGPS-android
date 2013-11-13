package com.smartgps.activities;

import java.util.ArrayList;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smartgps.R;
import com.smartgps.interfaces.DialogTextCommunicator;

public class SetupNavigationActivity extends BaseActivity implements DialogTextCommunicator{

	private TextView  navigationTypeTv;
	private ArrayList<String> navigationTypes;
	private Dialog dialog;
	private EditText latitude;
	private EditText longitude;
	private EditText address;
	private LinearLayout gpsCoordinatesLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_navigation);
		setActionbarTitle(getString(R.string.setup_navigation));
		initArrays();
		initUI();
	}

	private void initArrays() {
		navigationTypes = new ArrayList<String>();

		navigationTypes.add(getString(R.string.address));
		navigationTypes.add(getString(R.string.gps_coordinates));
	}

	private void initUI() {
		navigationTypeTv = (TextView) findViewById(R.id.navigation_type);
		latitude = (EditText) findViewById(R.id.latitude);
		longitude = (EditText) findViewById(R.id.longitude);
		address = (EditText) findViewById(R.id.address);
		gpsCoordinatesLayout = (LinearLayout) findViewById(R.id.gps_layout);
		
		navigationTypeTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog = dialogBuilder.buildDialog(navigationTypes,
						getString(R.string.navigation_type), navigationTypeTv);
				dialog.show();
			}
		});
		
		
	}

	@Override
	public void passTextToActivity(String text) {
		if(text.equalsIgnoreCase(getString(R.string.gps_coordinates))){
			gpsCoordinatesLayout.setVisibility(View.VISIBLE);
			address.setVisibility(View.GONE);
		}
		else{
			gpsCoordinatesLayout.setVisibility(View.GONE);
			address.setVisibility(View.VISIBLE);		
		}
	}
}
