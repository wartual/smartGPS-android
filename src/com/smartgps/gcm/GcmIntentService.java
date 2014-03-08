package com.smartgps.gcm;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.smartgps.R;
import com.smartgps.activities.MainActivity;
import com.smartgps.activities.navigation.NavigationActivity;
import com.smartgps.activities.navigation.SmartNavigationActivity;

public class GcmIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    private static final String TAG = "GcmIntentService";

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("GcmIntentService", "onHandlerIntent");
        
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if(!extras.isEmpty() && extras.get("travelId") != null){
        	showNodesNotification(extras);
        }
        else if(!extras.isEmpty()){
        	showNotification(extras);
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
    
    private void showNodesNotification(Bundle extras){
    	Log.d("nodes notification", extras.toString());
    	
    	NotificationCompat.Builder mBuilder =
    	        new NotificationCompat.Builder(this)
    	        .setSmallIcon(R.drawable.logo)
    	        .setContentTitle(getString(R.string.app_name))
    	        .setContentText("Travel directions obtained!")
    	        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
    	
    	Intent intent = new Intent(GcmIntentService.this, SmartNavigationActivity.class);
    	intent.putExtra(SmartNavigationActivity.TRAVEL_ID, extras.getString("travelId"));
    	intent.putExtra(SmartNavigationActivity.TRAVEL_DATA, extras.getString("travelData"));
    	
    	
    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    	
    	intent.putExtra(MainActivity.IS_NOTIFICATION, true);
    	
    	PendingIntent pendingIntent = PendingIntent.getActivity(
    		      this, 
    		      0, 
    		      intent,  
    		      Intent.FLAG_ACTIVITY_NEW_TASK);
    	
    	mBuilder.setContentIntent(pendingIntent);
    	mBuilder.setAutoCancel(true);
    
    	NotificationManager mNotificationManager =
    	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	// mId allows you to update the notification later on.
    	int mId = 001;
    	mNotificationManager.notify(mId, mBuilder.build());
    	
    }
    
    @SuppressLint("NewApi")
	private void showNotification(Bundle extras){
    	Log.d("extras", extras.toString());
    	String message = extras.getString("text");
    	
    	NotificationCompat.Builder mBuilder =
    	        new NotificationCompat.Builder(this)
    	        .setSmallIcon(R.drawable.logo)
    	        .setContentTitle(getString(R.string.app_name))
    	        .setContentText(message)
    	        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
    	
    	Intent intent = new Intent(GcmIntentService.this, NavigationActivity.class);
    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    	
    	intent.putExtra(MainActivity.IS_NOTIFICATION, true);
    	
    	PendingIntent pendingIntent = PendingIntent.getActivity(
    		      this, 
    		      0, 
    		      intent,  
    		      Intent.FLAG_ACTIVITY_NEW_TASK);
    	
    	mBuilder.setContentIntent(pendingIntent);
    	mBuilder.setAutoCancel(true);
    
    	NotificationManager mNotificationManager =
    	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	// mId allows you to update the notification later on.
    	int mId = 001;
    	mNotificationManager.notify(mId, mBuilder.build());
    }
}
