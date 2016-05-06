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
import android.widget.TextView;

import com.chris.hunger.Bean.CollectionBean;
import com.chris.hunger.InStoreActivity;
import com.chris.hunger.R;
import com.chris.hunger.utils.PrefUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

///* import com.lidroid.xutils.BitmapUtils;

public class MyFavouriteAdapter extends BaseAdapter {

	private final Context context;
	private LayoutInflater inflater;
//	private BitmapUtils utils;
	private int layout;
	private List<CollectionBean.FavouriteEntity> stores;

	public MyFavouriteAdapter(Context context, List<CollectionBean.FavouriteEntity> stores, int layout) {
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
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();

		}
		final CollectionBean.FavouriteEntity.ShopEntity shop=stores.get(position).getShop();
		BitmapUtils utils=new BitmapUtils(context);
		utils.display(holder.img, (String) shop.getShopphoto());
		holder.name.setText(shop.getName());
		holder.price.setText(shop.getAddress() + "店");
		holder.info.setText(shop.getPhone() + "");
		utils.display(holder.disc, (String) shop.getLicense());
//		final Double time=(Double)stores.get(position).getRegistertime();
		//可以看改变格式的效果
//		Log.d("tag",stores.get(position).getId()+"获取到id了");
		holder.shopInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent=new Intent(context, InStoreActivity.class);
				PrefUtils.setString(context, "storeName", shop.getName() );
				PrefUtils.setString(context,"storePhone",shop.getPhone());
				PrefUtils.setString(context,"storeLicense",shop.getLicense());
				PrefUtils.setString(context,"storeShopphoto",shop.getShopphoto());

//				PrefUtils.setString(context,"storeTime",time+"");
				PrefUtils.setString(context,"storeAddress",shop.getAddress());
//				PrefUtils.setString(context,"storeId",shop.getId()+"");
				PrefUtils.setString(context, "shopId", shop.getId() + "");
				context.startActivity(intent);

			}
		});
		Log.d("tag","已经进入收藏列表了");
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
	}

}

