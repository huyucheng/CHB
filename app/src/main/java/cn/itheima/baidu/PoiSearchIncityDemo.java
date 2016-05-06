package cn.itheima.baidu;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 城市内搜索
 * 
 * @author h
 * 
 */
public class PoiSearchIncityDemo extends BaseActivity {
	private PoiSearch poiSearch;
	private int currentPageIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		poiSearch = PoiSearch.newInstance();
		poiSearch.setOnGetPoiSearchResultListener(new MyListener());
		search();
	}

	private void search() {
		PoiCitySearchOption cityOption = new PoiCitySearchOption();
		cityOption.city("北京");
		cityOption.keyword("加油站");
		cityOption.pageNum(currentPageIndex);
		poiSearch.searchInCity(cityOption);
	}

	class MyListener implements OnGetPoiSearchResultListener {

		@Override
		public void onGetPoiDetailResult(PoiDetailResult result) {

		}

		@Override
		public void onGetPoiResult(PoiResult result) {
			if (result == null
					|| SearchResult.ERRORNO.RESULT_NOT_FOUND == result.error) {
				Toast.makeText(getApplicationContext(), "未搜索到结果", 0).show();
				return;
			}
			String text = "共" + result.getTotalPageNum() + "页，共"
					+ result.getTotalPoiNum() + "条，当前第"
					+ result.getCurrentPageNum() + "页，当前页"
					+ result.getCurrentPageCapacity() + "条";
			
			Toast.makeText(getApplicationContext(), text, 1).show();
			baiduMap.clear();// 清空地图所有的 Overlay 覆盖物以及 InfoWindow
			PoiOverlay overlay = new MyPoiOverlay(baiduMap);// 搜索poi的覆盖物
			baiduMap.setOnMarkerClickListener(overlay);// 把事件分发给overlay，overlay才能处理点击事件
			overlay.setData(result);// 设置结果
			
			overlay.addToMap();// 把搜索的结果添加到地图中
			overlay.zoomToSpan();// 缩放地图，使所有Overlay都在合适的视野内 注：
									// 该方法只对Marker类型的overlay有效
		}

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_1){
			currentPageIndex++;
			search();
		}
		return super.onKeyDown(keyCode, event);
	}

	class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap arg0) {
			super(arg0);
		}

		@Override
		public boolean onPoiClick(int index) {
			PoiResult poiResult = getPoiResult();
			PoiInfo poiInfo = poiResult.getAllPoi().get(index);// 得到点击的那个poi信息
			String text = poiInfo.name + "," + poiInfo.address;
			Toast.makeText(getApplicationContext(), text, 0).show();
			return super.onPoiClick(index);
		}

	}
}
