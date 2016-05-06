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

public class PaixuAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	String [] sort;
	int last_item;
	int [] images;
	public PaixuAdapter(Context context, String[] sort){
		this.context = context;
		this.sort = sort;
		inflater=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sort.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder  holder = null;
	    if(convertView==null){
	    convertView = inflater.inflate(R.layout.sort_item, null);
	    holder = new ViewHolder();
        holder.textView =(TextView)convertView.findViewById(R.id.textview);
//        holder.imageView =(ImageView)convertView.findViewById(R.id.imageview);
        holder.layout=(LinearLayout)convertView.findViewById(R.id.colorlayout);
        convertView.setTag(holder);
	    }
	    else{
	    holder=(ViewHolder)convertView.getTag();
	    }
//	     if(selectedPosition == position)
//	    {
//	    	 holder.textView.setTextColor(Color.BLUE);
//
//
//	     holder.layout.setBackgroundColor(Color.LTGRAY);
//	   } else {
//		   holder.textView.setTextColor(Color.WHITE);
//	    holder.layout.setBackgroundColor(Color.TRANSPARENT);
//	     }
		Log.d("tag","一级菜单adapter已进入"+sort[position]);
	   
	    holder.textView.setText(sort[position]);
	    holder.textView.setTextColor(Color.BLACK);
//	    holder.imageView.setBackgroundResource(images[position]);
	    
		return convertView;
	}

	public static class ViewHolder{
		public TextView textView;
//		public ImageView  imageView;
		public LinearLayout layout;
	}

//	public void setSelectedPosition(int position) {
//	   selectedPosition = position;
//	}

}
