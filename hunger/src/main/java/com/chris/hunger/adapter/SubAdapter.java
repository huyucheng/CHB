package com.chris.hunger.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chris.hunger.R;

public class SubAdapter extends BaseAdapter {
	
	Context context;
	LayoutInflater layoutInflater;
	String[][] cities;
	public int foodpoition;
	private LinearLayout ll_menu_list;
	public SubAdapter(Context context, String[][] cities,int position,LinearLayout ll_menu_list) {
		this.context = context;
		this.cities = cities;
		this.ll_menu_list=ll_menu_list;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.foodpoition = position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cities.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		final int location=position;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.sublist_item, null);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.textview1);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Log.d("tag", "进入二级菜单adapter了"+cities[foodpoition][position]+"000000000");
		viewHolder.textView.setText(cities[foodpoition][position]);
		viewHolder.textView.setTextColor(Color.BLACK);
		viewHolder.textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ll_menu_list.setVisibility(View.GONE);
			}
		});
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
	}

}
