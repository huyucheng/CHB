package com.chris.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.chris.hunger.base.BasePager;
import com.chris.hunger.baseImpl.ConmentInfoPager;
import com.chris.hunger.baseImpl.GoodsInfoPager;
import com.chris.hunger.baseImpl.StoresInfoPager;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * Created by huyucheng on 2016/3/8.
 */
public class InStoreActivity extends Activity implements ViewPager.OnPageChangeListener{
    private TextView tvSearchStoreByaddress;
    private TextView etSearchStoreByaddress;
    private TabPageIndicator tpiInStore;
    private ViewPager vpInStore;
    private ArrayList<BasePager> mPagerList;
    private String[] titles;
    private TextView tvBackMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.in_store_activity);
        Log.d("tag","进入商品");
        intiViews();
        initData();
    }
    private void intiViews() {
        tvSearchStoreByaddress = (TextView) findViewById(R.id.tv_search_store_byaddress);
        etSearchStoreByaddress = (TextView) findViewById(R.id.et_search_store_byaddress);
        tpiInStore = (TabPageIndicator) findViewById(R.id.Indicator_in_store);
        vpInStore = (ViewPager) findViewById(R.id.vp_in_store);
        tvBackMenu =(TextView)findViewById(R.id.tv_back_menu);
        vpInStore.setOnPageChangeListener(this);
        tpiInStore.setOnPageChangeListener(this);

        Log.d("tag", "初始化数据111");
    }
    public void back(View v){
        int key=v.getId();
        switch (key){
            case R.id.tv_back:Intent intent=new Intent(InStoreActivity.this,MainActivity.class);
                    intent.putExtra("storeFLAG",1);
                    InStoreActivity.this.startActivity(intent);
                    break;
        }
    }
    public void initData(){
        mPagerList=new ArrayList<>();
        mPagerList.add(new GoodsInfoPager(InStoreActivity.this));
        mPagerList.add(new ConmentInfoPager(InStoreActivity.this));
        mPagerList.add(new StoresInfoPager(InStoreActivity.this));
        Log.d("tag", "初始化数据222");
        titles = new String[]{"商品","评价","商家"};
        vpInStore.setAdapter(new TabAdapter());
        tpiInStore.setViewPager(vpInStore,0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        try {

            mPagerList.get(position).initData();//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class TabAdapter extends PagerAdapter {
        /**
         * 重写此方法,返回页面标题,用于viewpagerIndicator的页签显示
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            return pager.mRootView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//        try{
//
//            mPagerList.get(position).initData();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
