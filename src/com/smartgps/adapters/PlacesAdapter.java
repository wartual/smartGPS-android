package com.smartgps.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartgps.R;
import com.smartgps.models.api.places.APIPlacesModel;
import com.smartgps.utils.Utilities;

public class PlacesAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<APIPlacesModel> events;
	private LayoutInflater inflater;
	private Location myLocation;
	
	private TextView name;
	private TextView vicinity;
	private TextView distance;
	
	public PlacesAdapter(Context ctx, ArrayList<APIPlacesModel> places, Location location){
		this.context = ctx;
		this.events = places;
		this.myLocation = location;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return events.size();
	}

	@Override
	public Object getItem(int position) {
		return events.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.row_places, null);
	
		name = (TextView) convertView.findViewById(R.id.name);
		vicinity = (TextView) convertView.findViewById(R.id.vicinity);
		distance = (TextView) convertView.findViewById(R.id.distance);
		
		name.setText(events.get(position).getName());
		vicinity.setText(events.get(position).getVicinity());
		
		Location location = new Location("");
		location.setLatitude(events.get(position).getGeometry().getLocation().getLatitude());
		location.setLongitude(events.get(position).getGeometry().getLocation().getLongitude());	
		distance.setText(Utilities.formatDistance(Utilities.getDistance(myLocation, location)));
		
		return convertView;
	}

}
