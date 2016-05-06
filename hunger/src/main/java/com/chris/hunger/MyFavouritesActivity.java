package com.chris.hunger;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.Bean.CollectionBean;
import com.chris.hunger.adapter.MyFavouriteAdapter;
import com.chris.hunger.utils.CacheUtils;
import com.chris.hunger.utils.GetURLUtil;
import com.chris.hunger.utils.PrefUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by guozi on 2016/3/14.
 */
public class MyFavouritesActivity extends Activity {

    private ImageView ivBackMyCollection;
    private TextView tvMyCollection;
    private ListView favouritesList;
    private MyFavouriteAdapter adapter;
    private CollectionBean collectionBean;

    private void assignViews() {
        ivBackMyCollection = (ImageView) findViewById(R.id.iv_back_my_collection);
        tvMyCollection = (TextView) findViewById(R.id.tv_my_collection);
        favouritesList = (ListView) findViewById(R.id.favourites_list);
        ivBackMyCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_my);
        assignViews();
        initData();
    }


    public void initData() {
        String cache= CacheUtils.getCache(GetURLUtil.LOVE_QUERY_BASEURL, MyFavouritesActivity.this);
        if (!TextUtils.isEmpty(cache)){
            parseData(cache);
            Log.d("tag", "ccccc" + cache);
        }
        getDataFromServer();
    }
    private void getDataFromServer() {
        String userId=PrefUtils.getString(MyFavouritesActivity.this,"userId","");
        RequestParams params = new RequestParams(GetURLUtil.LOVE_QUERY_BASEURL+"userid="+userId);
        Log.d("tag", "进入xtuil了");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("tag", "成功進入收藏了" + result);
                parseData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MyFavouritesActivity.this, "输入信息有误，请重新填写", Toast.LENGTH_SHORT).show();
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
        collectionBean = gson.fromJson(result, CollectionBean.class);
        List<CollectionBean.FavouriteEntity> favourites = collectionBean.getFavourite();
        Log.d("tag","進入收藏了"+result);
        adapter = new MyFavouriteAdapter(MyFavouritesActivity.this, favourites,R.layout.shop_info_item);
        favouritesList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
    }


}
