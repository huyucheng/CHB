package com.chris.hunger;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.List;

public class SearchStoreByNameActivity extends FragmentActivity implements
		OnGetPoiSearchResultListener, OnGetSuggestionResultListener {
	private ImageView ivDeleteText;
	private AutoCompleteTextView etSearch = null;
	private PoiSearch mPoiSearch = null;
	private ArrayAdapter<String> sugAdapter = null;
	private BaiduMap mBaiduMap = null;
	private List<String> suggest;
	private SuggestionSearch mSuggestionSearch = null;
	private EditText editCity;
	private EditText editSearchKey;
	private int loadIndex=0;
	private ListView searchList;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.search_store_address_activity);
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		mSuggestionSearch = SuggestionSearch.newInstance();
		mSuggestionSearch.setOnGetSuggestionResultListener(this);
        ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);

        etSearch = (AutoCompleteTextView) findViewById(R.id.etSearch);


		searchList =(ListView)findViewById(R.id.searchListView);


		sugAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);
		etSearch.setAdapter(sugAdapter);
		etSearch.setThreshold(1);
		editCity = (EditText) findViewById(R.id.city);
		editSearchKey = (EditText) findViewById(R.id.etSearch);
		editCity.setText(MainActivity.LOCATION_CITY);
		editSearchKey.setText(MainActivity.LOCATION_DISTRICT + MainActivity.LOCATION_STREET);
		ivDeleteText.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				etSearch.setText("");
			}
		});
		loadIndex++;
		mPoiSearch.searchInCity((new PoiCitySearchOption())
				.city(editCity.getText().toString())
				.keyword(editSearchKey.getText().toString())
				.pageNum(loadIndex));
		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
										  int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
									  int arg3) {
				if (cs.length() <= 0) {
					return;
				}
				String city = ((EditText) findViewById(R.id.city)).getText()
						.toString();
				/**
				 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
				 */
				mSuggestionSearch
						.requestSuggestion((new SuggestionSearchOption())
								.keyword(cs.toString()).city(city));
			}
		});
        etSearch.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					ivDeleteText.setVisibility(View.GONE);
				} else {
					ivDeleteText.setVisibility(View.VISIBLE);
				}
			}
		});
		etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String obj = (String) parent.getItemAtPosition(position);
				Intent intent = new Intent(SearchStoreByNameActivity.this, MainActivity.class);

				intent.putExtra("address", obj);
				setResult(1, intent);
				finish();
			}
		});
    }
	@Override
	protected void onDestroy() {
		mPoiSearch.destroy();
		mSuggestionSearch.destroy();
		super.onDestroy();
	}
	public void back(View v) {
		finish();
	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(SearchStoreByNameActivity.this, "未找到结果", Toast.LENGTH_LONG)
					.show();
			return;
		}
//		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//			mBaiduMap.clear();
//			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
//			mBaiduMap.setOnMarkerClickListener(overlay);
//			overlay.setData(result);
//			overlay.addToMap();
//			overlay.zoomToSpan();
//			return;
//		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			Toast.makeText(SearchStoreByNameActivity.this, strInfo, Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(SearchStoreByNameActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(SearchStoreByNameActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult res) {
		if (res == null || res.getAllSuggestions() == null) {
			return;
		}
		suggest = new ArrayList<>();
		for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
			if (info.key != null) {
				suggest.add(info.key);
			}
		}
		sugAdapter = new ArrayAdapter<String>(SearchStoreByNameActivity.this, android.R.layout.simple_dropdown_item_1line, suggest);
		etSearch.setAdapter(sugAdapter);
		sugAdapter.notifyDataSetChanged();

	}

//	private class MyPoiOverlay extends PoiOverlay {
//
//		public MyPoiOverlay(BaiduMap baiduMap) {
//			super(baiduMap);
//		}
//
//		@Override
//		public boolean onPoiClick(int index) {
//			super.onPoiClick(index);
//			PoiInfo poi = getPoiResult().getAllPoi().get(index);
//			// if (poi.hasCaterDetails) {
//			mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
//					.poiUid(poi.uid));
//			// }
//			return true;
//		}
//	}
}
