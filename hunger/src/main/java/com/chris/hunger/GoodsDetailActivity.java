package com.chris.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chris.hunger.adapter.CommentAdapter;
import com.chris.hunger.view.XListView;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by huyucheng on 2016/3/10.
 */
public class GoodsDetailActivity extends Activity implements XListView.IXListViewListener{

//    private ArrayList<GoodsBean.GoodsEntity> foods;
    private XListView lv_conment_info;
    private CommentAdapter adapter;
    private Handler mHandler;
    private TextView back;
    private TextView totalPrice;
    private Button submit;
    private int total;
    private ImageButton ibSelect;
    private TextView price;
    private TextView tv_price;
    private LinearLayout backGroudPhoto;
    private TextView peisong;
    private ImageView ibSelectadd;
    private ImageView ibSelectdelete;
    private int count;
    private String goodsname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_detail_activity);
//        comments = new ArrayList<>();
//        lv_conment_info = (XListView) findViewById(R.id.lv_conment_info);
           back =(TextView)findViewById(R.id.tv_back_menu);
        initData();
//        lv_conment_info.setPullLoadEnable(true);
//        lv_conment_info.setPullRefreshEnable(true);
//        lv_conment_info.setXListViewListener(this);
//        adapter = new CommentAdapter(GoodsDetailActivity.this,comments,R.layout.comment_info_item);
//        lv_conment_info.setAdapter(adapter);
//        ibSelectadd =(ImageView)findViewById(R.id.ib_select_add);
//        ibSelectdelete = (ImageView)findViewById(R.id.ib_select_delete);
        totalPrice =(TextView)findViewById(R.id.tv_total_price);
//        submit =(Button)findViewById(R.id.btn_submit_order);
        tv_price =(TextView)findViewById(R.id.tv_merchandise_price);
        backGroudPhoto =(LinearLayout)findViewById(R.id.ll_backgroud_photo);
        peisong =(TextView)findViewById(R.id.tv_peisong_price);
//        submit.setBackgroundColor(Color.rgb(136, 136, 136));
        Intent intent=getIntent();
        String photo=intent.getStringExtra("photo");
        BitmapUtils utils = new BitmapUtils(GoodsDetailActivity.this);
        utils.display(backGroudPhoto, photo);
        goodsname = intent.getStringExtra("name");
        final int price=intent.getIntExtra("price", 0);
        tv_price.setText(price+""  );
        peisong.setText(goodsname);
        total = 0;
        count =0;
//        ibSelectdelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ibSelectdelete.setVisibility(View.VISIBLE);
//                total=total-price;
//                if (total>=0){
//                    count--;
//                    totalPrice.setText(total+"");
//                }
//                if(total<25){
//                    submit.setBackgroundColor(Color.rgb(136,136,136));
//                    submit.setClickable(false);
//                }
//            }
//        });
//        ibSelectadd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ibSelectdelete.setVisibility(View.VISIBLE);
//                total = total + price;
//                totalPrice.setText(total + "");
//                if (25 > total) {
//                    submit.setBackgroundColor(Color.rgb(136, 136, 136));
//                    submit.setClickable(false);
//                } else {
//                    submit.setBackgroundResource(R.drawable.btn_selector);
//                    submit.setClickable(true);
//
//                }
//            }
//        });
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String flag=PrefUtils.getString(mContext,"loginValue","");
//                if ("success".equals(flag)){
//
//
////                            intent.putExtra("memo",1+"");
////                            Bundle bundle=new Bundle();
////                            bundle.putSerializable("orderList",orders);
////                            intent.putExtras()
//                    intent.putExtra("totalNum", totalCount + "");
////                            intent.putExtra()
//                }else{
//                    showWarningDialog();
//                }
////                            order.setGoodname(good.getName());
////                            order.setGoodprice((double) good.getPrice());
////                            order.setGoodcount(count);
////                            orders.add(order);
////                            params.addBodyParameter("userid", userid);
////                            params.addBodyParameter("shopid", shopid);
////                            params.addBodyParameter("totalprice", totalprice);
////                            params.addBodyParameter("memo", memo);//等级  评论星级
////                            params.addBodyParameter("orderdetail", orderdetail);//订单详情
////                            params.addBodyParameter("addressid", addressid);
////                            params.addBodyParameter("totalNum", totalNum);
//            }
//        });
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if ("success".equals(flag)){
//                Intent intent = new Intent(GoodsDetailActivity.this, Order_Certain_Activity.class);
//                intent.putExtra("userid", PrefUtils.getString(GoodsDetailActivity.this, "userId", ""));
//                intent.putExtra("shopid", PrefUtils.getString(GoodsDetailActivity.this, "shopId", ""));
//                intent.putExtra("totalprice", total + "");
//                intent.putExtra("goodsName",goodsname);
////                            intent.putExtra("memo",1+"");
//                intent.putExtra("orderdetail", "送的慢，不好吃");
////                            Bundle bundle=new Bundle();
////                            bundle.putSerializable("orderList",orders);
////                            intent.putExtras()
////                intent.putExtra("totalNum", totalCount + "");
//                intent.putExtra("phone", PrefUtils.getString(GoodsDetailActivity.this, "phone", ""));
//                intent.putExtra("name", PrefUtils.getString(GoodsDetailActivity.this, "name", ""));
//                startActivity(intent);
//            }else{
//                    showWarningDialog();
//                }
//            }
//        });


//        intent.putExtra("photo",good.getPhoto());
//        intent.putExtra("name",good.getName());
//        intent.putExtra("price",good.getPrice());
        mHandler = new Handler();
    }
    public void initData(){



    }
    private void onLoad() {
        lv_conment_info.stopRefresh();
        lv_conment_info.stopLoadMore();
        lv_conment_info.setRefreshTime("刚刚");
    }
    public void back(View v){
        int key=v.getId();
        switch (key){
            case R.id.tv_back_menu:finish();break;
        }
    }
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                stores.clear();
                initData();
                // mAdapter.notifyDataSetChanged();
//                adapter = new CommentAdapter(GoodsDetailActivity.this, stores, R.layout.comment_info_item);
                lv_conment_info.setAdapter(adapter);
                onLoad();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                adapter.notifyDataSetChanged();
                onLoad();
            }
        }, 1000);
    }
}
