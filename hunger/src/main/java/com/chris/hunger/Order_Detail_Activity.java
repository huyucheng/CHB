package com.chris.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.Bean.Order;
import com.chris.hunger.Bean.Order_good;
import com.chris.hunger.adapter.OrderGoodListAdapter;
import com.chris.hunger.entity.Shop;
import com.chris.hunger.utils.GetURLUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order_Detail_Activity extends Activity implements GetURLUtil {

    private Order ord1;
    private TextView order_state;
    private TextView order_create_time;
    private ImageView shop_photo;
    private TextView shop_name;
    private TextView shop_telephone;
    private ListView order_list;
    private TextView sender_name;
    private TextView sender_telephone;
    private TextView receiver_name;
    private TextView receiver_telephone;
    private TextView receiver_addr;
    private TextView order_id;//订单id
    private TextView total_price;
    private ImageView iv_forward;
//    private TextView pay_method;//支付方式
    //时间信息
    private TextView shop_order_time;
    private TextView send_time;
//    private TextView send_over_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_search);
        ord1 = new Order();
        order_state=(TextView)this.findViewById(R.id.order_state);
        order_create_time=(TextView)this.findViewById(R.id.order_create_time);
        shop_photo=(ImageView)this.findViewById(R.id.shop_photo);
        shop_name=(TextView)this.findViewById(R.id.shop_name);
        shop_telephone=(TextView)this.findViewById(R.id.shop_telephone);
        order_list=(ListView)this.findViewById(R.id.order_list);
        sender_name=(TextView)this.findViewById(R.id.sender_name);
        sender_telephone=(TextView)this.findViewById(R.id.sender_telephone);
        receiver_name=(TextView)this.findViewById(R.id.receiver_name);
        receiver_telephone=(TextView)this.findViewById(R.id.receiver_telephone);
        receiver_addr=(TextView)this.findViewById(R.id.receiver_addr);
        order_id=(TextView)this.findViewById(R.id.order_id);
        shop_order_time=(TextView)this.findViewById(R.id.shop_order_time);
        send_time=(TextView)this.findViewById(R.id.send_time);
        total_price=(TextView)this.findViewById(R.id.total_price1);
        iv_forward=(ImageView)this.findViewById(R.id.iv_forward);
        iv_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order_Detail_Activity.this.finish();
            }
        });

        Intent intent=getIntent();
        Bundle bd=new Bundle();
        bd=intent.getBundleExtra("bd");
        String orderid=bd.getString("id");
        getDataFromServer( orderid);

    }

    private void getDataFromServer(String orderid) {
        HttpUtils utils = new HttpUtils();
        final String URL=Order_BaseURL +"orderDetail.do?orderid="+orderid;
        // 使用xutils发送请求
        Log.i("!!!!!",orderid);
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
                        Toast.makeText(Order_Detail_Activity.this, "当前网络不可用,请检查网络！", Toast.LENGTH_SHORT).show();
                        Log.i("########", "loginfail  " + Log.getStackTraceString(error) + "!!  ###  " + msg);
                        error.printStackTrace();
                    }
                }
        );
    }


    protected void parseData(String result) throws JSONException {
        JSONObject json = new JSONObject(result);

         JSONArray  json1 = json.getJSONArray("rows");

            List<Order_good> ordergoods = new ArrayList<Order_good>();
            JSONObject jsonObj = json1.optJSONObject(0);

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
            }
            ord1.setGoods(ordergoods);
//			ordergoods.clear();

            //商店信息
            JSONObject shopdetail = jsonObj.getJSONObject("shop");
            String shopid = shopdetail.getString("id");
            String shopurl = shopdetail.getString("shopphoto");
            String shopname = shopdetail.getString("username");
            String shopphone=shopdetail.getString("phone");//shop电话
            Shop shop = new Shop();
            shop.setId(Integer.parseInt(shopid));
            shop.setShopphoto(shopurl);
            shop.setName(shopname);
            shop.setPhone(shopphone);
            ord1.setShop(shop);

            //订单id
            String orderid = jsonObj.getString("id");
            ord1.setId(Integer.parseInt(orderid));

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


            //订单时间
            String ordertime = jsonObj.getString("ordertime");
            Date d = new Date(Long.parseLong(ordertime));
            ord1.setOrdertime(d);
            String receivetime = jsonObj.getString("receivetime");//receive时间
            Date d1 = new Date(Long.parseLong(receivetime));
            ord1.setReceivetime(d1);

            //订单状态
            String orderstatus = jsonObj.getString("orderstatus");
            ord1.setOrderstatus(Integer.parseInt(orderstatus));

            //总价
            String totalprice = jsonObj.getString("totalprice");
        ord1.setTotalprice(Double.parseDouble(totalprice));

            //送货员信息
            JSONObject o1=jsonObj.getJSONObject("sendperson");
            String receiver_name1=o1.getString("name");
            String receiver_tel=o1.getString("phone");
        ord1.setSendpersonname(receiver_name1);
        ord1.setSendpersonphone(receiver_tel);

        if(ord1.getOrderstatus()!=4) {
            order_state.setText("正在处理");
        }else{
            order_state.setText("已成功");
        }

        BitmapUtils bitmap=new BitmapUtils(Order_Detail_Activity.this);
//        bitmap.display(shop_photo, SHOP_LOCAL_PHOTO+ord1.getShop().getShopphoto());
        bitmap.display(shop_photo, "http://pic.to8to.com/attch/day_160218/20160218_d968438a2434b62ba59dH7q5KEzTS6OH.png");
        shop_name.setText(ord1.getShop().getName());
        shop_telephone.setText(ord1.getShop().getPhone());

        OrderGoodListAdapter goodadpter=new OrderGoodListAdapter(Order_Detail_Activity.this,ord1.getGoods());
        order_list.setAdapter(goodadpter);

        order_id.setText(ord1.getId().toString());//订单id 忘记加tostring 导致报错
        total_price.setText(ord1.getTotalprice().toString());
        if(!ord1.getSendpersonname().equals("null")) {
            sender_name.setText(ord1.getSendpersonname());
            sender_telephone.setText(ord1.getSendpersonphone());
        }
        receiver_name.setText(ord1.getUsername());
        receiver_telephone.setText(ord1.getUserphone());
        receiver_addr.setText(ord1.getAddress());

        //时间信息
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//这边改变格式
        Date date1 = ord1.getOrdertime();
        String ordertime1 = s.format(date1);//可以看改变格式的效果
        shop_order_time.setText(ordertime1);
        order_create_time.setText(ordertime1);

        Date date2 = ord1.getReceivetime();
        String ordertime2 = s.format(date2);//可以看改变格式的效果
        send_time.setText(ordertime2);

        }//order 结束



}
