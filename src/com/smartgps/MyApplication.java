package com.smartgps;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smartgps.models.dao.NotificationCategoriesDao;
import com.smartgps.utils.ProjectConfig;

public class MyApplication extends Application{

	
	@Override
	public void onCreate() {
		super.onCreate();
		ActiveAndroid.initialize(this);
		
		
		DisplayImageOptions imageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).build();
		ImageLoaderConfiguration imageLoader = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(imageOptions).build();
		ImageLoader.getInstance().init(imageLoader);
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}
}
