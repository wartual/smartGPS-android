package com.smartgps.utils;

import java.util.HashMap;

import com.smartgps.activities.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {

	// Shared Preferences
	private SharedPreferences pref;

	// Editor for Shared preferences
	private Editor editor;

	// Context
	private Context context;

	 // Sharedpref file name
    private static final String PREF_NAME = "iHelpUserManagement";
    
    // Shared pref mode
    int PRIVATE_MODE = 0;
	
	public static final String KEY_NAME = "username";

	public static final String KEY_SESSION_ID = "session_id";
	
	private static final String IS_LOGIN = "IsLoggedIn";
	
	public SessionManager(Context context) {
		this.context = context;
		this.pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		this.editor = pref.edit();
	}

	/**
     * Create login session
     * */
    public void createLoginSession(String name, String sessionId){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
         
        // Storing name in pref
        editor.putString(KEY_NAME, name);
         
        // Storing session id in pref
        editor.putString(KEY_SESSION_ID, sessionId);
         
        // commit changes
        editor.commit();
    }   
    
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
         
        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         
        // Staring Login Activity
        context.startActivity(i);
    }
    
    /**
    * Get stored session data
    * */
   public HashMap<String, String> getUserDetails(){
       HashMap<String, String> user = new HashMap<String, String>();
       // user name
       user.put(KEY_NAME, pref.getString(KEY_NAME, null));
       
       // user session id
       user.put(KEY_SESSION_ID, pref.getString(KEY_SESSION_ID, null));
       
       // return user
       return user;
   }
    
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
