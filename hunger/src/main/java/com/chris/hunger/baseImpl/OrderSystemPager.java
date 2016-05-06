package com.chris.hunger.baseImpl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.Bean.Order;
import com.chris.hunger.Bean.Order_good;
import com.chris.hunger.InStoreActivity;
import com.chris.hunger.Order_Certain_Activity;
import com.chris.hunger.Order_Comment_Activity;
import com.chris.hunger.Order_Detail_Activity;
import com.chris.hunger.R;
import com.chris.hunger.XLoginMainActivity;
import com.chris.hunger.adapter.OrderGoodListAdapter;
import com.chris.hunger.base.BasePager;
import com.chris.hunger.entity.Shop;
import com.chris.hunger.utils.CacheUtils;
import com.chris.hunger.utils.GetURLUtil;
import com.chris.hunger.utils.PrefUtils;
import com.chris.hunger.view.Order_ListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单管理
 *
 * @author Kevin
 *
 */
//XListView.IXListViewListener,
public class OrderSystemPager extends BasePager implements GetURLUtil {

	private Order_ListView ordering_list;
	private Handler mHandler;
	public static List<Order> order;
	private OrderListAdapter adapter;
	private String userid="2";
	private RelativeLayout load;
	private TextView loadtext;

	public OrderSystemPager(Activity activity) {
		super(activity);
	}
	@Override
	public View initViews() {
		// TODO Auto-generated method stub
			order=new ArrayList<>();
			View view = View
					.inflate(mActivity, R.layout.order_list, null);//得到主界面layout
			ordering_list = (Order_ListView) view.findViewById(R.id.ordering_list);//XListView控件获取
//			ordering_list.setPullLoadEnable(false);
//			ordering_list.setPullRefreshEnable(false);
			load= (RelativeLayout) view.findViewById(R.id.load_tips);
			loadtext= (TextView) view.findViewById(R.id.tv_click_info);
			loadtext.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					loadtext.setClickable(false);
					Intent intent=new Intent(mActivity, XLoginMainActivity.class);
					mActivity.startActivity(intent);
				}
			});
		    initData();//初始化数据
			mHandler = new Handler();//作用？没有不能刷新
			return view;
		}



	@Override
	public void initData(){
		String cache = CacheUtils.getCache(Order_BaseURL + "orderList.do?userid=2&limit=10&offset=0",
				mActivity);
		if (!TextUtils.isEmpty(cache)) {// 如果缓存存在,直接解析数据, 无需访问网路
				parseData(cache);}
		String login=PrefUtils.getString(mActivity,"loginValue","");
		Log.d("tag", "订单yyyyyyyynnnnnnnnnnnnnnn" + login);
		if ("success".equals(login)){
			getDataFromServer();
			load.setVisibility(View.GONE);
			ordering_list.setVisibility(View.VISIBLE);

		}else {
			load.setVisibility(View.VISIBLE);
			ordering_list.setVisibility(View.INVISIBLE);
		}

	}




