package com.chris.hunger.baseImpl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.Bean.GoodsBean;
import com.chris.hunger.R;
import com.chris.hunger.adapter.GoodsCategoryAdapter;
import com.chris.hunger.adapter.SectionedAdapter;
import com.chris.hunger.base.BasePager;
import com.chris.hunger.utils.GetURLUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import za.co.immedia.pinnedheaderlistview.PinnedHeaderListView;

/**
 * Created by huyucheng on 2016/3/8.
 */
public class GoodsInfoPager extends BasePager{

    private boolean isScroll = true;

    private ListView left_listView;
   private List<GoodsBean.CategoryEntity>  category;
//    private String[][] rightStr = null;
//    private List<GoodsBean.CategoryEntity> category=null;
    private GoodsBean goodsBean;
    private List<List<GoodsBean.GoodsEntity>> goods=null;
    private SectionedAdapter sectionedAdapter;
    private PinnedHeaderListView right_listview;
    private TextView totalPrice;
    private Button submit;
    //    private String[] leftStr;

    public GoodsInfoPager(Activity activity) {
        super(activity);
    }


    @Override
    public View initViews() {
       initData();
        View view=View.inflate(mActivity, R.layout.goods_info_pager,null);
        right_listview = (PinnedHeaderListView) view.findViewById(R.id.pinnedListView);
        LayoutInflater inflator = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout header1 = (LinearLayout) inflator.inflate(R.layout.list_item, null);
        ((TextView) header1.findViewById(R.id.textItem)).setText("啤酒喷鸡屁股");
        LinearLayout header2 = (LinearLayout) inflator.inflate(R.layout.list_item, null);
        ((TextView) header2.findViewById(R.id.textItem)).setText("HEADER 2");
        LinearLayout footer = (LinearLayout) inflator.inflate(R.layout.list_item, null);
        ((TextView) footer.findViewById(R.id.textItem)).setText("FOOTER");

        totalPrice =(TextView)view.findViewById(R.id.tv_total_price);
        submit =(Button)view.findViewById(R.id.btn_submit_order);
        submit.setBackgroundColor(Color.rgb(136,136,136));
        left_listView = (ListView) view.findViewById(R.id.left_listview);

        Log.d("tag", "进入商品信息");
        left_listView.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long arg3) {
                isScroll = false;

                for (int i = 0; i < left_listView.getChildCount(); i++) {
                    if (i == position) {
                        left_listView.getChildAt(i).setBackgroundColor(Color.rgb(255, 255, 255));
                    } else {
                        left_listView.getChildAt(i).setBackgroundColor(Color.rgb(245,245,245));
                    }
                }

                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += sectionedAdapter.getCountForSection(i) + 1;
                }
                right_listview.setSelection(rightSection);

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {

            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }

        });
        right_listview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < left_listView.getChildCount(); i++) {

                        if (i == sectionedAdapter.getSectionForPosition(firstVisibleItem)) {
                            left_listView.getChildAt(i).setBackgroundColor(
                                    Color.rgb(255, 255, 255));
                        } else {
                            left_listView.getChildAt(i).setBackgroundColor(Color.rgb(245,245,245));

                        }
                    }

                } else {
                    isScroll = true;
                }
            }
        });
        return view;
    }
    public void initData(){
//        String cache= CacheUtils.getCache(GetURLUtil.GOODS_BASEURL, mActivity);
//        if (!TextUtils.isEmpty(cache)){
//            parseData(cache);
//            Log.d("tag","ccccc"+cache);
//        }
        getDataFromServer();

//            leftStr =new String[]{"立减套餐","新品上市","热销榜","排骨系列","炸鸡薯条","销量最高","好评最多","特色套餐"};
//        rightStr=new String[][]{
//                {"星期一  早餐","星期一  午餐","星期一  晚餐"}, {"星期二  早餐","星期二  午餐","星期二  晚餐"},
//                {"星期三  早餐","星期三  午餐","星期三  晚餐"}, {"星期四  早餐","星期四  午餐","星期四  晚餐"},
//                {"星期五  早餐","星期五  午餐","星期五  晚餐"}, {"星期六  早餐","星期六  午餐","星期六  晚餐"},
//                {"星期日  早餐","星期日  午餐","星期日  晚餐"}};
//        leftStr=new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    }
    private void getDataFromServer() {
        RequestParams params = new RequestParams(GetURLUtil.GOODS_BASEURL);
        Log.d("tag", "进入xtuil了");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("tag", "进入商店成功了" + result);
                parseData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mActivity, "输入信息有误，请重新填写2", Toast.LENGTH_SHORT).show();
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
        goodsBean = gson.fromJson(result, GoodsBean.class);
        Log.d("tag", goodsBean.toString() + "&&&&&&&&&&&&&&&&&" + result);
        category=goodsBean.getCategory();
        List<String>  nameList=new ArrayList<>();
        for(GoodsBean.CategoryEntity cate:category){
            nameList.add(cate.getName());
        }
        goods=goodsBean.getGoods();
        sectionedAdapter = new SectionedAdapter(mActivity, category, goods,totalPrice,submit);
        right_listview.setAdapter(sectionedAdapter);
//        left_listView.setAdapter(new ArrayAdapter<>(mActivity,
//                android.R.layout.simple_expandable_list_item_1, nameList));
        left_listView.setAdapter(new GoodsCategoryAdapter(mActivity,R.layout.category_item,nameList));
    }
}
