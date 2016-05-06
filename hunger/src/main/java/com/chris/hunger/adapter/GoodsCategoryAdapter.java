package com.chris.hunger.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chris.hunger.R;

import java.util.List;

/**
 * Created by huyucheng on 2016/3/21.
 */
public class GoodsCategoryAdapter extends BaseAdapter{

    private Context context;
    private int layout;
    private List<String> array;
    private LayoutInflater inflater;

    public GoodsCategoryAdapter(Context context,int layout,List<String> array){
        this.context =context;
        this.layout =layout;
        this.array =array;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.CategoryName = (TextView) convertView.findViewById(R.id.category_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.CategoryName.setText(array.get(position));
        holder.CategoryName.setTextColor(Color.rgb(253,147,75));
        return convertView;
    }
    static class ViewHolder {
        public TextView CategoryName;

    }
}
