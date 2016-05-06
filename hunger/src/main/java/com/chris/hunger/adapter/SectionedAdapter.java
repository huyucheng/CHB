package com.chris.hunger.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chris.hunger.Bean.GoodsBean;
import com.chris.hunger.Bean.Order_good;
import com.chris.hunger.GoodsDetailActivity;
import com.chris.hunger.Order_Certain_Activity;
import com.chris.hunger.R;
import com.chris.hunger.XLoginMainActivity;
import com.chris.hunger.utils.PrefUtils;
import com.lidroid.xutils.BitmapUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import za.co.immedia.pinnedheaderlistview.SectionedBaseAdapter;

public class SectionedAdapter extends SectionedBaseAdapter {

    private TextView totalPrice;
    private Context mContext;
    private List<GoodsBean.CategoryEntity>  category;
    private LayoutInflater inflater;
    private List<List<GoodsBean.GoodsEntity>> goods;
    private Button submit;
    private int total;
    private int totalCount;
    private final Set<Order_good> orders;

    public SectionedAdapter(Context context, List<GoodsBean.CategoryEntity>  category,List<List<GoodsBean.GoodsEntity>> goods,TextView totalPrice,Button submit){
		this.mContext = context;
		this.category = category;
        this.goods=goods;
        this.totalPrice =totalPrice;
        this.submit=submit;
        inflater = LayoutInflater.from(context);
        orders = new HashSet<>();
	}

    @Override
    public Object getItem(int section, int position) {
        return goods.get(section).get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return category.size();
    }

    @Override
    public int getCountForSection(int section) {
        return goods.get(section).size();
    }
    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.good_list_item, null);
            holder = new ViewHolder();
            holder.goodsImg = (ImageView) convertView.findViewById(R.id.iv_goods_image);
            holder.goodsName = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.goodsGrade = (TextView) convertView.findViewById(R.id.tv_goods_star);
            holder.goodsPrice = (TextView) convertView.findViewById(R.id.tv_goods_price);
            holder.goodsSelectadd = (ImageView) convertView.findViewById(R.id.ib_select_add);
            holder.goodsSelectdelete = (ImageView) convertView.findViewById(R.id.ib_select_delete);
            holder.goodsDetail = (LinearLayout) convertView.findViewById(R.id.ll_goods_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.goodsImg.setImageResource(R.drawable.instore_goods);
//        holder.goodsName.setText("脆皮鸡");
//        holder.goodsPrice.setText("19");
//        holder.goodsGrade.setText("5");
        BitmapUtils utils=new BitmapUtils(mContext);
        final GoodsBean.GoodsEntity good=goods.get(section).get(position);
        utils.display(holder.goodsImg, (String) good.getPhoto());
        holder.goodsName.setText(good.getName());
        holder.goodsName.setTextColor(Color.rgb(180, 180, 180));
        holder.goodsPrice.setText(good.getPrice() + "");
        holder.goodsPrice.setTextColor(Color.rgb(240, 169, 29));

        final Order_good  order = new Order_good();

        holder.goodsSelectdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.goodsSelectdelete.setVisibility(View.VISIBLE);
                if (order.getGoodcount()>0&&total>=0){

                   int count=0+order.getGoodcount();
                    count--;
                    order.setGoodcount(count);
                    totalCount=totalCount-count;
                    total=total-good.getPrice();
                    if (total<25){

                        totalPrice.setText(total+""+"还需"+(25-total));
                    }else if (total>=25){
                        totalPrice.setText(total+"");
                    }
                }


                if(total<25){
                    submit.setBackgroundColor(Color.rgb(136,136,136));
                    submit.setClickable(false);
                }

                if (order!=null&&order.getGoodcount()<=0){
                    orders.remove(order);
                    holder.goodsSelectdelete.setVisibility(View.INVISIBLE);
                }
            }
        });





        holder.goodsSelectadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(mContext, "欢迎订购", Toast.LENGTH_SHORT).show();
                total=total+good.getPrice();
                int count=0;
                if (order.getGoodcount()==null){
                    count++;
                }else {
                    count=order.getGoodcount();
                    count++;
                }
                order.setGoodcount(count);
                totalCount=totalCount+count;
                if (total<25){

                    totalPrice.setText(total+""+"    还需"+(25-total));
                }else if (total>=25){
                    totalPrice.setText(total+"");
                }

                holder.goodsSelectdelete.setVisibility(View.VISIBLE);

                order.setGoodname(good.getName());
                order.setGoodprice(Double.parseDouble(good.getPrice() + ""));

                Log.d("tag", "食品總數量為" + count);
