package com.chris.hunger.baseImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.Bean.ShopBean;
import com.chris.hunger.InStoreActivity;
import com.chris.hunger.R;
import com.chris.hunger.StoreManagerActivity;
import com.chris.hunger.adapter.ListViewAdapter;
import com.chris.hunger.base.BasePager;
import com.chris.hunger.utils.GetURLUtil;
import com.chris.hunger.view.XListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页实现
 * 
 * @author Kevin
 * 
 */
public class TakeOutSystemPager extends BasePager implements XListView.IXListViewListener,View.OnClickListener {
	private TextView tvSearchStoreByAddress;
	private ImageView ivSearchStoreByName;
	private EditText etSearchStoreByAddress;
	private XListView lv_take_out;
	private Handler mHandler;
	private static int refreshCnt = 0;
	private List<ShopBean.ShopListEntity> stores;
	private ListViewAdapter adapter;
	private ShopBean shopBean;
	private View takeout_detail_header;
	private LinearLayout llTitle;
	private ImageView tvSearchStoreByaddress;
	private TextView etSearchStoreByaddress;
	private XListView lvTakeOut;


	public TakeOutSystemPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initViews() {
		// TODO Auto-generated method stub
		View view = View.inflate(mActivity, R.layout.takeout_detail_pager, null);
		stores=new ArrayList<>();
		lv_take_out = (XListView) view.findViewById(R.id.lv_take_out);
		lv_take_out.setPullLoadEnable(true);
	    lv_take_out.setPullRefreshEnable(true);
		takeout_detail_header = View.inflate(mActivity, R.layout.takeout_detail_header, null);
		lv_take_out.addHeaderView(takeout_detail_header);
		lv_take_out.setXListViewListener(this);
		mHandler = new Handler();
		llTitle = (LinearLayout) view.findViewById(R.id.ll_title);
		tvSearchStoreByaddress = (ImageView) view.findViewById(R.id.tv_search_store_byaddress);
		etSearchStoreByaddress = (TextView) view.findViewById(R.id.et_search_store_byaddress);
		lvTakeOut = (XListView) view.findViewById(R.id.lv_take_out);
		return view;
	}

	class MyItemClick implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent=new Intent(mActivity, InStoreActivity.class);
			mActivity.startActivity(intent);
		}
	}
	@Override
	public void initData() {

//		String cache=CacheUtils.getCache(GetURLUtil.SHOP_BASEURL,mActivity);
//		if (!TextUtils.isEmpty(cache)){
//			parseData(cache);
//			Log.d("tag","ccccc"+cache);
//		}
		getDataFromServer();
	}
	private void getDataFromServer() {
		Intent intent=mActivity.getIntent();
		int FLAG=intent.getIntExtra("FLAG",1);
		RequestParams params = new RequestParams(GetURLUtil.SHOP_BASEURL+"id="+FLAG);
		Log.d("tag", "进入xtuil了");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				Log.d("tag", "成功了" + result);
				parseData(result);

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				Toast.makeText(mActivity, "网络故障", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCancelled(CancelledException cex) {
				Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFinished() {
			}
		});
	}
	protected void parseData(String result) {
		Gson gson = new Gson();
		stores.clear();


		shopBean = gson.fromJson(result, ShopBean.class);
		List<ShopBean.ShopListEntity> shops = shopBean.getShopList();
		Log.d("tag", shops.size() + "mmmmmmmm" + result);
		stores.addAll(shops);
		Log.d("tag", stores.size() + "===========================");

		lv_take_out.setOnItemClickListener(new MyItemClick());
		adapter = new ListViewAdapter(mActivity, stores,R.layout.shop_info_item);
		lv_take_out.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
	}
	private void onLoad() {
		lv_take_out.stopRefresh();
		lv_take_out.stopLoadMore();
		lv_take_out.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				stores.clear();
				initData();
				adapter = new ListViewAdapter(mActivity, stores, R.layout.shop_info_item);
				lv_take_out.setAdapter(adapter);
				onLoad();
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				moreData();
				onLoad();
			}
		}, 1000);
	}
	protected void moreData(){
		RequestParams params = new RequestParams(GetURLUtil.SHOP_BASEURL);
		Log.d("tag", "进入xtuil了");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				Log.d("tag", "成功了" + result);
				Gson gson = new Gson();
				shopBean = gson.fromJson(result, ShopBean.class);
				List<ShopBean.ShopListEntity> shops = shopBean.getShopList();
				Log.d("tag", shops.size() + "mmmmmmmm" + result);
				stores.addAll(shops);
				Log.d("tag", stores.size() + "===========================");
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
//				Toast.makeText(mActivity, "输入信息有误，请重新填写", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCancelled(CancelledException cex) {
				Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFinished() {
			}
		});
	}

	@Override
	public void onClick(View v) {
		int key = v.getId();
		switch (key) {
			case R.id.ll_cake:
				Intent intent = new Intent(mActivity, StoreManagerActivity.class);
				mActivity.startActivity(intent);
				break;
			case R.id.ll_fruit:
				Intent intent1 = new Intent(mActivity, StoreManagerActivity.class);
				mActivity.startActivity(intent1);
				break;
			case R.id.ll_market:
				Intent intent2 = new Intent(mActivity, StoreManagerActivity.class);
				mActivity.startActivity(intent2);
				break;
			case R.id.ll_nice_food:
				Intent intent3 = new Intent(mActivity, StoreManagerActivity.class);
				mActivity.startActivity(intent3);
				break;
			case R.id.ll_suger:
				Intent intent4 = new Intent(mActivity, StoreManagerActivity.class);
				mActivity.startActivity(intent4);
				break;
			default:
				break;
		}
	}
}
