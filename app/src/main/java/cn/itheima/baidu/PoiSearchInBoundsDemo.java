package cn.itheima.baidu;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import android.os.Bundle;
import android.widget.Toast;

/**
 * 范围内搜索
 * @author h
 *
 */
public class PoiSearchInBoundsDemo extends BaseActivity {
	private PoiSearch poiSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		search();
	}

	private void search() {
		poiSearch = PoiSearch.newInstance();
		poiSearch.setOnGetPoiSearchResultListener(new MyListener());
		
		PoiBoundSearchOption boundOption = new PoiBoundSearchOption();
		LatLngBounds latlngBounds = new LatLngBounds.Builder()
		.include(new LatLng(40.049233, 116.302675))// 东北的点
		.include(new LatLng(40.050645, 116.303695))// 西南的点
		.build();
		boundOption.bound(latlngBounds);// 设置搜索范围
		boundOption.keyword("加油站");// 搜索关键字
		poiSearch.searchInBound(boundOption);// 只是把范围能搜索到的poi设置为 地图的中心，同时会搜索到范围外的poi
	}
	
	class MyListener implements OnGetPoiSearchResultListener{

		@Override
		public void onGetPoiDetailResult(PoiDetailResult result) {
			
		}

		@Override
		public void onGetPoiResult(PoiResult result) {
			if(result==null||SearchResult.ERRORNO.RESULT_NOT_FOUND==result.error){
				Toast.makeText(getApplicationContext(), "未搜索到结果", 0).show();
				return;
			}
			PoiOverlay overlay = new MyPoiOverlay(baiduMap);// 搜索poi的覆盖物
			baiduMap.setOnMarkerClickListener(overlay);// 把事件分发给overlay，overlay才能处理点击事件
			overlay.setData(result);// 设置结果
			overlay.addToMap();// 把搜索的结果添加到地图中
			overlay.zoomToSpan();// 缩放地图，使所有Overlay都在合适的视野内 注： 该方法只对Marker类型的overlay有效
		}
		
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
