package com.smartgps.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

	public static class Tab {

		/**
		 * Tab title.
		 */
		public String title;

		/**
		 * Contents of the tab.
		 */
		public View tabView;
	}

	private final ArrayList<Tab> tabs;

	public ViewPagerAdapter() {
		tabs = new ArrayList<ViewPagerAdapter.Tab>();
	}

	public void addTab(String title, View tabView) {
		Tab tab = new Tab();
		tab.title = title;
		tab.tabView = tabView;

		tabs.add(tab);
	}

	@Override
	public int getCount() {
		return tabs.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public String getPageTitle(int position) {
		return tabs.get(position).title;
	}

	@Override
	public Object instantiateItem(View pager, int position) {
		((ViewPager) pager).addView(tabs.get(position).tabView);
		return tabs.get(position).tabView;
	}

	@Override
	public void destroyItem(View pager, int position, Object object) {
		((ViewPager) pager).removeView((View) object);
	}

}