//                totalPrice.setText(total + "");

                if (order!=null&&order.getGoodcount()>0){
                    orders.add(order);
                }

                if(total<25){
                    submit.setBackgroundColor(Color.rgb(136,136,136));
                    submit.setClickable(false);
                }
                else {
                    submit.setBackgroundResource(R.drawable.btn_selector);
                    submit.setClickable(true);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String flag=PrefUtils.getString(mContext,"loginValue","");
                            Log.d("###","确认订单点击成功"+flag);
                            if ("success".equals(flag)){

                                Intent intent = new Intent(mContext, Order_Certain_Activity.class);
                                Bundle bundle = new Bundle();
                                Log.d("tag","集合的长度为_____________________"+orders.size());
                                bundle.putSerializable("orders", (Serializable) orders);
                                intent.putExtras(bundle);

                                intent.putExtra("userid", PrefUtils.getString(mContext, "userId", ""));
                                intent.putExtra("shopid", PrefUtils.getString(mContext, "shopId", ""));
                                intent.putExtra("totalprice", total + "");
//                            intent.putExtra("memo",1+"");
                                intent.putExtra("orderdetail", "送的慢，不好吃");
//                            Bundle bundle=new Bundle();
//                            bundle.putSerializable("orderList",orders);
//                            intent.putExtras()
                                intent.putExtra("totalNum", totalCount + "");
                                intent.putExtra("phone", PrefUtils.getString(mContext, "phone", ""));
                                intent.putExtra("name", PrefUtils.getString(mContext, "name", ""));
//                            intent.putExtra()
                                mContext.startActivity(intent);
                            }else{
                                showWarningDialog();
                            }
//                            order.setGoodname(good.getName());
//                            order.setGoodprice((double) good.getPrice());
//                            order.setGoodcount(count);
//                            orders.add(order);
//                            params.addBodyParameter("userid", userid);
//                            params.addBodyParameter("shopid", shopid);
//                            params.addBodyParameter("totalprice", totalprice);
//                            params.addBodyParameter("memo", memo);//等级  评论星级
//                            params.addBodyParameter("orderdetail", orderdetail);//订单详情
//                            params.addBodyParameter("addressid", addressid);
//                            params.addBodyParameter("totalNum", totalNum);
                        }
                    });
                }

            }
        });
        Log.d("tag","已经刷新商品了");
        holder.goodsDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent=new Intent(mContext, GoodsDetailActivity.class);
                intent.putExtra("photo",good.getPhoto());
                intent.putExtra("name",good.getName());
                intent.putExtra("price",good.getPrice());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    private void showWarningDialog() {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示").setIcon(android.R.drawable.stat_notify_error);
        builder.setMessage("您还未登录，还不能下单");
        builder.setPositiveButton("登录用户", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(mContext, XLoginMainActivity.class);
                mContext.startActivity(intent);
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
    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setBackgroundColor(Color.rgb(245, 245, 245));
        ((TextView) layout.findViewById(R.id.textItem)).setText("满15减10,50减20，指定菜品满19减18");
        ((TextView) layout.findViewById(R.id.textItem)).setTextColor(Color.rgb(255, 102, 51));
        return layout;
    }
    static class ViewHolder {
        public ImageView goodsImg;
        public TextView goodsName;
        public TextView goodsPrice;
        public TextView goodsGrade ;
        public ImageView goodsSelectadd;
        public ImageView goodsSelectdelete;
        public LinearLayout goodsDetail;

    }

}
