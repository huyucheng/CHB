package com.chris.hunger;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.chris.hunger.application.MyApplication;
import com.chris.hunger.base.BasePager;
import com.chris.hunger.baseImpl.OrderSystemPager;
import com.chris.hunger.baseImpl.TakeOutSystemPager;
import com.chris.hunger.baseImpl.UserInfoSystemPager;
import com.chris.hunger.service.LocationService;
import com.chris.hunger.utils.PrefUtils;

import java.util.ArrayList;
public class MainActivity extends Activity {
	private RadioGroup rgGroup;

	private ViewPager mViewPager;
	public static final int TAKEOUT = 0;
	public static final int ORDER = 1;
	public static final int USERINFO = 2;
	private ArrayList<BasePager> mPagerList;
	private RadioButton rb_order;
	private RadioButton rb_takeout;
	private RadioButton rb_userInfo;
	private LocationService locationService;
	private TextView etSearchStore;
	public static  String LOCATION_CITY=null;
	public static  String LOCATION_DISTRICT=null;
	public static  String LOCATION_STREET=null;
	public static  String LOCATION_POIT=null;
	private LinearLayout ll_title;
	private ImageView searchStroreByName;
	private int netType;
	private View loadAgain;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		netType = UserInfoSystemPager.getNetType(MainActivity.this);
//		if (netType == -1) {
//			setContentView(R.layout.activity_no_net_address);
//			loadAgain = findViewById(R.id.btn_load_again);
//			loadAgain.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					int netType = UserInfoSystemPager.getNetType(MainActivity.this);
//					if (netType != -1) {
//
//						initViews();
//					} else {
//						Toast.makeText(MainActivity.this, "网络异常，请重新设置", Toast.LENGTH_SHORT).show();
//
//					}
//				}
//			});
//		} else {

			initViews();
//		}


    }
    public void initViews() {
		setContentView(R.layout.main_activity);
		mViewPager=(ViewPager) findViewById(R.id.vp_content);
		etSearchStore =(TextView) findViewById(R.id.et_search_store_byaddress);
		rgGroup=(RadioGroup) findViewById(R.id.rg_group);
		ll_title =(LinearLayout)findViewById(R.id.ll_title);
		rb_order = (RadioButton) findViewById(R.id.rb_order);	
		rb_takeout = (RadioButton) findViewById(R.id.rb_takeout);	
		rb_userInfo = (RadioButton) findViewById(R.id.rb_userInfo);
		searchStroreByName =(ImageView)findViewById(R.id.search_store_byname);
		Intent intent=getIntent();
		if (intent.getIntExtra("storeFLAG",0)!=1){
			PrefUtils.setString(MainActivity.this, "loginValue", "");
			PrefUtils.setString(MainActivity.this, "orderAddress", "");
		}
		initData();
	}

    public void click(View view) {
		// TODO Auto-generated method stub
		int key = view.getId();
		switch (key) {
			case R.id.ll_cake:
				Intent intent = new Intent(MainActivity.this, StoreManagerActivity.class);
				intent.putExtra("FLAG",5);
				startActivity(intent);
				break;
			case R.id.ll_fruit:
				Intent intent1 = new Intent(MainActivity.this, StoreManagerActivity.class);
				intent1.putExtra("FLAG",3);
				startActivity(intent1);
				break;
			case R.id.ll_market:
				Intent intent2 = new Intent(MainActivity.this, StoreManagerActivity.class);
				intent2.putExtra("FLAG",2);
				startActivity(intent2);
				break;
			case R.id.ll_nice_food:
				Intent intent3 = new Intent(MainActivity.this, StoreManagerActivity.class);
				intent3.putExtra("FLAG",1);
				startActivity(intent3);
				break;
			case R.id.ll_suger:
				Intent intent4 = new Intent(MainActivity.this, StoreManagerActivity.class);
				intent4.putExtra("FLAG",4);
				startActivity(intent4);
				break;
			case R.id.tv_search_store_byaddress:
			case R.id.et_search_store_byaddress:
				Intent intent5 = new Intent(MainActivity.this,
						SearchStoreByNameActivity.class);
				startActivityForResult(intent5, 1);
//			case R.id.search_store_byname:
			default:
				break;
		}

	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void initData() {

		rgGroup.check(R.id.rb_takeout);
		mPagerList = new ArrayList<>();
		//商品主界面
			mPagerList.add(new TakeOutSystemPager(MainActivity.this));
		try{

			mPagerList.get(0).initData();
		}catch (Exception e){
			e.printStackTrace();
		}
		Log.d("tag","执行到外卖页面了");



		//订单管理模块
		OrderSystemPager orderSystemPager= new OrderSystemPager(MainActivity.this);//创建orderPager界面
		mPagerList.add(orderSystemPager);
		orderSystemPager.mRootView.findViewById(R.id.all_list).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(MainActivity.this, "全部订单", Toast.LENGTH_LONG).show();

			}
		});


		//用户信息模块
		mPagerList.add(new UserInfoSystemPager(MainActivity.this));
		mViewPager.setAdapter(new ContentAdapter());
		rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.rb_takeout:
						mViewPager.setCurrentItem(0, false);
						ll_title.setVisibility(View.VISIBLE);
						break;
					case R.id.rb_order:
						mViewPager.setCurrentItem(1, false);//
						ll_title.setVisibility(View.GONE);
						break;
					case R.id.rb_userInfo:
						mViewPager.setCurrentItem(2, false);
						ll_title.setVisibility(View.GONE);
						break;
					default:
						break;
				}
			}
		});

		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				try{

					mPagerList.get(arg0).initData();//
				}catch (Exception e){
					e.printStackTrace();
				}
				switch (arg0) {
					case TAKEOUT:
						rb_takeout.setChecked(true);
						ll_title.setVisibility(View.VISIBLE);
						break;
					case ORDER:
						rb_order.setChecked(true);
						ll_title.setVisibility(View.GONE);
						break;
					case USERINFO:
						rb_userInfo.setChecked(true);
						ll_title.setVisibility(View.GONE);
						break;
					default:
						break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		Log.d("tag", "aaaaaaaaa" + mPagerList.get(0));

		Log.d("tag", "bbbbbbb" + mPagerList.get(0));
	}

	class ContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mPagerList.get(position);
			container.addView(pager.mRootView);
			return pager.mRootView;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub

		Log.d("tag","000000000000000000000");
		// -----------location config ------------
		locationService = ((MyApplication) getApplication()).locationService;
		//获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
		locationService.registerListener(mListener);
		//注册监听
