package com.smartgps.activities.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartgps.R;
import com.smartgps.activities.BaseActivity;
import com.smartgps.models.SmartResponseTypes;
import com.smartgps.models.api.APIJsonResponseModel;
import com.smartgps.models.api.APIProfileModel;
import com.smartgps.params.APIUpdateProfileParams;
import com.smartgps.utils.APICalls;
import com.smartgps.utils.SessionManager;
import com.smartgps.utils.Utilities;

public class ProfileActivity extends BaseActivity implements OnTabChangeListener{

	private APIProfileModel profile;
	private EditText username;
	private EditText name;
	private EditText surname;
	private TextView gender;
	private TextView dateOfBirth;
	private EditText email;
	private EditText phone;
	private EditText address;
	private EditText postalOffice;
	private EditText country;
	private TabHost tabHost;
	private LinearLayout basicTab;
	private LinearLayout contactTab;
	private LinearLayout addressTab;
	private Dialog dialog;
	private ArrayList<String> genders;
	private Date dateForServer;
	
	protected final String first = "first";
	protected final String second = "second";
	protected final String third = "third";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		setActionbarTitle(R.string.my_profile);

		initArrays();
		initUI();
		getData();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			if(!Utilities.checkInternetConnection(ProfileActivity.this)){
				buildOkDialog(getString(R.string.no_internet_connection), false);
			}
			else if(Utilities.validateDate(dateForServer)){
				saveData();
			}
			else{
				buildOkDialog(getString(R.string.incorrect_date_of_birth), false);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 1, Menu.NONE, getString(R.string.settings))
				.setIcon(R.drawable.actionbar_accept)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return super.onCreateOptionsMenu(menu);
	}
	
	private void initArrays(){
		genders = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.genders)));
	}

	private void initUI() {
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		basicTab = (LinearLayout) findViewById(R.id.basic_tab);
		contactTab = (LinearLayout) findViewById(R.id.contact_tab);
		addressTab = (LinearLayout) findViewById(R.id.address_tab);
		username = (EditText) findViewById(R.id.username);
		name = (EditText) findViewById(R.id.name);
		surname = (EditText) findViewById(R.id.surname);
		gender = (TextView) findViewById(R.id.gender);
		dateOfBirth = (TextView) findViewById(R.id.date_of_birth);
		email = (EditText) findViewById(R.id.email);
		phone = (EditText) findViewById(R.id.phone);
		address = (EditText) findViewById(R.id.address);
		postalOffice = (EditText) findViewById(R.id.postal_office);
		country = (EditText) findViewById(R.id.country);
		
		tabHost.setup();
		tabHost.setOnTabChangedListener(this);
		setupTabs();
		
		gender.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog = dialogBuilder.buildDialog(genders,
						getString(R.string.navigation_type), gender);
				dialog.show();
			}
		});
		
		dateOfBirth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog = buildDateDialog();
				dialog.show();
			}
		});
		
	}

	@SuppressLint("NewApi")
	private void setupTabs() {
		TabHost.TabSpec spec;

		spec = tabHost.newTabSpec(first).setIndicator(createTabView(getString(R.string.basic)))
				.setContent(new TabContentFactory() {

					@Override
					public View createTabContent(String tag) {
						return basicTab;
					}
				});
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec(first).setIndicator(createTabView(getString(R.string.contact)))
				.setContent(new TabContentFactory() {

					@Override
					public View createTabContent(String tag) {
						return contactTab;
					}
				});
		tabHost.addTab(spec);
		
		spec = tabHost.newTabSpec(first).setIndicator(createTabView(getString(R.string.address)))
				.setContent(new TabContentFactory() {

					@Override
					public View createTabContent(String tag) {
						return addressTab;
					}
				});
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(2);
		tabHost.setCurrentTab(1);
		tabHost.setCurrentTab(0);
	}

	private void getData() {
		url = APICalls.getProfileUrl(user.get(SessionManager.KEY_SESSION_ID));
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
			public void onSuccess(JSONObject json) {
				reader = json.toString();
				response = gson.fromJson(reader, APIJsonResponseModel.class);
				if (response.getStatus() == null) {
					profile = gson.fromJson(reader, APIProfileModel.class);
					mapData();
				} else {
					hideLoadingOverlay();
					buildOkDialog(response.getMessage(), false);
				}
			}
		});
	}

	private void saveData() {

		url = APICalls.getUpadateProfileUrl();
		showLoadingOverlay();

		APIUpdateProfileParams params = new APIUpdateProfileParams();
		params.setAddress(address.getText());
		params.setCountry(country.getText().toString());
		params.setDateOfBirth(dateForServer.getTime());
		params.setEmail(email.getText().toString());
		params.setName(name.getText().toString());
		params.setPhone(phone.getText().toString());
		params.setPostalOffice(postalOffice.getText().toString());
		params.setUsername(username.getText().toString());
		params.setUserId(profile.getUserId());
		params.setSurname(surname.getText().toString());
		if(gender.getText().toString().equalsIgnoreCase(genders.get(0))){
			params.setGender(1);
		}
		else{
			params.setGender(0);
		}
		
		client.put(url, params.getRequestParams(),
				new JsonHttpResponseHandler() {

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
					public void onSuccess(JSONObject json) {
						reader = json.toString();
						response = gson.fromJson(reader,
								APIJsonResponseModel.class);
						
						if (response.getStatus().equalsIgnoreCase(
								SmartResponseTypes.RESPONSE_OK)) {
							buildOkDialog(response.getMessage(), true);
						} else {
							buildOkDialog(response.getMessage(), false);
						}
						hideLoadingOverlay();
					}
				});
	}

	private void mapData() {
		username.setText(profile.getUsername());
		name.setText(profile.getName());
		surname.setText(profile.getSurname());
		if (profile.getEmail() != null) {
			email.setText(profile.getEmail());
		}

		if (profile.getPhone() != null) {
			phone.setText(profile.getPhone());
		}

		if (profile.getAddress() != null) {
			address.setText(profile.getAddress());
		}

		if (profile.getPostalOffice() != null) {
			postalOffice.setText(profile.getPostalOffice());
		}

		if (profile.getCountry() != null) {
			country.setText(profile.getCountry());
		}

		if(profile.getGender() != null){
			gender.setText(genders.get(1));
		}
		else{
			gender.setText(genders.get(0));
		}
		
		if(profile.getDateOfBirth() != 0){
			dateForServer = new Date();
			dateForServer.setTime(profile.getDateOfBirth());
			dateOfBirth.setText(dateForServer.getDate() + "." + (dateForServer.getMonth() + 1) + "." + (dateForServer.getYear() + 1900));	
		}
		
		hideLoadingOverlay();
	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
	}
	
	private Dialog buildDateDialog() {
		Calendar calendar = Calendar.getInstance();
		final DatePickerDialog dialog = new DatePickerDialog(
				this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						dateForServer = new Date(year-1900, monthOfYear, dayOfMonth,
								0, 0, 0);
						dateOfBirth.setText(dayOfMonth + "." + (monthOfYear+1)
								+ "." + year + ".");
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));

		return dialog;
	}
}
