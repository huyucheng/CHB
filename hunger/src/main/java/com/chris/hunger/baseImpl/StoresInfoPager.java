package com.chris.hunger.baseImpl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.R;
import com.chris.hunger.XLoginMainActivity;
import com.chris.hunger.base.BasePager;
import com.chris.hunger.utils.GetURLUtil;
import com.chris.hunger.utils.PrefUtils;
import com.lidroid.xutils.BitmapUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.ParseException;

/**
 * Created by huyucheng on 2016/3/8.
 */
public class StoresInfoPager extends BasePager{

    private ImageView image1;
    private ImageView image2;
    private TextView address;
    private TextView tvPhone;
    private TextView tvLicense;
    private TextView openTime;

    public StoresInfoPager(Activity activity) {
        super(activity);
    }
    private ScrollView scrollView1;
    private TextView tvMerchant;
    private ImageView ivComment;
    private ImageButton ibCollect;
    private TextView textView3;
    private TextView tvStartPrice;
    private TextView tvPeisongPrice;
    private TextView tvArriveTime;
    @Override
    public View initViews() {
        View view=View.inflate(mActivity, R.layout.store_info_pager,null);
        scrollView1 = (ScrollView) view.findViewById(R.id.scrollView1);
        tvMerchant = (TextView) view.findViewById(R.id.tv_merchant);
        ivComment = (ImageView) view.findViewById(R.id.iv_comment);
        ibCollect = (ImageButton) view.findViewById(R.id.ib_collect);
//        textView3 = (TextView) view.findViewById(R.id.textView3);
        tvStartPrice = (TextView) view.findViewById(R.id.tv_start_price);
        tvPeisongPrice = (TextView) view.findViewById(R.id.tv_peisong_price);
        tvArriveTime = (TextView) view.findViewById(R.id.tv_arrive_time);
        image1 = (ImageView) view.findViewById(R.id.iv_shop_image1);
        image2 = (ImageView) view.findViewById(R.id.iv_shop_image2);
        address =(TextView)view.findViewById(R.id.tv_address);
        tvPhone =(TextView)view.findViewById(R.id.tv_phone);
        tvLicense =(TextView)view.findViewById(R.id.tv_license);
        openTime =(TextView)view.findViewById(R.id.tv_open_time);
        String shopId=PrefUtils.getString(mActivity, "storeId", "1");
       PrefUtils.setBoolean(mActivity,"shopIdCollection",false);
        ibCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flag = PrefUtils.getString(mActivity, "loginValue", "");
                if ("success".equals(flag)) {
                    Boolean collection=PrefUtils.getBoolean(mActivity, "shopIdCollection", false);
                    collection=!collection;
                    getDataFromServer();
                    if (collection){
                        ibCollect.setBackgroundResource(R.drawable.my_collection_yes);
                        PrefUtils.setBoolean(mActivity,"shopIdCollection",true);

                    }else{
                        ibCollect.setBackgroundResource(R.drawable.my_collection_no);
                        PrefUtils.setBoolean(mActivity, "shopIdCollection", false);
                    }

                } else {
                    showWarningDialog();
                }

            }
        });
        return view;
    }
    public void initData() throws ParseException {
//        String cache= CacheUtils.getCache("http://10.6.12.72:8080/chb/shopCategory/shop.do?id=1", mActivity);
//        if (!TextUtils.isEmpty(cache)){
//            parseData(cache);
//            Log.d("tag", "ccccc" + cache);
//        }
//        getDataFromServer();
//        PrefUtils.setString(context, "storeName", shopName );
//        PrefUtils.setString(context,"storePhone",(String)stores.get(position).getPhone());
//        PrefUtils.setString(context,"storeLicense",(String)stores.get(position).getLicense());
//        PrefUtils.setString(context,"storeShopphoto",(String)stores.get(position).getShopphoto());
//        PrefUtils.setString(context,"storeTime",(String)stores.get(position).getRegistertime());
//        PrefUtils.setString(context,"storeAddress",(String)stores.get(position).getAddress());
//        PrefUtils.setString(context, "storeId", stores.get(position).getId() + "");
        tvMerchant.setText(PrefUtils.getString(mActivity.getApplicationContext(), "storeName", ""));
        BitmapUtils utils=new BitmapUtils(mActivity);
        utils.display(image1, PrefUtils.getString(mActivity.getApplicationContext(),"storeShopphoto","暂无图片"));
        utils.display(image2, PrefUtils.getString(mActivity.getApplicationContext(), "storeShopphoto", "暂无图片"));
        address.setText("商家地址:"+PrefUtils.getString(mActivity.getApplicationContext(), "storeAddress", ""));
        tvPhone.setText("商家电话:"+PrefUtils.getString(mActivity.getApplicationContext(),"storePhone",""));
        utils.display(tvLicense, PrefUtils.getString(mActivity.getApplicationContext(), "storeLicense", "暂无图片"));

//        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//这边改变格式
        String time = PrefUtils.getString(mActivity.getApplicationContext(), "storeTime", "");
//        Date date=s.parse(time);
//        Log.d("tag",time+"________________");
//        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//这边改变格式
//        Date d =  new Date(time+"000");
//        String ordertime1 = s.format(d);//可以看改变格式的效果
        openTime.setText(time);

    }
    private void getDataFromServer() {
        String userid=PrefUtils.getString(mActivity,"userId","");
        String shopid=PrefUtils.getString(mActivity, "storeId", "");

        Log.d("tag","登录用户Id"+userid+"用户Id"+shopid);
        RequestParams params = new RequestParams(GetURLUtil.LOVE_ADD_BASEURL+"userid="+userid+"&shopid="+shopid);

//        params.addQueryStringParameter("shopId", );
//        params.addQueryStringParameter("userId",);
        Log.d("tag", "进入xtuil了");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("tag", "成功了" + result);
//                Toast.makeText(mActivity,"收藏成功",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mActivity, "输入信息有误，请重新填写", Toast.LENGTH_SHORT).show();
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
    private void showWarningDialog() {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("提示").setIcon(android.R.drawable.stat_notify_error);
        builder.setMessage("您还未登录，还不能收藏");
        builder.setPositiveButton("登录用户", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(mActivity, XLoginMainActivity.class);
                mActivity.startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        dialog = builder.create();
        dialog.setCancelable(false);//3/22
        dialog.show();
    }
}
