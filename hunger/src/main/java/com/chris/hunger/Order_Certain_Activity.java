package com.chris.hunger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.OrderQueryListener;
import com.bmob.pay.tool.PayListener;
import com.chris.hunger.Bean.Order;
import com.chris.hunger.Bean.Order_good;
import com.chris.hunger.adapter.OrderGoodListAdapter;
import com.chris.hunger.entity.Address;
import com.chris.hunger.entity.Shop;
import com.chris.hunger.pay.Constant;
import com.chris.hunger.pay.InstallPlugin;
import com.chris.hunger.pay.PayOrder;
import com.chris.hunger.utils.GetURLUtil;
import com.chris.hunger.utils.PrefUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import c.b.BP;

public class Order_Certain_Activity extends Activity implements GetURLUtil {

    public TextView tv_order_user_name;  //
    public TextView tv_order_user_phone;
    public ImageView iv_forward;
    public TextView tv_order_user_address;
    public RadioGroup rg_pay_method;
    public TextView tv_arrive_time_select;
    public ListView order_list;
    public TextView tv_total_price;
    public Button btn_submit_order;
    private ImageView iv_go_address;


    public PayOrder order;//支付实体类
    BmobPay bmobPay;//支付按钮
    public String neworderid;//新订单的id号



    String userid;
    String shopid;
    String totalprice;
    String memo;
    String orderdetail;
    String addressid;
    String totalNum;
    String orderid;

