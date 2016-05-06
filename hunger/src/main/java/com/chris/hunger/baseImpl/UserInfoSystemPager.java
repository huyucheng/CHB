package com.chris.hunger.baseImpl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chris.hunger.MyFavouritesActivity;
import com.chris.hunger.R;
import com.chris.hunger.SettingPageActivity;
import com.chris.hunger.ShipAddressActivity;
import com.chris.hunger.UserInfoActivity;
import com.chris.hunger.XLoginMainActivity;
import com.chris.hunger.base.BasePager;
import com.chris.hunger.utils.PrefUtils;

import java.io.File;

import cn.smssdk.SMSSDK;
import cn.smssdk.gui.ContactsPage;

/**
 * 鏅烘収鏈嶅姟
 * 
 * @author Kevin
 * 
 */
public class UserInfoSystemPager extends BasePager {
	//-----------短信验证码登录-----------
    String APPKEY = "10082851d5db8";
    String APPSECRET = "d8e91f215262be4138811f90c11b6366";

//	String APPKEY = "1018c0a536a22";
//	String APPSECRET = "25ef3a8e92368a5c16f78535c5b45012";
	private int mNetType;
	private View ll_login_register;
	private View ll_address;
	private View ivSetting;
	private LinearLayout myFavorite;
	private View tv_Invite_Friends;
	private String userstate;
	private String userid;
	private String name;
	private TextView tv_name;
	private TextView tv_Tips;
	private ImageView touxiang;
	private String login;

	public UserInfoSystemPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
			//-----------短信验证码登录-----------
			//初始化
			SMSSDK.initSDK(mActivity.getApplicationContext(), APPKEY, APPSECRET);

		login = PrefUtils.getString(mActivity, "loginValue", "");
		Log.d("tag","login________________________"+login);
		Log.d("###",PrefUtils.getString(mActivity.getApplicationContext(),"loginValue",""));
		if ("success".equals(login)){
			String name=PrefUtils.getString(mActivity,"name","");
			tv_name.setText(name);
			File file = new File(Environment.getExternalStorageDirectory()+"/a.png");
			if(file.exists()){
				Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/a.png");
				touxiang.setImageBitmap(bm);
			}
			tv_Tips.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public View initViews() {

//		userid=PrefUtils.getString(mActivity,"","");
//		if("".equals(userid))
//		{
//			View view=View.inflate(mActivity,R.layout., null);
//			return view;
//		}else {

		mNetType = getNetType(mActivity);
			View view = View.inflate(mActivity, R.layout.userinfo_detail_pager, null);
			ll_login_register = view.findViewById(R.id.ll_login_register);
			myFavorite = (LinearLayout) view.findViewById(R.id.my_favorite);
			ivSetting = view.findViewById(R.id.iv_setting);
			ll_address = view.findViewById(R.id.ll_address);
		   tv_name =(TextView)view.findViewById(R.id.tv_name);
		   touxiang =(ImageView)view.findViewById(R.id.index_my_list1_touxiang);
			tv_Invite_Friends = view.findViewById(R.id.tv_Invite_Friends);
		tv_Tips =(TextView)view.findViewById(R.id.tv_tips);
			ll_address.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if ("success".equals(PrefUtils.getString(mActivity, "loginValue", ""))) {
						Intent intent = new Intent(mActivity, ShipAddressActivity.class);
						mActivity.startActivity(intent);

					} else {
						Intent intent = new Intent(mActivity, XLoginMainActivity.class);
						mActivity.startActivity(intent);

					}

//				}
				}
			});
			ll_login_register.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if ("success".equals(PrefUtils.getString(mActivity, "loginValue", ""))) {
						Intent intent = new Intent(mActivity, UserInfoActivity.class);
						mActivity.startActivity(intent);

					} else {
						Intent intent = new Intent(mActivity, XLoginMainActivity.class);
						mActivity.startActivity(intent);

					}
				}
			});
			ivSetting.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mActivity, SettingPageActivity.class);
					intent.putExtra("loginValue", PrefUtils.getString(mActivity, "loginValue", ""));
					mActivity.startActivity(intent);
				}
			});
			myFavorite.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					if ("success".equals(PrefUtils.getString(mActivity, "loginValue", ""))) {
						Intent intent = new Intent(mActivity, MyFavouritesActivity.class);
						mActivity.startActivity(intent);

					} else {
						Intent intent = new Intent(mActivity, XLoginMainActivity.class);
						mActivity.startActivity(intent);

					}
				}
			});
			tv_Invite_Friends.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if ("success".equals(PrefUtils.getString(mActivity, "loginValue", ""))) {
						showDialog();

					} else {
						Intent intent = new Intent(mActivity, XLoginMainActivity.class);
						mActivity.startActivity(intent);

					}

				}
			});

			return view;
		}
//	}
	//获取当前网络类型，无网为-1，流量为0，wifi为1
	public static int getNetType(Context context) {
		int netType = -1;
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			netType = 0;
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = 1;
		}
		return netType;
	}
	private void showDialog() {
		AlertDialog dialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setTitle("一起嗨").setIcon(android.R.drawable.stat_notify_error);
		builder.setMessage("邀请好基友使用吃货宝吧，吃货宝，你值得拥有！");
		builder.setNegativeButton("果断邀请", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//打开通信录好友列表页面
				ContactsPage contactsPage = new ContactsPage();
				contactsPage.show(mActivity);

			}
		});
		builder.setPositiveButton("残忍拒绝", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dialog = builder.create();
		dialog.setCancelable(false);
		dialog.show();
	}

}
