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
import com.smartgps.models.APITravelStatusCategories;
import com.smartgps.utils.APICalls;

public class TravelCategoriesDao {

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void populate(String userId) {
		deleteAll();
		String url = APICalls.getTravelStatusCategoriesUrl(userId);
		Log.d("DOHVAĆAM TRAVEL CATEGORIES", url);
		client.get(url,
				new JsonHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error, String content) {
						Log.d("GET ALL TRAVEL STATUS CATEGORIES", "FALSE");
					}

					@Override
					public void onSuccess(JSONArray json) {
						GsonBuilder gsonBuilder = new GsonBuilder();
						gsonBuilder.setDateFormat("M/d/yy hh:mm a");
						Gson gson = gsonBuilder.create();
						String reader = json.toString();
						ArrayList<APITravelStatusCategories> categories = new ArrayList<APITravelStatusCategories>(
								Arrays.asList(gson.fromJson(reader,
										APITravelStatusCategories[].class)));
						ActiveAndroid.beginTransaction();
						try {
							for(APITravelStatusCategories category : categories){
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
		APITravelStatusCategories category = new APITravelStatusCategories();
		category.setStatusId(categoryId);
		category.setType(type);
		category.save();
	}

	public static void createNew(APITravelStatusCategories category) {
		Log.d("DODAJEM", category.getType() + ", " + category.getStatusId());
		category.save();
	}

	public static void deleteAll() {
		try{
			new Delete().from(APITravelStatusCategories.class).execute();			
		}
		catch(Exception e){
			
		}
	}
	
	public static List<APITravelStatusCategories> getAll(){
		try{
			return new Select().from(APITravelStatusCategories.class).execute();
		}
		catch(Exception e){
			return null;
		}
	}
	
	public static APITravelStatusCategories getByType(String type){
		try{
			return new Select().from(APITravelStatusCategories.class).where("Type = ?", type).executeSingle();
		}
		catch(Exception e){
			return null;
		}
	}
	
	public static APITravelStatusCategories getByStatusId(String statusId){
		try{
			return new Select().from(APITravelStatusCategories.class).where("StatusId = ?", statusId).executeSingle();
		}
		catch(Exception e){
			return null;
		}
	}
}