    public Order ord1;
    private Handler handler;
    private String responseInfo;
    private String goodsName;
    private String phone;
    private String name;
    private LinearLayout selectAddress;
    private List<Order_good> goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_certain);

        // 必须先初始化
        BP.init(Order_Certain_Activity.this, Constant.APPID);
        bmobPay = new BmobPay(this);
        order=new PayOrder();
        ord1=new Order();
        selectAddress =(LinearLayout)findViewById(R.id.ll_select_address);


        iv_go_address=(ImageView)findViewById(R.id.iv_go_address);
        tv_order_user_name=(TextView)findViewById(R.id.tv_order_user_name);
        tv_order_user_phone=(TextView)findViewById(R.id.tv_order_user_phone);
        tv_order_user_address=(TextView)findViewById(R.id.tv_order_user_address);

        iv_forward=(ImageView)findViewById(R.id.iv_forward);
        rg_pay_method=(RadioGroup)findViewById(R.id.rg_pay_method);
        tv_arrive_time_select=(TextView)findViewById(R.id.tv_arrive_time_select);
        order_list=(ListView)findViewById(R.id.order_list);
        tv_total_price=(TextView)findViewById(R.id.tv_total_price);
        btn_submit_order=(Button)findViewById(R.id.btn_submit_order);
        btn_submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rg_pay_method.getCheckedRadioButtonId()==R.id.rb_pay_online) {
                    showReminder();
                }else{
                    NopaycommitToServer();
                }
            }
        });
        iv_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order_Certain_Activity.this.finish();
            }
        });

        selectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Order_Certain_Activity.this, ShipAddressActivity.class);
                intent.putExtra("orderFLAG",1);
                startActivity(intent);
            }
        });
        Intent intent=getIntent();
        if (1==intent.getIntExtra("FLAG",0)){

            String address= PrefUtils.getString(Order_Certain_Activity.this,"orderAddress","");
            tv_order_user_address.setText(address);
        }else {

            if(intent.getStringExtra("userid")!=null) {
                goods = ord1.getGoods();
                userid = intent.getStringExtra("userid");
                shopid = intent.getStringExtra("6");
                totalprice=intent.getStringExtra("totalprice");
//            memo=intent.getStringExtra("memo");
                orderdetail=intent.getStringExtra("orderdetail");
                totalNum=intent.getStringExtra("totalNum");
                goodsName =intent.getStringExtra("goodsName");
                phone =intent.getStringExtra("phone");
                name =intent.getStringExtra("name");
                PrefUtils.setString(Order_Certain_Activity.this,"orderName",name);
                PrefUtils.setString(Order_Certain_Activity.this,"orderPhone",phone);
                Bundle bundle=intent.getExtras();
                Set<Order_good> orderList= (Set<Order_good>) bundle.getSerializable("orders");

                for(Order_good a:orderList){
                    goods.add(a);
                    Log.d("tag",a.getGoodname()+"名字________"+a.getGoodcount()+"数量________"+a.getGoodprice()+"价格_______");
                }
//            tv_order_user_address.setText(ord1.getAddress());
                tv_total_price.setText(totalprice+"");

//            Order_good good =new Order_good();
//            good.setGoodcount(Integer.parseInt(totalNum));
//            good.setGoodname(goodsName);
//            good.setGoodprice(Double.parseDouble(totalprice));
//            goods.add(good);
                OrderGoodListAdapter adapter=new OrderGoodListAdapter(Order_Certain_Activity.this, goods);
                order_list.setAdapter(adapter);

            }else{
                Bundle bd = new Bundle();
                bd = intent.getBundleExtra("bd");
                orderid = bd.getString("orderId");
                getDataFromServer(orderid);
            }
        }
        //初始化数据

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                responseInfo = (String) msg.obj;
                if(Integer.parseInt(responseInfo)!=0){
                    //     Log.i("!!comment","success");

                    Toast.makeText(Order_Certain_Activity.this,"下单成功",Toast.LENGTH_SHORT).show();
                    Order_Certain_Activity.this.finish();//修改

                }else{
                    Log.i("!!comment","fail");
                    Toast.makeText(Order_Certain_Activity.this,"下单失败",Toast.LENGTH_SHORT).show();
                    Order_Certain_Activity.this.finish();//修改
                }
            }
        };

    }

    @Override
    protected void onStart() {
//        PrefUtils.setString(context, "orderAddress", address.getAddress());
////                    PrefUtils.setString(context,"orderSex",address.getSex());
//        PrefUtils.setString(context,"orderPhone",address.getPhone());
//        PrefUtils.setString(context,"orderName",address.getName());
        String address= PrefUtils.getString(Order_Certain_Activity.this,"orderAddress","");
        tv_order_user_address.setText(address);
        tv_order_user_name.setText(name);
        tv_order_user_phone.setText(phone);
        tv_total_price.setText(totalprice+"");


        super.onStart();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null){
            Address address = new Address();
            address.setSex(data.getStringExtra("sex"));
            address.setPhone(data.getStringExtra("phone"));
            address.setAddress(data.getStringExtra("address"));
            address.setName(data.getStringExtra("name"));

            tv_order_user_name.setText(address.getName());
            tv_order_user_phone.setText(address.getPhone());
            tv_order_user_address.setText(address.getAddress());
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
  public void showReminder(){
        new AlertDialog.Builder(this)
                .setMessage(
                        "选择支付方式")
                .setPositiveButton("微信支付",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                order.setPrice(Double.parseDouble(totalprice));
                                order.setName(shopid);
                                order.setOrderId(orderid);
                                order.setBody(memo);
                                pay(false);
                            }
                        })
                .setNeutralButton("支付宝支付", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        order.setPrice(Double.parseDouble(totalprice));
                        order.setName(shopid);
                        order.setOrderId(orderid);
                        order.setBody(memo);
                        pay(true);
                    }
                }).create().show();
    }

    public void commitToServer() {

        final String url =  Order_BaseURL+"orderInsert.do";//"http://10.6.12.21:8080/chb/order/commentInsert.do";//?&shopid=1&goodsid=2&userid=22&orderid=1&content=lalala&commenttime=1990-10-20 12:00:00&rank=3
        final RequestParams params = new RequestParams();//要传的数据
//        JSONObject json = new JSONObject();
        try {
            params.addBodyParameter("userid", userid);
            params.addBodyParameter("shopid", ""+6);
            params.addBodyParameter("totalprice", totalprice);
            params.addBodyParameter("memo", memo);
            params.addBodyParameter("orderdetail", orderdetail);
            params.addBodyParameter("addressid", ""+3);
            params.addBodyParameter("totalNum", totalNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //http请求
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onFailure(HttpException arg0, String arg1) {
                Toast.makeText(Order_Certain_Activity.this, "fail", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                Message msg = Message.obtain();
                try {
                    JSONObject json = new JSONObject((String) arg0.result);
                    neworderid = json.getString("orderid");
                    msg.obj = json.getString("result");
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //货到付款
    public void NopaycommitToServer() {
        final String url =  Order_BaseURL+"orderInsert.do";//"http://10.6.12.21:8080/chb/order/commentInsert.do";//?&shopid=1&goodsid=2&userid=22&orderid=1&content=lalala&commenttime=1990-10-20 12:00:00&rank=3
        final RequestParams params = new RequestParams();//要传的数据
//        JSONObject json = new JSONObject();
        try {

            params.addBodyParameter("userid", userid);
            params.addBodyParameter("shopid", shopid);
            params.addBodyParameter("totalprice", totalprice);
            params.addBodyParameter("memo", memo);//等级  评论星级
            params.addBodyParameter("orderdetail", orderdetail);//订单详情
            params.addBodyParameter("addressid", addressid);
            params.addBodyParameter("totalNum", totalNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //http请求
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onFailure(HttpException arg0, String arg1) {
                Toast.makeText(Order_Certain_Activity.this, "fail", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                Message msg = Message.obtain();
                try {
                    JSONObject json = new JSONObject((String) arg0.result);
                    neworderid = json.getString("orderid");
                    judge_pay(neworderid);
                    msg.obj = json.getString("result");
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //订单支付
   public void pay(final boolean byAli) {
        Toast.makeText(this, "正在申请支付。。请稍候。。", Toast.LENGTH_SHORT).show();
        order.setPaid(false);
        order.save(Order_Certain_Activity.this);
        PayListener listener = new PayListener() {
            @Override
            public void unknow() {
                Toast.makeText(Order_Certain_Activity.this, "支付失败，很抱歉",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void succeed() {
                Toast.makeText(Order_Certain_Activity.this,
                        "支付操作完成!请等待服务器校验通过即可满足您的要求!", Toast.LENGTH_SHORT)
                        .show();
                checkAgain();//支付成功后反馈
            }

            @Override
            public void orderId(String arg0) {
                order.setOrderId(arg0);
                order.update(Order_Certain_Activity.this);
            }

            @Override
            public void fail(int arg0, String arg1) {
                if (!byAli && arg0 == -3) {
                    Toast.makeText(Order_Certain_Activity.this, "您尚未安裝微信支付插件", Toast.LENGTH_SHORT)
                            .show();
                    InstallPlugin.installBmobPayPlugin(Order_Certain_Activity.this,
                            InstallPlugin.ASSETS_PLUGIN);
                }

                Toast.makeText(Order_Certain_Activity.this, "下单成功，支付失败",
                        Toast.LENGTH_SHORT).show();
                judge_pay(neworderid);
            }
        };

       commitToServer();
        if (byAli) {
            bmobPay.pay(Double.parseDouble(order.getPrice().toString()),//支付宝
                    order.getName(), listener);
        } else {
            bmobPay.payByWX(Double.parseDouble(order.getPrice().toString()),//微信支付
                    order.getName(), listener);
        }

    }


    //支付成功后的反馈，根据返回的数据来判断是否支付成功
    void checkAgain() {
        bmobPay.query(order.getOrderId(), new OrderQueryListener() {
            @Override
            public void succeed(String arg0) {
                if (arg0.equals("NOTPAY")) {     //支付失败
                    Toast.makeText(Order_Certain_Activity.this, "检查一次，下单成功，支付失败",
                            Toast.LENGTH_SHORT).show();
                    judge_pay(neworderid);
                    Log.i("!!!!!!!!","hello"+neworderid);
                } else {
                    order.setPaid(true);
                    order.update(Order_Certain_Activity.this);
                    Toast.makeText(Order_Certain_Activity.this,
                            "感谢你购买" + order.getName() + "!", Toast.LENGTH_SHORT)
                            .show();
 //                   judge_pay(neworderid);
                }
            }

            @Override
            public void fail(int arg0, String arg1) {
                Toast.makeText(Order_Certain_Activity.this, "查询失败，很抱歉 ",
                        Toast.LENGTH_SHORT).show();
            }
        });}


    //返回支付结果
    private void judge_pay(final String oid) {
        HttpUtils utils = new HttpUtils();
        final String URL=Order_BaseURL+"orderUpdate.do?orderid="+oid+"&flag=0";
        // 使用xutils发送请求
        HttpHandler<String> send = utils.send(HttpRequest.HttpMethod.GET, URL,
                new RequestCallBack<String>() {
                    // 访问成功, 在主线程运行
                    @Override
                    public void onSuccess(ResponseInfo responseInfo) {
                        Message msg = Message.obtain();
                        String result1 = (String) responseInfo.result;
                        System.out.println("!!!!!返回结果:" + result1+ URL);
                        try {
                            JSONObject jsonObject = new JSONObject(result1);
                            msg.obj = jsonObject.getString("result");
                            handler.sendMessage(msg);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // 访问失败, 在主线程运行
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(Order_Certain_Activity.this, "当前网络不可用,请检查网络！", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        );
    }



    //获取订单数据
    private void getDataFromServer(String orderid) {
        HttpUtils utils = new HttpUtils();
        final String URL=Order_BaseURL +"orderDetail.do?orderid="+orderid;
        // 使用xutils发送请求
        HttpHandler<String> send = utils.send(HttpRequest.HttpMethod.GET, URL,
                new RequestCallBack<String>() {

                    // 访问成功, 在主线程运行
                    @Override
                    public void onSuccess(ResponseInfo responseInfo) {
                        String result = (String) responseInfo.result;
                        System.out.println("!!!!!返回结果:" + result);
                        Log.i("########", "loginsuccess    ");
                        try {
                            parseData(result);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    // 访问失败, 在主线程运行
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(Order_Certain_Activity.this, "当前网络不可用,请检查网络！", Toast.LENGTH_SHORT).show();
                        Log.i("########", "loginfail  " + Log.getStackTraceString(error) + "!!  ###  " + msg);
                        error.printStackTrace();
                    }
                }
        );
    }


    protected void parseData(String result) throws JSONException {
        JSONObject json = new JSONObject(result);

        JSONArray json1 = json.getJSONArray("rows");

        List<Order_good> ordergoods = new ArrayList<Order_good>();
        JSONObject jsonObj = json1.optJSONObject(0);

        int total=0;

        //订单商品赋值
        JSONArray orderdelist = jsonObj.getJSONArray("orderdelist");
        for (int j = 0; j < orderdelist.length(); j++) {//获取订单的商品
            JSONObject jordergood = orderdelist.optJSONObject(j);
            Order_good order_good = new Order_good();
            String a = jordergood.getString("productnum");
            String b = jordergood.getString("productprice");
            String c = jordergood.getString("productname");
            order_good.setGoodname(a);
            order_good.setGoodcount(Integer.parseInt(b));
            order_good.setGoodprice(Double.parseDouble(c));
            ordergoods.add(order_good);
            total+=Integer.parseInt(b);
        }
        ord1.setGoods(ordergoods);

        ord1.setTotalnum(total);
        //orderdetail信息
        String orderdetail1=jsonObj.getString("orderdetail");
        ord1.setOrderdetail(orderdetail1);

        //用户id
        String userid1=jsonObj.getString("userid");
        ord1.setUserid(Integer.parseInt(userid1));

        //订单memo
        String memo1=jsonObj.getString("memo");
        ord1.setMemo(memo1);

        //商店信息
        JSONObject shopdetail = jsonObj.getJSONObject("shop");
        String shopid1 = shopdetail.getString("id");
        String shopurl1 = shopdetail.getString("shopphoto");
        String shopname1 = shopdetail.getString("username");
        String shopphone1=shopdetail.getString("phone");//shop电话
        Shop shop = new Shop();
        shop.setId(Integer.parseInt(shopid1));
        shop.setShopphoto(shopurl1);
        shop.setName(shopname1);
        shop.setPhone(shopphone1);
        ord1.setShop(shop);

        //用戶信息id
        JSONObject user = jsonObj.getJSONObject("user");
        String username = user.getString("username");
        String userphone=user.getString("phone");
        ord1.setUsername(username);
        ord1.setUserphone(userphone);

        //订单地址
        JSONObject addr = jsonObj.getJSONObject("address");
        String add1=addr.getString("address");
        ord1.setAddress(add1);
        //订单id
        String addrid1=addr.getString("id");
        ord1.setAddressid(Integer.parseInt(addrid1));

        //总价
        String totalprice1 = jsonObj.getString("totalprice");
        ord1.setTotalprice(Double.parseDouble(totalprice1));


        tv_order_user_name.setText(ord1.getUsername());
        tv_order_user_phone.setText(ord1.getUserphone());
        tv_order_user_address.setText(ord1.getAddress());

        OrderGoodListAdapter adapter=new OrderGoodListAdapter(Order_Certain_Activity.this,ord1.getGoods());
        order_list.setAdapter(adapter);
        tv_total_price.setText(ord1.getTotalprice().toString());


        userid=ord1.getUserid().toString();//用户ID
        shopid=ord1.getShop().getId().toString();//商店的ID .
        totalprice=ord1.getTotalprice().toString();//商品总价 .
        memo=ord1.getMemo();//备注信息 .
        orderdetail=ord1.getOrderdetail();//订单详情 .
        addressid=ord1.getAddressid().toString();//地址id .
        totalNum=ord1.getTotalnum().toString();//商品总数
    }//order 结束




}