//		int type = getIntent().getIntExtra("from", 0);
//		Log.d("tag", "000000000000000000000" + type);
//		if (type == 0) {
			locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//		} else if (type == 1) {
//			locationService.setLocationOption(locationService.getOption());
//		}
//		etSearchStore.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (etSearchStore.getText().toString().equals("开始定位")) {
					locationService.start();// 定位SDK
//					 start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
//					etSearchStore.setText("停止定位");
//				} else {
//					locationService.stop();
//					etSearchStore.setText("开始定位");
//				}
//			}
//		});
		super.onStart();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		locationService.unregisterListener(mListener); //注销掉监听
		locationService.stop(); //停止定位服务
		super.onStop();
	}
	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
				StringBuffer sb = new StringBuffer(256);
//				sb.append("time : ");
				/**
				 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
				 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
				 */
//				sb.append(location.getTime());
//				sb.append("\nerror code : ");
//				sb.append(location.getLocType());
//				sb.append("\nlatitude : ");
//				sb.append(location.getLatitude());
//				sb.append("\nlontitude : ");
//				sb.append(location.getLongitude());
				PrefUtils.setString(MainActivity.this, "latitude", location.getLatitude() + "");
				PrefUtils.setString(MainActivity.this,"longitude",location.getLongitude()+"");
				Log.d("tag","latitude"+"_______________"+"longitude______________");
//				sb.append("\nradius : ");
//				sb.append(location.getRadius());
//				sb.append("\nCountryCode : ");
//				sb.append(location.getCountryCode());
//				sb.append("\nCountry : ");
//				sb.append(location.getCountry());
//				sb.append("\ncitycode : ");
//				sb.append(location.getCityCode());
//				sb.append("\ncity : ");
				Log.d("tag", location + "1111111");
				if (location.getCity()!=null&&location.getDistrict()!=null&&location.getStreet()!=null){

					sb.append(location.getCity());
					LOCATION_CITY=location.getCity();
					LOCATION_DISTRICT=location.getDistrict();
					LOCATION_STREET=location.getStreet();
					sb.append(location.getDistrict());
					sb.append(location.getStreet());
				}
//				sb.append("\naddr : ");
//				sb.append(location.getAddrStr());
//				sb.append("\nDescribe: ");
//				sb.append(location.getLocationDescribe());
//				sb.append("\nDirection(not all devices have value): ");
//				sb.append(location.getDirection());
//				sb.append("\nPoi: ");
				if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
//					for (int i = 0; i < location.getPoiList().size(); i++) {
						Poi poi = (Poi) location.getPoiList().get(0);
						LOCATION_POIT=poi.getName();
						sb.append(poi.getName() );
//					}
				}
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// 单位：km/h
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// 单位：米
					sb.append("\ndescribe : ");
					sb.append("gps定位成功");
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
					// 运营商信息
//					sb.append("\noperationers : ");
//					sb.append(location.getOperators());
//					sb.append("\ndescribe : ");
//					sb.append("网络定位成功");
				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//					sb.append("\ndescribe : ");
					sb.append("无网络,请联网");
				} /*else if (location.getLocType() == BDLocation.TypeServerError) {
					sb.append("\ndescribe : ");
					sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\ndescribe : ");
					sb.append("网络不同导致定位失败，请检查网络是否通畅");
				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\ndescribe : ");
					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
				}*/
				logMsg(sb.toString());

			}
		}

	};
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("tag", data + "kkkkkkkkkkkkkkk");
		if (data!=null){
			String address=data.getStringExtra("address");
			etSearchStore.setText(address);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	protected void onDestroy() {
		if (locationService!=null){
			locationService.unregisterListener(mListener); //注销掉监听
			locationService.stop();
			locationService=null;
		}
		super.onDestroy();
	}
	public void logMsg(String str) {
		try {
			if (etSearchStore!= null)
				etSearchStore.setText(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//
}
