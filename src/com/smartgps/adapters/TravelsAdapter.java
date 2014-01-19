package com.smartgps.adapters;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartgps.R;
import com.smartgps.models.APITravelModel;
import com.smartgps.models.api.foursquare.APIItemsModel;
import com.smartgps.models.dao.TravelCategoriesDao;
import com.smartgps.utils.Utilities;

public class TravelsAdapter extends BaseAdapter{
	
	private Context context;
	private List<APITravelModel> travels;
	private LayoutInflater inflater;
	
	private TextView destination;
	private TextView date;
	private TextView status;
	
	public TravelsAdapter(Context ctx, List<APITravelModel> travels){
		this.context = ctx;
		this.travels = travels;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return travels.size();
	}

	@Override
	public Object getItem(int position) {
		return travels.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.row_travels, null);
	
		destination = (TextView) convertView.findViewById(R.id.destination);
		date = (TextView) convertView.findViewById(R.id.date);
		status = (TextView) convertView.findViewById(R.id.status);
		
		destination.setText(context.getString(R.string.destination) + ": " + travels.get(position).getDestinationAddress());
		date.setText(new Date(travels.get(position).getDateCreated()).toLocaleString());
		status.setText(TravelCategoriesDao.getByStatusId(travels.get(position).getStatusId()).getType());
		return convertView;
	}
}