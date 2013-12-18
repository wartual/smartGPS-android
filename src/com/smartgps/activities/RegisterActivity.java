package com.smartgps.activities;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.actionbarsherlock.view.MenuItem;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartgps.R;
import com.smartgps.models.SmartResponseTypes;
import com.smartgps.models.api.APIJsonResponseModel;
import com.smartgps.params.APICreateUserParams;
import com.smartgps.utils.APICalls;
import com.smartgps.utils.Utilities;

public class RegisterActivity extends BaseActivity{

	private EditText username;
	private EditText password;
	private EditText confirmPassword;
	private EditText name;
	private EditText surname;
	private BootstrapButton register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		setActionbarTitle(R.string.register_new_user);
		initUI();
	}
	
	private void initUI(){
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		confirmPassword = (EditText) findViewById(R.id.confirm_password);
		name = (EditText) findViewById(R.id.name);
		surname = (EditText) findViewById(R.id.surname);
		register = (BootstrapButton) findViewById(R.id.register);
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				registerUser();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		handleOnBackPressed();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		handleOnBackPressed();
		
		return super.onOptionsItemSelected(item);
	}
	
	private void handleOnBackPressed(){
		Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	private void registerUser(){
		if(TextUtils.isEmpty(username.getText().toString())
				|| TextUtils.isEmpty(password.getText().toString())
				|| TextUtils.isEmpty(name.getText().toString())
				|| TextUtils.isEmpty(surname.getText().toString())){
			buildOkDialog(getString(R.string.missing_input_data), false);
		}
		else if(password.getText().length() < 8){
			buildOkDialog(getString(R.string.password_validation_message), false);
		}
		else if(!password.getText().toString().equals(confirmPassword.getText().toString())){
			buildOkDialog(getString(R.string.passwords_dont_match), false);		
		}
		else{
			showLoadingOverlay();

			url = APICalls.getRegisterUrl();
			APICreateUserParams registerParams = new APICreateUserParams();
			registerParams.setUsername(username.getText().toString());
			registerParams.setPassword(password.getText().toString());
			registerParams.setName(name.getText().toString());
			registerParams.setSurname(surname.getText().toString());
			
			client.post(url, registerParams.getRequestParams() ,new JsonHttpResponseHandler(){
				
				@Override
				public void onFailure(Throwable error, String content) {
					if(error.getCause() instanceof ConnectTimeoutException){
						buildOkDialog(getString(R.string.connection_timeout_has_occured), false);
					}
				}

				@Override
				public void onSuccess(JSONObject json) {
					reader = json.toString();
					response = gson.fromJson(reader, APIJsonResponseModel.class);
					if(response.getStatus().equalsIgnoreCase(SmartResponseTypes.RESPONSE_OK)){
						sessionManager.createLoginSession(username.getText()
								.toString(), response.getMessage());
						Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
					else{
						buildOkDialog(response.getMessage(), false);
					}
					hideLoadingOverlay();
				}
			});
		}
	}
}
