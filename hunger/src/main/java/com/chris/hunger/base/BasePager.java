package com.chris.hunger.base;

import android.app.Activity;
import android.view.View;

import org.json.JSONException;

import java.text.ParseException;

public abstract class BasePager {

	public Activity mActivity;
	public View mRootView;// 甯冨眬瀵硅薄

	public BasePager(Activity activity) {
		mActivity = activity;
		mRootView=initViews();
	}
	/**
	 * 鍒濆鍖栧竷灞�
	 */
	public abstract View initViews();
	public void initData() throws JSONException, ParseException {

	}
}