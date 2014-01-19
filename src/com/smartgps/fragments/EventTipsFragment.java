package com.smartgps.fragments;

import java.util.List;

import android.media.JetPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.smartgps.R;
import com.smartgps.adapters.EventTipsAdapter;
import com.smartgps.models.api.foursquare.APITipsModel;

public class EventTipsFragment extends SherlockFragment{
	
	private ViewGroup rootView;
	private ListView list;
	private List<APITipsModel> tips;
	private EventTipsAdapter adapter;
	private TextView header;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		initUI(inflater, container);
		return rootView;
	}

	private void initUI(LayoutInflater inflater, ViewGroup container) {
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_event_tips,
				container, false);
		
		list = (ListView) rootView.findViewById(R.id.list);
		header = (TextView) rootView.findViewById(R.id.tips_header);
		
		header.setText(getString(R.string.tips) + "(" + tips.size() + ")");
		
		adapter = new EventTipsAdapter(getActivity(), tips);
		list.setAdapter(adapter);
	}

	public List<APITipsModel> getTips() {
		return tips;
	}

	public void setTips(List<APITipsModel> tips) {
		this.tips = tips;
	}
}
