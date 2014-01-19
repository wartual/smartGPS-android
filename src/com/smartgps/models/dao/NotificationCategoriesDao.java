package com.smartgps.models.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartgps.models.APINotificationCategories;
import com.smartgps.utils.APICalls;

public class NotificationCategoriesDao {

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void populate(String userId) {
		deleteAll();
		String url = APICalls.getNotificationCategoriesUrl(userId); 
		Log.d("DOHVAĆAM NOTIFICATION CATEGORIES", url);
		client.get(url,
				new JsonHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error, String content) {
						Log.d("GET ALL CATEGORIES", "FALSE");
					}

					@Override
					public void onSuccess(JSONArray json) {
						GsonBuilder gsonBuilder = new GsonBuilder();
						gsonBuilder.setDateFormat("M/d/yy hh:mm a");
						Gson gson = gsonBuilder.create();
						String reader = json.toString();
						ArrayList<APINotificationCategories> categories = new ArrayList<APINotificationCategories>(
								Arrays.asList(gson.fromJson(reader,
										APINotificationCategories[].class)));
						ActiveAndroid.beginTransaction();
						try {
							for(APINotificationCategories category : categories){
								createNew(category);
							}
							ActiveAndroid.setTransactionSuccessful();
						} finally {
							ActiveAndroid.endTransaction();
						}

					}
				});
	}

	public static void createNew(String categoryId, String type) {
		APINotificationCategories category = new APINotificationCategories();
		category.setCategoryId(categoryId);
		category.setType(type);
		category.save();
	}

	public static void createNew(APINotificationCategories category) {
		Log.d("DODAJEM", category.getType() + ", " + category.getCategoryId());
		category.save();
	}

	public static void deleteAll() {
		try{
			new Delete().from(APINotificationCategories.class).execute();			
		}
		catch(Exception e){
			
		}
	}
	
	public static List<APINotificationCategories> getAll(){
		try{
			return new Select().from(APINotificationCategories.class).execute();
		}
		catch(Exception e){
			return null;
		}
	}
	
	public static APINotificationCategories getByType(String type){
		try{
			return new Select().from(APINotificationCategories.class).where("Type = ?", type).executeSingle();
		}
		catch(Exception e){
			return null;
		}
	}
}
