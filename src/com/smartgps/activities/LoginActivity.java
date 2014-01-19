package com.smartgps.activities;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartgps.R;
import com.smartgps.models.SmartResponseTypes;
import com.smartgps.models.api.APIJsonResponseModel;
import com.smartgps.params.APILoginParams;
import com.smartgps.utils.APICalls;
import com.smartgps.utils.SessionManager;

public class LoginActivity extends BaseActivity{
	
	private EditText username;
	private EditText password;
	private BootstrapButton login;
	private BootstrapButton register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		if(user.get(SessionManager.KEY_SESSION_ID) != null){
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		
		initUI();
	}

	private void initUI(){
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login = (BootstrapButton) findViewById(R.id.login);
		register = (BootstrapButton) findViewById(R.id.register);
	
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loginUser();
			}
		});
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	
	private void loginUser(){
		if(TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText())){
			buildOkDialog(getString(R.string.missing_input_data), false);
			hideLoadingOverlay();
		}
		else{
			showLoadingOverlay();

			url = APICalls.getLoginUrl();
			APILoginParams loginParams = new APILoginParams();
			loginParams.setUsername(username.getText().toString());
			loginParams.setPassword(password.getText().toString());
			
			client.put(url, loginParams.getRequestParams() ,new JsonHttpResponseHandler(){
				
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
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
