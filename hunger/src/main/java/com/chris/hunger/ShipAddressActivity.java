package com.chris.hunger;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.Bean.AddressBean;
import com.chris.hunger.adapter.AddressAdapter;
import com.chris.hunger.entity.Address;
import com.chris.hunger.utils.GetURLUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipAddressActivity extends Activity implements GetURLUtil {
    public static final String baseURL = GetURLUtil.ADDRESS_BaseURL + "addAddress.json";//雨哥服务器返回的json数据路径

    String path = "/sdcard/data/address.txt";//下载信息到指定路径下、没实现

    private String userid="4";

    private String nameAddress;
    private String sexAddress;
    private String phoneAddress;
    private String gpsAddress;
    private String detailAddress;

    private AddressBean addressBean;
    private ImageView ivAddressForward;
    private ListView lvAddAddressSuccess;
    private ImageView ivNoAddress;
    private TextView tvNoAddress;
    private TextView tvClickInfo;
    private TextView tvAddAddress;
    private RelativeLayout rlAddressTips;
    List<Address> list = new ArrayList<Address>();
    private AddressAdapter adapter;//显示地址item的适配器
    private View loadAgain;
    private int netType;
    private Handler handler;


    //初始化界面
    private void assignViews() {
        ivAddressForward = (ImageView) findViewById(R.id.iv_address_forward);
        lvAddAddressSuccess = (ListView) findViewById(R.id.lv_addAddressSuccess);//显示地址的listview
        ivNoAddress = (ImageView) findViewById(R.id.iv_no_address);
        tvNoAddress = (TextView) findViewById(R.id.tv_no_address);
        tvClickInfo = (TextView) findViewById(R.id.tv_click_info);
        tvAddAddress = (TextView) findViewById(R.id.tv_add_address);
        rlAddressTips =(RelativeLayout)findViewById(R.id.rl_address_tips);
        //显示数据   显示数据
        //初始化list数据
        getDataFromServer("4");//用户的id

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String responseInfo = (String) msg.obj;
                if(responseInfo.equals("1")){
                    lvAddAddressSuccess.setVisibility(View.VISIBLE);//有地址的界面显示
                    rlAddressTips.setVisibility(View.GONE); //没添加成功后显示界面
                    Intent intent=getIntent();
                    adapter =new AddressAdapter(ShipAddressActivity.this,list,intent);
                    lvAddAddressSuccess.setAdapter(adapter);
                }else{
                    lvAddAddressSuccess.setVisibility(View.GONE);//有地址的界面显示
                    rlAddressTips.setVisibility(View.VISIBLE);
                    Toast.makeText(ShipAddressActivity.this, "暂时无地址", Toast.LENGTH_LONG).show();
                }
            }
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        netType = UserInfoSystemPager.getNetType(ShipAddressActivity.this);
//        if (netType == -1) {
//            setContentView(R.layout.activity_no_net_address);
//            loadAgain = findViewById(R.id.btn_load_again);
//            loadAgain.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   int netType = UserInfoSystemPager.getNetType(ShipAddressActivity.this);
//                    if (netType!=-1){
//                        initHaveNet();
//                    }else {
//                        Toast.makeText(ShipAddressActivity.this, "网络异常，请重新设置", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            });
//        } else {
            initHaveNet();//有网显示界面
//        }
    }




    //初始化，有网的情况下
    private  void initHaveNet(){
        setContentView(R.layout.activity_ship_address);
        assignViews();//出初始化界面
        ivAddressForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //增加地址响应事件
        tvAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShipAddressActivity.this, AddAddressActivity.class);
                startActivityForResult(intent, 1);
                Log.d("tag", "aaaaaa");
            }
        });
    }


    // 添加收货地址信息， 加入列表
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==1){
            lvAddAddressSuccess.setVisibility(View.VISIBLE);//有地址的界面显示
            rlAddressTips.setVisibility(View.GONE); //没添加成功后显示界面
            Address address = new Address();
            address.setSex(data.getStringExtra("sex"));
            address.setPhone(data.getStringExtra("phone"));
            address.setAddress(data.getStringExtra("address"));
            address.setName(data.getStringExtra("name"));
            list.add(address);
            Log.d("tag", address.getName() + "_________" + address.getAddress());
            Intent intent=getIntent();
            adapter =new AddressAdapter(this,list,intent);
            lvAddAddressSuccess.setAdapter(adapter);

            }
//        else {
//            Toast.makeText(ShipAddressActivity.this, "添加收货地址失败", Toast.LENGTH_LONG).show();
//            }

        super.onActivityResult(requestCode, resultCode, data);

    }



    private void getDataFromServer(String oid) {
        HttpUtils utils = new HttpUtils();
        final String URL=ADDRESS_BaseURL+"queryAddress.do?userid="+oid;
        // 使用xutils发送请求
        HttpHandler<String> send = utils.send(HttpRequest.HttpMethod.GET, URL,
                new RequestCallBack<String>() {
                    // 访问成功, 在主线程运行
                    @Override
                    public void onSuccess(ResponseInfo responseInfo) {
                        String result1 = (String) responseInfo.result;
                        Log.d("tag","返回结果为"+result1);
                        try {
                            parseData(result1);
                            Message msg = Message.obtain();
                            try {
                                JSONObject json = new JSONObject(result1);
                                msg.obj = json.getString("success");
                                Log.d("tag","得到的信息为"+msg.obj);
                                handler.sendMessage(msg);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // 访问失败, 在主线程运行
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(ShipAddressActivity.this, "当前网络不可用,请检查网络！", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        );
    }



    protected void parseData(String result) {
        try{
            JSONObject json = new JSONObject(result);
            JSONArray json1 = json.getJSONArray("addressList");
 //           String total = json.getString("success");
            //每一个订单赋值
            for (int i = 0; i < json1.length(); i++) {
                Address newaddress = new Address();

                JSONObject jsonObj  = json1.optJSONObject(i);
                String address=jsonObj.getString("address");
                newaddress.setAddress(address);

                String addressid=jsonObj.getString("id");
                newaddress.setId(Integer.parseInt(addressid));

                String username=jsonObj.getString("name");
                newaddress.setName(username);

                String userphone=jsonObj.getString("phone");
                newaddress.setPhone(userphone);

                String userid=jsonObj.getString("userid");
                newaddress.setUserid(Integer.parseInt(userid));

                list.add(newaddress);

            }//LIST结束
        }catch (Exception e){
            e.printStackTrace();
        }
    }





    private List<Map<String, String>> getData() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", nameAddress);
        map.put("sex", sexAddress);
        map.put("phone", phoneAddress);
        map.put("gpsAddress", gpsAddress);
        map.put("address", detailAddress);
        list.add(map);
        return list;
    }

    public class MyDatePickerDialog extends DatePickerDialog {

        public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
            super(context, callBack, year, monthOfYear, dayOfMonth);
        }

        @Override
        protected void onStop() {
        }
    }
}