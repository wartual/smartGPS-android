package com.smartgps.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.smartgps.R;
import com.smartgps.adapters.MergeAdapter;
import com.smartgps.interfaces.DialogTextCommunicator;

public class DialogBuilder{
	
	private Activity activity;
	private DialogTextCommunicator communicator;
	
	public DialogBuilder(Activity activity){
		this.activity = activity; 
	}

	public Dialog buildDialog(final ArrayList<String> array, String text,
			final TextView tv) {
			
		communicator = (DialogTextCommunicator) activity;	
		
		MergeAdapter adapter = new MergeAdapter();

		final Dialog dialog = new Dialog(activity, R.style.CustomDialogTheme);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View dialogView = inflater.inflate(R.layout.dialog_list, null);
		ListView list = (ListView) dialogView
				.findViewById(R.id.dialog_listview);

		adapter.addView(header(activity,R.layout.dialog_header, text));
		adapter.addAdapter(new DialogAdapter(activity,
				array));

		list.setAdapter(adapter);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				tv.setText(array.get(position - 1).toString());
				communicator.passTextToActivity(tv.getText().toString());
				dialog.dismiss();
			}
		});

		dialog.setContentView(dialogView);

		return dialog;
	}
	
	public View header(Context context, int inflateLayout, String text) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(inflateLayout, null);

		TextView item = (TextView) view.findViewById(R.id.tv_title);
		item.setText(text);

		return view;
	}

	class DialogAdapter extends ArrayAdapter<String> {
		DialogAdapter(Context context, ArrayList<String> array) {
			super(context, R.layout.dialog_row, R.id.item, array);
		}
	}
}
