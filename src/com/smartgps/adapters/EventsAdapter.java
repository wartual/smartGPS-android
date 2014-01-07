package com.smartgps.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartgps.R;
import com.smartgps.models.api.foursquare.APIEventsModel;
import com.smartgps.models.api.foursquare.APIItemsModel;
import com.smartgps.models.api.foursquare.APILocationModel;
import com.smartgps.models.api.places.APIPlacesModel;
import com.smartgps.utils.Utilities;

public class EventsAdapter extends BaseAdapter{
	
	private Context context;
	private List<APIItemsModel> events;
	private LayoutInflater inflater;
	private Location myLocation;
	
	private TextView name;
	private TextView vicinity;
	private TextView distance;
	
	public EventsAdapter(Context ctx, List<APIItemsModel> events, Location location){
		this.context = ctx;
		this.events = events;
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
		
		name.setText(events.get(position).getVenue().getName());
		vicinity.setText(getAddress(events.get(position).getVenue().getLocation()));
		
		Location location = new Location("");
		location.setLatitude(events.get(position).getVenue().getLocation().getLatitude());
		location.setLongitude(events.get(position).getVenue().getLocation().getLongitude());	
		distance.setText(Utilities.formatDistance(Utilities.getDistance(myLocation, location)));
		
		return convertView;
	}
	
	private String getAddress(APILocationModel location){
		String address = "";
		if(location.getAddress() != null){
			address = address + location.getAddress();
		}
		
		if(location.getCity() != null){
			address = address + ", " + location.getCity();
		}
		
		if(location.getCountry() != null){
			address = address + ", " + location.getCountry();
		}
		
		return address;
	}
}
