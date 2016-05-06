package com.chris.hunger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chris.hunger.Bean.Order_good;
import com.chris.hunger.R;

import java.util.List;

public class OrderGoodListAdapter extends BaseAdapter {

 	private LayoutInflater inflater;
    Context c;
	private List<Order_good> goods;

	public OrderGoodListAdapter(Context context, List<Order_good> goods) {
		inflater = LayoutInflater.from(context);
		this.goods=goods;
		c=context;
	}

	//获取列表项的总数
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		return 5;
		return goods.size();
	}

	//获取每一个列表项
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return goods.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
		    convertView= inflater.inflate(R.layout.order_good_detail, null);
			holder = new ViewHolder();
			holder.goodname = (TextView) convertView.findViewById(R.id.good_name);
			holder.goodcount = (TextView) convertView.findViewById(R.id.good_num);
			holder.goodprice = (TextView) convertView.findViewById(R.id.good_price);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.goodname.setText(goods.get(position).getGoodname());
		holder.goodcount.setText(goods.get(position).getGoodcount().toString());
		holder.goodprice.setText(goods.get(position).getGoodprice().toString());
		return convertView;

	}

	static class ViewHolder {
		public TextView goodname ;
		public TextView goodcount;
		public TextView goodprice;

	}

}

