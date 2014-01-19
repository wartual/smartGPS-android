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
import com.smartgps.models.api.foursquare.APIItemModel;
import com.smartgps.models.api.foursquare.APIItemsModel;
import com.smartgps.models.api.foursquare.APISpecialsModel;
import com.smartgps.models.api.foursquare.APITipsModel;

public class EventSpecialsAdapter  extends BaseAdapter{
	
	private Context context;
	private List<APIItemModel> specials;
	private LayoutInflater inflater;
	
	private TextView title;
	private TextView description;
	
	public EventSpecialsAdapter(Context ctx, List<APIItemModel> specials){
		this.context = ctx;
		this.specials = specials;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return specials.size();
	}

	@Override
	public Object getItem(int position) {
		return specials.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.row_event_specials, null);
		title = (TextView) convertView.findViewById(R.id.title);
		description = (TextView) convertView.findViewById(R.id.description);
		
		title.setText(specials.get(position).getTitle());
		description.setText(specials.get(position).getMessage());
		return convertView;
	}

}
