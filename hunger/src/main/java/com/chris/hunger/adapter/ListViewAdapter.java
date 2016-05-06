package com.chris.hunger.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chris.hunger.Bean.ShopBean;
import com.chris.hunger.InStoreActivity;
import com.chris.hunger.R;
import com.chris.hunger.utils.PrefUtils;
import com.lidroid.xutils.BitmapUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

///* import com.lidroid.xutils.BitmapUtils;

public class ListViewAdapter extends BaseAdapter {

	private final Context context;
	private LayoutInflater inflater;
//	private BitmapUtils utils;
	private int layout;
	private List<ShopBean.ShopListEntity> stores;

	public ListViewAdapter(Context context, List<ShopBean.ShopListEntity> stores,int layout) {
		if (stores == null) {
			this.stores = new ArrayList<>();
		} else {
			this.stores = stores;
		}
		this.layout=layout;
		this.context =context;
//		utils = new BitmapUtils(context);
//		utils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return stores.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return stores.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(layout, null);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.store_info_image);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.price = (TextView) convertView.findViewById(R.id.price);
			holder.grade = (TextView) convertView.findViewById(R.id.grade);
			holder.send = (TextView) convertView.findViewById(R.id.send);
			holder.info = (TextView) convertView.findViewById(R.id.info);
			holder.pay = (TextView) convertView.findViewById(R.id.pay);
			holder.shopInfo=(LinearLayout)convertView.findViewById(R.id.ll_shop_info);
			holder.shop_star=(RatingBar)convertView.findViewById(R.id.shop_star);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();

		}
		BitmapUtils utils=new BitmapUtils(context);
		utils.display(holder.img, (String) stores.get(position).getShopphoto());
		Log.d("####",""+stores.get(position).getShopphoto());
		holder.name.setText(stores.get(position).getName());
		holder.price.setText(stores.get(position).getAddress() + "店");
//		holder.info.setText(stores.get(position).getPhone() + "");
		holder.send.setText(stores.get(position).getSendexpense() + "");
		holder.shop_star.setRating(stores.get(position).getRank() );
		String distance=(String)stores.get(position).getDistance();
//		String time=(String)stores.get(position).getSendtime();
		if (distance!=null){

			holder.info.setText("距离"+distance+"米");
		}
		int sales=stores.get(position).getCurmonthsales();
		holder.grade.setText("销量"+stores.get(position).getCurmonthsales()+"");
		final long time=(long)stores.get(position).getRegistertime();
		//可以看改变格式的效果
//		Log.d("tag",stores.get(position).getId()+"获取到id了");
		holder.shopInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String shopName=stores.get(position).getName();

				PrefUtils.setString(context, "storeName", shopName );
				PrefUtils.setString(context,"storePhone",(String)stores.get(position).getPhone());
				PrefUtils.setString(context,"storeLicense",(String)stores.get(position).getLicense());
				PrefUtils.setString(context,"storeShopphoto",(String)stores.get(position).getShopphoto());

				SimpleDateFormat s = new SimpleDateFormat("MM-dd hh:mm:ss");//这边改变格式
				Date d =  new Date(time*1000);
				String times = s.format(d);

				PrefUtils.setString(context,"storeTime",times+"");

				Log.d("*****",times+"fffffff");
				PrefUtils.setString(context,"storeAddress",(String)stores.get(position).getAddress());
				PrefUtils.setString(context,"storeId",stores.get(position).getId()+"");

				Log.d("tag", stores.get(position).getName() + "商店名为");
				Intent intent = new Intent(context, InStoreActivity.class);
				context.startActivity(intent);
			}
		});
		Log.d("tag","已经进入viewholder了");
		return convertView;
	}
	static class ViewHolder {
		public ImageView img;
		public TextView name;
		public TextView price;
		public TextView grade ;
		public TextView send ;
		public TextView info ;
		public TextView pay ;
		public ImageView disc ;
		public TextView discinfo;
		public LinearLayout shopInfo;
		public RatingBar shop_star;
	}

}

