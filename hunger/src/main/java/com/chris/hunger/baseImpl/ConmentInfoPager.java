package com.chris.hunger.baseImpl;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chris.hunger.Bean.CommentBean;
import com.chris.hunger.R;
import com.chris.hunger.adapter.CommentAdapter;
import com.chris.hunger.base.BasePager;
import com.chris.hunger.utils.GetURLUtil;
import com.chris.hunger.view.XListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyucheng on 2016/3/8.
 */
public class ConmentInfoPager extends BasePager implements XListView.IXListViewListener{

    private XListView lv_conment_info;
    private CommentAdapter adapter;
    private Handler mHandler;
    private CommentBean commentBean;
    private List<CommentBean.ShopCommentListEntity> commentList;
    public ConmentInfoPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {
        commentList = new ArrayList<>();
//        initData();
        View view=View.inflate(mActivity, R.layout.comment_info_pager,null);
        lv_conment_info = (XListView) view.findViewById(R.id.lv_conment_info);
        lv_conment_info.setPullRefreshEnable(true);
        lv_conment_info.setPullLoadEnable(true);
//        lv_conment_info.setPullRefreshEnable(true);
        lv_conment_info.setXListViewListener(this);

        mHandler = new Handler();
        initData();
        return view;
    }
    public void initData(){
//        String cache= CacheUtils.getCache(GetURLUtil.COMMENT_BASEURL, mActivity);
//        if (!TextUtils.isEmpty(cache)){
//            parseData(cache);
//        }
        getDataFromServer();
    }
    private void getDataFromServer() {
        RequestParams params = new RequestParams(GetURLUtil.COMMENT_BASEURL);
        Log.d("tag", "进入商店内的评价了");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("tag", "成功获取到评论" + result);
                parseData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mActivity, "输入信息有误，请重新填写", Toast.LENGTH_SHORT).show();
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
    private void onLoad() {
        lv_conment_info.stopRefresh();
        lv_conment_info.stopLoadMore();
        lv_conment_info.setRefreshTime("刚刚");
    }

    protected void parseData(String result) {
        Gson gson = new Gson();
        commentList.clear();


        commentBean = gson.fromJson(result, CommentBean.class);
        List<CommentBean.ShopCommentListEntity> comments = commentBean.getShopCommentList();
        Log.d("tag", comments.size() + "已经进入评论了" + result);
        commentList.addAll(comments);
        Log.d("tag", commentList.size() + "===========================");
        adapter = new CommentAdapter(mActivity, commentList,R.layout.comment_info_item);
        lv_conment_info.setAdapter(adapter);
    }
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                commentList.clear();
                initData();
                // mAdapter.notifyDataSetChanged();
                adapter = new CommentAdapter(mActivity, commentList, R.layout.comment_info_item);
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
                moreData();
                onLoad();
            }
        }, 1000);
    }
    protected void moreData(){
        RequestParams params = new RequestParams(GetURLUtil.COMMENT_BASEURL);
        Log.d("tag", "进入商品评论了");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("tag", "成功了" + result);
                Gson gson = new Gson();
                commentBean = gson.fromJson(result, CommentBean.class);
                List<CommentBean.ShopCommentListEntity> comments = commentBean.getShopCommentList();
                Log.d("tag", comments.size() + "mmmmmmmm" + result);
                commentList.addAll(comments);
                Log.d("tag", commentList.size() + "===========================");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                Toast.makeText(mActivity, "输入信息有误，请重新填写", Toast.LENGTH_SHORT).show();
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
}
