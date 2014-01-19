package com.smartgps.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.smartgps.R;
import com.smartgps.activities.TravelActivity;
import com.smartgps.adapters.TravelsAdapter;
import com.smartgps.models.APITravelModel;

public class TravelsListFragment extends SherlockFragment {
	
	public ArrayList<APITravelModel> travels;
	private ListView list;
	private TravelsAdapter adapter;
	private ViewGroup rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		initUI(inflater, container);
		
		if(travels != null && travels.size() != 0){
			mapData();
		}
		return rootView;
	}

	private void initUI(LayoutInflater inflater, ViewGroup container) {
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_travels,
				container, false);
		
		list = (ListView) rootView.findViewById(R.id.list);
	}

	private void mapData() {
		adapter = new TravelsAdapter(getActivity(), travels);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), TravelActivity.class);
				intent.putExtra(TravelActivity.TRAVEL, travels.get(position));
				startActivity(intent);
				getActivity().finish();
			}
		});
	}

	public ArrayList<APITravelModel> getTravels() {
		return travels;
	}

	public void setTravels(ArrayList<APITravelModel> travels) {
		this.travels = travels;
	}
}

