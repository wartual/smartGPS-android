package com.smartgps.fragments;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.smartgps.R;
import com.smartgps.adapters.EventSpecialsAdapter;
import com.smartgps.models.api.foursquare.APIItemModel;

public class EventSpecialsFragment extends SherlockFragment{
	
	private ViewGroup rootView;
	private ListView list;
	private List<APIItemModel> specials;
	private EventSpecialsAdapter adapter;
	private TextView header;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		initUI(inflater, container);
		return rootView;
	}

	private void initUI(LayoutInflater inflater, ViewGroup container) {
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_event_specials,
				container, false);
		
		list = (ListView) rootView.findViewById(R.id.list);
		header = (TextView) rootView.findViewById(R.id.specials_header);
		
		header.setText(getString(R.string.specials) + "(" + specials.size() + ")");
		
		adapter = new EventSpecialsAdapter(getActivity(), specials);
		list.setAdapter(adapter);
	}

	public List<APIItemModel> getSpecials() {
		return specials;
	}

	public void setSpecials(List<APIItemModel> specials) {
		this.specials = specials;
	}

}
