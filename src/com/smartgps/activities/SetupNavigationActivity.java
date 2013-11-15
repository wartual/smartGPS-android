package com.smartgps.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.smartgps.R;
import com.smartgps.interfaces.DialogTextCommunicator;
import com.smartgps.models.SmartDestinationModel;
import com.smartgps.utils.Utilities;

public class SetupNavigationActivity extends BaseActivity implements DialogTextCommunicator{

	private TextView  navigationTypeTv;
	private ArrayList<String> navigationTypes;
	private Dialog dialog;
	private EditText latitude;
	private EditText longitude;
	private EditText address;
	private TextView destination;
	private LinearLayout gpsCoordinatesLayout;
	private BootstrapButton findDestination;

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
		findDestination = (BootstrapButton) findViewById(R.id.find_navigation);
		destination = (TextView) findViewById(R.id.destination);
		
		navigationTypeTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog = dialogBuilder.buildDialog(navigationTypes,
						getString(R.string.navigation_type), navigationTypeTv);
				dialog.show();
			}
		});
		
		findDestination.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Utilities.showLoadingOverlay(progressDialog, SetupNavigationActivity.this);
				getDestinations();
			}
		});
	}
	
	private void getDestinations(){
		if(address.getVisibility() == View.VISIBLE){
			List<SmartDestinationModel> list = Utilities.getDestinationsFromAddress(SetupNavigationActivity.this, address.getText().toString());
			if(list == null || list.size() == 0){
				Utilities.buildOkDialog(getString(R.string.destination_cannot_be_obtained), SetupNavigationActivity.this,
						false);
			}
			else{
				ArrayList<String> obtainedAddresses = new ArrayList<String>();
				for(int i = 0; i < list.size(); i++){
					obtainedAddresses.add(list.get(i) + ", " + list.get(i).getCountry());
				}
				
				dialog = dialogBuilder.buildDialog(obtainedAddresses,
						getString(R.string.destination), destination);
				dialog.show(); 
			}
		}
		else{
			SmartDestinationModel model = Utilities.getDestinationFromGpsCoordinates(SetupNavigationActivity.this, 
								 Double.parseDouble(latitude.getText().toString()), Double.parseDouble(longitude.getText().toString()));
			
			if(model == null){
				Utilities.buildOkDialog(getString(R.string.destination_cannot_be_obtained), SetupNavigationActivity.this,
													false);
			}
			else{
				destination.setText(model.getAddress() + ", " + model.getCountry());
			}
		}
		
		Utilities.hideLoadingOverlay(progressDialog);
	}

	@Override
	public void passTextToActivity(String text) {
		if(text.equalsIgnoreCase(getString(R.string.gps_coordinates))){
			gpsCoordinatesLayout.setVisibility(View.VISIBLE);
			address.setVisibility(View.GONE);
			findDestination.setVisibility(View.GONE);
		}
		else{
			gpsCoordinatesLayout.setVisibility(View.GONE);
			address.setVisibility(View.VISIBLE);		
			findDestination.setVisibility(View.VISIBLE);
		}
	}
}
