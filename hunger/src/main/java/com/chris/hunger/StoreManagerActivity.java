package com.chris.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chris.hunger.Bean.ShopBean;
import com.chris.hunger.Bean.ShopMenu;
import com.chris.hunger.adapter.ListViewAdapter;
import com.chris.hunger.adapter.MyAdapter;
import com.chris.hunger.adapter.PaixuAdapter;
import com.chris.hunger.adapter.SubAdapter;
import com.chris.hunger.utils.CacheUtils;
import com.chris.hunger.utils.GetURLUtil;
import com.chris.hunger.utils.PrefUtils;
import com.chris.hunger.view.XListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyucheng on 2016/3/7.
 */
public class StoreManagerActivity extends Activity implements XListView.IXListViewListener{
    private ListView listView;
    private ListView subListView;
    private MyAdapter myAdapter;
    private SubAdapter subAdapter;
    private LinearLayout ll_menu_list;
    private boolean hide1=false;
    private boolean hide2=false;
    private Handler mHandler;
    List<ShopBean.ShopListEntity> stores;
    private ListViewAdapter adapter;
//    String cities[][] = new String[][] {
//            new String[] {"全部美食", "本帮江浙菜", "川菜", "粤菜", "湘菜","东北菜","台湾菜","新疆/清真","素菜","火锅","自助餐","小吃快餐","日本","韩国料理",
//                    "东南亚菜","西餐","面包甜点","其他"},
//            new String[] {"全部休闲娱乐","咖啡厅","酒吧","茶馆","KTV","电影院","游乐游艺","公园","景点/郊游","洗浴","足浴按摩","文化艺术",
//                    "DIY手工坊","桌球馆","桌面游戏","更多休闲娱乐"},
//            new String[] {"全部购物", "综合商场", "服饰鞋包", "运动户外","珠宝饰品","化妆品","数码家电","亲子购物","家居建材"
//                    ,"书店","书店","眼镜店","特色集市","更多购物场所","食品茶酒","超市/便利店","药店"},
//            new String[] {"全部休闲娱乐","咖啡厅","酒吧","茶馆","KTV","电影院","游乐游艺","公园","景点/郊游","洗浴","足浴按摩","文化艺术",
//                    "DIY手工坊","桌球馆","桌面游戏","更多休闲娱乐"},
//            new String[] {"全","咖啡厅","酒吧","茶馆","KTV","游乐游艺","公园","景点/郊游","洗浴","足浴按摩","文化艺术",
//                    "DIY手工坊","桌球馆","桌面游戏","更多休闲娱乐"},
//            new String[] {"全部","咖啡厅","酒吧","茶馆","电影院","游乐游艺","公园","景点/郊游","洗浴","足浴按摩","文化艺术",
//                    "DIY手工坊","桌球馆","桌面游戏","更多休闲娱乐"},
//            new String[] {"全部休","咖啡厅","酒吧","茶馆","KTV","电影院","游乐游艺","公园","景点/郊游","洗浴","足浴按摩","文化艺术",
//                    "DIY手工坊","桌球馆","桌面游戏","更多休闲娱乐"},
//            new String[] {"全部休闲","咖啡厅","酒吧","茶馆","KTV","电影院","游乐游艺","公园","景点/郊游","洗浴","足浴按摩","文化艺术",
//                    "DIY手工坊","桌球馆","桌面游戏","更多休闲娱乐"},
//            new String[] {"全部休闲娱","咖啡厅","酒吧","茶馆","KTV","电影院","游乐游艺","公园","景点/郊游","洗浴","足浴按摩","文化艺术",
//                    "DIY手工坊","桌球馆","桌面游戏"},
//            new String[] {"全部休闲娱乐","咖啡厅","酒吧","茶馆","KTV","电影院","游乐游艺","公园","景点/郊游","洗浴","足浴按摩","文化艺术",
//                    "DIY手工坊","桌球馆","桌面游戏","更多休闲娱乐"},
//            new String[] {"全部休闲aaa","咖啡厅","酒吧","茶馆","KTV","电影院","游乐游艺","公园","景点/郊游","洗浴","足浴按摩","文化艺术",
//                    "DIY手工坊","桌球馆","桌面游戏"},
//    };
//    String cities[][] =new String[][];
//    private List<String> firstMenu;
//    private List<String> secondMenu;
//    String foods[] =new String []{"全部频道","美食","休闲娱乐","购物","酒店","丽人","运动健身","结婚","亲子","爱车","生活服务"};
//    String foods[] =new String[];
    int images[] = new int[]{R.drawable.ic_category_0,R.drawable.ic_category_10,R.drawable.ic_category_30,R.drawable.ic_category_20
            ,R.drawable.ic_category_60,R.    drawable.ic_category_50,R.drawable.ic_category_45,R.drawable.ic_category_50,R.drawable.ic_category_70,
            R.drawable.ic_category_65,R.drawable.ic_category_80};
    private XListView lv_store_menu;
    private View tv_back_main;
    private ShopBean shopBean;
    private ShopMenu shopMenu;
    final int location=0;
    private RelativeLayout paixun;
    private ListView lvList;
    private PaixuAdapter paixunAdapter;
    private String[] sort;
    private RelativeLayout firstMenu;
    private String[] sortFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            initViews();
            initData();

    }
    private void initViews() {
        // TODO Auto-generated method stub
        stores = new ArrayList<>();
        initData();
        sort = new String[]{"按距离排序查询","按评分排序查询","按销量排序查询","按起送价排序查询"};
        sortFlag =new String[]{"distance","rank","curmonthsales","sendExpense"};
        setContentView(R.layout.store_manager_activity);
        tv_back_main = findViewById(R.id.tv_back_main);
        listView = (ListView) findViewById(R.id.listView);
        subListView = (ListView) findViewById(R.id.subListView);
        ll_menu_list = (LinearLayout) findViewById(R.id.ll_menu_list);
        lvList =(ListView)findViewById(R.id.lv_paixun);
        lv_store_menu = (XListView) findViewById(R.id.lv_store_menu);
        lv_store_menu.setPullLoadEnable(true);
        lv_store_menu.setPullRefreshEnable(true);
        lv_store_menu.setXListViewListener(this);
        paixun =(RelativeLayout)findViewById(R.id.rl_paixun);
        firstMenu =(RelativeLayout)findViewById(R.id.first_menu);
//        myAdapter=new MyAdapter(getApplicationContext(), foods, images);
//        listView.setAdapter(myAdapter);
//        adapter = new ListViewAdapter(StoreManagerActivity.this, stores,R.layout.shop_info_item);


//        myAdapter.setSelectedPosition(0);
//        myAdapter.notifyDataSetInvalidated();

//        subAdapter=new SubAdapter(getApplicationContext(), cities, 0);
//        subListView.setAdapter(subAdapter);
//        subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1,
//                                    int position, long arg3) {
//                // TODO Auto-generated method stub
//                Toast.makeText(getApplicationContext(), cities[location][position], Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//                                    long arg3) {
//                // TODO Auto-generated method stub
//                final int location = position;
//                myAdapter.setSelectedPosition(position);
//                myAdapter.notifyDataSetInvalidated();
//                subAdapter = new SubAdapter(getApplicationContext(), cities, position);
//                subListView.setAdapter(subAdapter);
//                subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> arg0, View arg1,
//                                            int position, long arg3) {
//                        // TODO Auto-generated method stub
//                        Toast.makeText(getApplicationContext(), cities[location][position], Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
        tv_back_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreManagerActivity.this, MainActivity.class);
                intent.putExtra("storeFLAG",1);
                startActivity(intent);
            }
        });

        mHandler = new Handler();
    }
    private void initData() {

        String cache= CacheUtils.getCache(GetURLUtil.SHOP_BASEURL, StoreManagerActivity.this);
        if (!TextUtils.isEmpty(cache)){
            parseData(cache);
            Log.d("tag", "ccccc" + cache);
        }
        getDataFromServer();

    }
    private void getDataFromServer() {
        Intent intent=getIntent();
        int FLAG=intent.getIntExtra("FLAG",1);
        RequestParams params = new RequestParams(GetURLUtil.SHOP_BASEURL+"id="+FLAG);
        Log.d("tag", "进入xtuil了");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("tag", "成功进入美食列表" + result);
                parseData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(StoreManagerActivity.this, "输入信息有误，请重新填写", Toast.LENGTH_SHORT).show();
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
        adapter = new ListViewAdapter(StoreManagerActivity.this, stores,R.layout.shop_info_item);
        lv_store_menu.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
    }
    public void onClick(View v) {
        int key=v.getId();
        switch (key){
            case R.id.first_menu:
                hide1=!hide1;
                hide2=false;
                if (hide1&&!hide2){
                    ll_menu_list.setVisibility(View.VISIBLE);
                    lvList.setVisibility(View.GONE);
                    initDataSecondMenu();
                }/*else if (hide1&&hide2){
                    ll_menu_list.setVisibility(View.VISIBLE);
                    lvList.setVisibility(View.GONE);
                    initDataSecondMenu();
                }*/else {
                    ll_menu_list.setVisibility(View.GONE);
                }
                break;
            case R.id.rl_paixun:
                hide2=!hide2;
                hide1=false;
                if (hide2&&!hide1){
                    Log.d("tag", "点击排序成功了____________________");
                    lvList.setVisibility(View.VISIBLE);
                    ll_menu_list.setVisibility(View.GONE);
                    PaixuInitData();
                }/*else if (hide2&&hide1){
                    ll_menu_list.setVisibility(View.GONE);
                    lvList.setVisibility(View.VISIBLE);
                    PaixuInitData();
                }*/else {
                    lvList.setVisibility(View.GONE);
                }
                break;
        }
    }
    private void initDataSecondMenu() {

        String cache= CacheUtils.getCache(GetURLUtil.MENU_BASEURL, StoreManagerActivity.this);
        if (!TextUtils.isEmpty(cache)){
            parseData(cache);
            Log.d("tag", "ccccc" + cache);
        }
        getDataFromServerSecondMenu();

    }
    private void getDataFromServerSecondMenu() {
        RequestParams params = new RequestParams(GetURLUtil.MENU_BASEURL);
        Log.d("tag", "进入xtuil了");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("tag", "成功进入二级菜单了" + result);
                parseDataSecondMenu(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(StoreManagerActivity.this, "输入信息有误，请重新填写", Toast.LENGTH_SHORT).show();
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
    protected void parseDataSecondMenu(String result) {
        Gson gson = new Gson();
        shopMenu = gson.fromJson(result, ShopMenu.class);
        List<ShopMenu.SfclistEntity> shopFirstMenu = shopMenu.getSfclist();
        List<List<ShopMenu.SsclistEntity>> shopSecondMenu=shopMenu.getSsclist();
        Log.d("tag", shopFirstMenu.size() + "mmmmmmmm" + result);

        Log.d("tag", shopFirstMenu.get(6).getName() + "==========================="+shopSecondMenu.size());
//        adapter = new ListViewAdapter(StoreManagerActivity.this, stores,R.layout.shop_info_item);
//        lv_store_menu.setAdapter(adapter);
        String foods[] =new String[shopFirstMenu.size()];
        final String cities[][] =new String[shopFirstMenu.size()][shopSecondMenu.size()];
        for(int i=0;i<shopFirstMenu.size();i++){
            foods[i]=shopFirstMenu.get(i).getName();
            Log.d("tag",foods[i]+"进入二级菜单成功");
        }
        for(int i=0;i<shopFirstMenu.size();i++){
            int line=i;
            for(int j=0;j<shopSecondMenu.get(line).size();j++){
                cities[i][j]=shopSecondMenu.get(i).get(j).getName();
                Log.d("tag",cities[i][j]+"进入二级菜单成功子图le1");
            }
        }

        subAdapter=new SubAdapter(getApplicationContext(), cities, 0,ll_menu_list);
        subListView.setAdapter(subAdapter);
        myAdapter=new MyAdapter(getApplicationContext(), foods, images);
        listView.setAdapter(myAdapter);
        subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub
//                Toast.makeText(getApplicationContext(), cities[location][position], Toast.LENGTH_SHORT).show();

                ll_menu_list.setVisibility(View.GONE);
                getDataFromServer();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                final int location = position;
                myAdapter.setSelectedPosition(position);
                myAdapter.notifyDataSetInvalidated();
                subAdapter = new SubAdapter(getApplicationContext(), cities, position,ll_menu_list);
                subListView.setAdapter(subAdapter);
                subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(), cities[location][position], Toast.LENGTH_SHORT).show();
//                        getDataFromServerNew();
                        ll_menu_list.setVisibility(View.GONE);
                    }
                });
            }
        });
        myAdapter.setSelectedPosition(0);
        myAdapter.notifyDataSetInvalidated();