private void getDataFromServer() {
	HttpUtils utils = new HttpUtils();
	final String URL=Order_BaseURL+"orderList.do?userid="+userid+"&limit=10&offset=0";
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
						e.printStackTrace();}}
				// 访问失败, 在主线程运行
				@Override
				public void onFailure(HttpException error, String msg) {
					Toast.makeText(mActivity, "当前网络不可用,请检查网络！", Toast.LENGTH_SHORT).show();
					Log.i("########", "loginfail  " + Log.getStackTraceString(error) + "!!  ###  " + msg);
					error.printStackTrace();}});}
	//解析数据
	protected void parseData(String result) {
		for(int i=0;i<order.size();i++) {
			order.remove(i);
		}
		order.clear();
		try{
			JSONObject json = new JSONObject(result);
			JSONArray json1 = json.getJSONArray("rows");
			String total = json.getString("total");
			//每一个订单赋值
			for (int i = 0; i < json1.length(); i++) {
				Log.d("tag",json1.length()+"uuuuuUu");
				Order order1 = new Order();
				List<Order_good> ordergoods = new ArrayList<Order_good>();
				JSONObject jsonObj  = json1.optJSONObject(i);
				//订单商品赋值
				JSONArray orderdelist = jsonObj .getJSONArray("orderdelist");

				int total1=0;
				for (int j = 0; j < orderdelist.length(); j++) {	//获取订单的商品
					JSONObject jordergood = orderdelist.optJSONObject(j);
					Order_good order_good = new Order_good();
					String a = jordergood.getString("productnum");
					String b = jordergood.getString("productname");
					String c = jordergood.getString("productprice");
					order_good.setGoodname(a);
					order_good.setGoodcount(Integer.parseInt(c));
					order_good.setGoodprice(Double.parseDouble(b));
					ordergoods.add(order_good);
					total1+=Integer.parseInt(c);
				}
				//商品详情
				order1.setGoods(ordergoods);
				//商品总计
				order1.setTotalnum(total1);
				//商店信息
				JSONObject shopdetail=jsonObj.getJSONObject("shop");
				String shopid=shopdetail.getString("id");
				String shopurl= shopdetail.getString("shopphoto");
				shopurl.replace("\\","/");
				shopurl=SHOP_LOCAL_PHOTO+shopurl;
//				String shopurl="http://10.6.12.58:8080/picture/shop_1458916006519.jpg";
				String shopname=shopdetail.getString("username");
				Shop shop=new Shop();
				shop.setId(Integer.parseInt(shopid));
				shop.setShopphoto(shopurl);
				shop.setName(shopname);
				order1.setShop(shop);

				//订单id
				String orderid=jsonObj.getString("id");
				order1.setId(Integer.parseInt(orderid));

				//用戶信息id
				JSONObject user =jsonObj.getJSONObject("user");
				String username=user.getString("username");
				order1.setUsername(username);
				String userid= user.getString("id");
				order1.setUserid(Integer.parseInt(userid));

				//订单时间
				String ordertime =  jsonObj .getString("ordertime");
				Log.i("!!!!!","ordertime :"+ordertime);
				Date d=new Date(Long.parseLong(ordertime));
				order1.setOrdertime(d);

				//订单状态
				String orderstatus = jsonObj .getString("orderstatus");
				order1.setOrderstatus(Integer.parseInt(orderstatus));

				//设置评价
				if(jsonObj.getString("comment")!=null)
				order1.setComment(jsonObj.getString("comment"));
				order1.setOrderdetail(jsonObj.getString("orderdetail"));

				//总价
				String totalprice=jsonObj.getString("totalprice");
				order1.setTotalprice(Double.parseDouble(totalprice));

				//加入order
				order.add(order1);
				Log.i("JSONDATA", ordertime + orderstatus+"    ##"+total);
				//设定用户的id号
			}//order 结束

			adapter = new OrderListAdapter(mActivity,order,mHandler);//新建适配器对象
			//Adapter 数据源改变时调用
			ordering_list.setAdapter(adapter);//控件设置适配
			adapter.notifyDataSetChanged();
		}catch (Exception e){
			e.printStackTrace();
		}
	}





	public class OrderListAdapter extends BaseAdapter implements GetURLUtil {

		private LayoutInflater inflater;
		Context c;
		private List<Order> order;
		public OrderGoodListAdapter goodadapter;
		public  Handler mHandler;
		ViewHolder holder;
		public int i=0;
		public String result;

		public OrderListAdapter(Context context, List<Order> order,Handler handler) {
			if (order == null) {
				this.order = new ArrayList<Order>();
			} else {
				this.order = order;
			}
			inflater = LayoutInflater.from(context);
			c=context;
			this.mHandler=handler;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return order.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return order.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final int flag=position;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.order_detail, null);
				holder = new ViewHolder();
				holder.merchant_img = (ImageView) convertView.findViewById(R.id.merchant_icon);
				holder.orderstatus = (TextView) convertView.findViewById(R.id.order_status);
				holder.shopname = (TextView) convertView.findViewById(R.id.shop_name);
				holder.totalprice = (TextView) convertView.findViewById(R.id.total_price);
				holder.ordertime = (TextView) convertView.findViewById(R.id.order_time);
				holder.bt_buy=(Button)convertView.findViewById(R.id.order_button);
				holder.bt_comment=(Button)convertView.findViewById(R.id.evaluate_button);
				holder.delete_img=(ImageView) convertView.findViewById(R.id.delete_icon);
				holder.order_detail_list=(ListView)convertView.findViewById(R.id.order_list);
				holder.order_count = (TextView) convertView.findViewById(R.id.order_count);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}

			try {
				convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(c, Order_Detail_Activity.class);
						Bundle bd=new Bundle();
						bd.putString("id",order.get(flag).getId().toString());
						intent.putExtra("bd", bd);
						c.startActivity(intent);
					}
				});
			}catch(Exception e){
				e.printStackTrace();
			}


			//评价事件响应
			Log.i("！！！！！ordercomment ",flag+"..."+order.get(position).getComment());
			if(order.get(position).getComment().equals("null")) {
				holder.bt_comment.setClickable(true);
				holder.bt_comment.setVisibility(View.VISIBLE);
				holder.bt_comment.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						OrderSystemPager.order.get(flag).setComment("1");
						holder.bt_comment.setClickable(false);
						holder.bt_comment.setVisibility(View.GONE);
						Intent intent = new Intent(c, Order_Comment_Activity.class);
						Bundle bd = new Bundle();
						bd.putString("orderid", order.get(flag).getId().toString());
						bd.putString("userid", order.get(flag).getUserid().toString());
						bd.putString("shopid", order.get(flag).getShop().getId().toString());
						intent.putExtra("bd", bd);
//					order.get(flag).setComment("1");
						c.startActivity(intent);
						adapter.notifyDataSetChanged();
					}
				});
			}else{
				holder.bt_comment.setClickable(false);
				holder.bt_comment.setVisibility(View.GONE);
			}


			List<Order_good> goods;
			goods=order.get(position).getGoods();
		Log.i("!!!!!!!!!!!!!!!!", "orderListAdapter" + goods.size() + "    goodposition" + position);
			goodadapter = new OrderGoodListAdapter(c, goods);
			holder.order_detail_list.setAdapter(goodadapter);
			goodadapter.notifyDataSetChanged();

			//获取bitmap图片绑定上控件
			BitmapUtils utils=new BitmapUtils(c);
			utils.display(holder.merchant_img, order.get(position).getShop().getShopphoto());

			holder.merchant_img.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {

					Intent intent = new Intent(c, InStoreActivity.class);
					Bundle bd = new Bundle();
					bd.putString("orderid", order.get(flag).getId().toString());
					intent.putExtra("bd", bd);
					c.startActivity(intent);
				}
			});

			holder.delete_img.setImageResource(R.drawable.goods_searchbar_clean);
			holder.delete_img.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					AlertDialog.Builder builder=new AlertDialog.Builder(c);
					builder.setMessage("确认删除么？删除后不可恢复哦");
					builder.setTitle("删除订单");
					builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							getDataFromServer(order.get(flag).getId().toString());
							Log.i("!!!!", "www"+order.get(flag).getId().toString());
							if(i==0) {
								order.remove(flag);
								Toast.makeText(c, "删除订单成功!", Toast.LENGTH_SHORT).show();
								notifyDataSetChanged();
								i=0;
							}else {

							}
							dialog.dismiss();
						}
					});
					builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					builder.create().show();
				}
			});

			if(order.get(position).getOrderstatus()==2) {
				holder.orderstatus.setText("订单正在处理");
			}else if(order.get(position).getOrderstatus()==3) {
				holder.orderstatus.setText("订单不受理");
			}else{
				holder.orderstatus.setText("订单完成");
			}

			holder.order_count.setText(order.get(position).getTotalnum().toString());
			holder.shopname.setText(order.get(position).getShop().getName());
			holder.totalprice.setText(order.get(position).getTotalprice().toString());
			//设置订单时间
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//这边改变格式
			Date d = order.get(position).getOrdertime();
			String ordertime1 = s.format(d);//可以看改变格式的效果
			holder.ordertime.setText(ordertime1);
			//再来一单响应事件
			holder.bt_buy.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(c, Order_Certain_Activity.class);
					Bundle bd = new Bundle();
					bd.putString("orderId", order.get(flag).getId().toString());
					intent.putExtra("bd", bd);
					c.startActivity(intent);
				}
			});


			return convertView;
		}

		 class ViewHolder {
			public ImageView merchant_img;
			public TextView orderstatus;
			public TextView shopname ;
			public ListView order_detail_list;
			public TextView totalprice;
			public TextView ordertime;
			public Button bt_buy;
			public Button bt_comment;
			public ImageView delete_img;
			public TextView order_count;
		}

		@Override
		public boolean isEnabled(int position) {
			return true;
		}

		private void getDataFromServer(final String oid) {
			HttpUtils utils = new HttpUtils();
			final String URL=Order_BaseURL+"orderDelete.do?orderid="+oid;
			// 使用xutils发送请求
			HttpHandler<String> send = utils.send(HttpRequest.HttpMethod.GET, URL,
					new RequestCallBack<String>() {
						// 访问成功, 在主线程运行
						@Override
						public void onSuccess(ResponseInfo responseInfo) {
							String result1 = (String) responseInfo.result;
							System.out.println("!!!!!返回结果:" + result1+ URL);
							try {
								JSONObject jsonObject = new JSONObject(result1);
								result=jsonObject.getString("result");
							}catch (Exception e) {
								e.printStackTrace();
							}

							if(Integer.parseInt(result)==1){
								i=0;
							}
						}

						// 访问失败, 在主线程运行
						@Override
						public void onFailure(HttpException error, String msg) {
							Toast.makeText(c, "当前网络不可用,请检查网络！", Toast.LENGTH_SHORT).show();
							error.printStackTrace();
						}
					}
			);
		}

	}




}
