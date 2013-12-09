package com.smartgps.activities;

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
import com.smartgps.utils.APICalls;
import com.smartgps.utils.Utilities;

public class LoginActivity extends BaseActivity{
	
	private EditText username;
	private EditText password;
	private BootstrapButton login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initUI();
	}

	private void initUI(){
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login = (BootstrapButton) findViewById(R.id.login);
	
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loginUser();
			}
		});
	}
	
	private void loginUser(){
		if(TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText())){
			Utilities.buildOkDialog(getString(R.string.missing_input_data), LoginActivity.this, false);
		}
		else{
			url = APICalls.getLoginUrl(username.getText().toString(), password.getText().toString());
			showLoadingOverlay();
			client.get(url, new JsonHttpResponseHandler(){

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
						Utilities.buildOkDialog(response.getMessage(), LoginActivity.this, false);
					}
					hideLoadingOverlay();
				}
			});
		}
	}
}
