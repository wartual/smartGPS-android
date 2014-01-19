package com.smartgps.adapters;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartgps.R;
import com.smartgps.models.api.foursquare.APITipsModel;

public class EventTipsAdapter extends BaseAdapter{
	
	private Context context;
	private List<APITipsModel> tips;
	private LayoutInflater inflater;
	
	private TextView name;
	private TextView date;
	private TextView tip;
	
	public EventTipsAdapter(Context ctx, List<APITipsModel> tips){
		this.context = ctx;
		this.tips = tips;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return tips.size();
	}

	@Override
	public Object getItem(int position) {
		return tips.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.row_event_tips, null);
		name = (TextView) convertView.findViewById(R.id.name);
		date = (TextView) convertView.findViewById(R.id.date);
		tip = (TextView) convertView.findViewById(R.id.tip);
		
		name.setText(tips.get(position).getUser().getFirstName());
		tip.setText((tips.get(position).getText()));
		
		Date dateCreated = new Date(tips.get(position).getCreatedAt());
		
		date.setText(dateCreated.toLocaleString());
		
		return convertView;
	}
}