//		adapter.notifyDataSetChanged();
    }
    private void onLoad() {
        lv_store_menu.stopRefresh();
        lv_store_menu.stopLoadMore();
        lv_store_menu.setRefreshTime("刚刚");
    }
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stores.clear();
                initData();
                // mAdapter.notifyDataSetChanged();
                adapter = new ListViewAdapter(StoreManagerActivity.this, stores, R.layout.shop_info_item);
                lv_store_menu.setAdapter(adapter);
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
                Toast.makeText(StoreManagerActivity.this, "输入信息有误，请重新填写", Toast.LENGTH_SHORT).show();
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
    private void PaixuInitData(){
        sort = new String[]{"按距离排序查询","按评分排序查询","按销量排序查询","按起送价排序查询"};
        paixunAdapter =new PaixuAdapter(getApplicationContext(), sort);

        Log.d("tag", "进入排序listview成功了____________________");
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("tag", "进入" + position + "——————————————————————————————————————————");
                lvList.setVisibility(View.GONE);
                PaixunGetDataFromServer(position);
            }
        });
        lvList.setAdapter(paixunAdapter);
    }

    private void PaixunGetDataFromServer(int positon) {
        RequestParams params=null;
        if (positon==0){
            String latitude=PrefUtils.getString(StoreManagerActivity.this, "latitude", "120.75345");
            String longitude=PrefUtils.getString(StoreManagerActivity.this, "longitude", "31.276669");
            Log.d("###",latitude+"______经纬度可以查询了__________"+longitude);
            params = new RequestParams(GetURLUtil.DISTANCE_BASEURL+"axisX="+latitude+"&"+"axisY="+longitude+"&type=2");
        }else if (positon==1){
            params = new RequestParams(GetURLUtil.RANK_BASEURL);
            Log.d("###","___111___经纬度可以查询了__________");

        }else if(positon==2){
            params = new RequestParams(GetURLUtil.SALES_BASEURL);
            Log.d("###","___222___经纬度可以查询了__________");


        }else if (positon==3){
            params=new RequestParams(GetURLUtil.SEND_BASEURL);
            Log.d("###","___333___经纬度可以查询了__________");

        }


        Log.d("tag", "进入排序了————————————————————————了");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("tag", "成功进入排序列表了_________" + result);
                PaixuParseData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                Toast.makeText(StoreManagerActivity.this, "输入信息有误，请重新填写", Toast.LENGTH_SHORT).show();
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
    protected void PaixuParseData(String result) {
        Gson gson = new Gson();
        stores.clear();


        shopBean = gson.fromJson(result, ShopBean.class);
        List<ShopBean.ShopListEntity> shops = shopBean.getShopList();
        Log.d("tag", shops.size() + "mmmmmmmm" + result);
//        Log.d("tag", stores.size() + "===========================");
        Log.d("tag", "成功进入刷新列表了_________" + result);
        adapter = new ListViewAdapter(StoreManagerActivity.this, shops,R.layout.shop_info_item);
        lv_store_menu.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
    }



}
