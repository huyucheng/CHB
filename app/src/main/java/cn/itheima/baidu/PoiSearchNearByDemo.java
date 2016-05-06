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
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import android.os.Bundle;
import android.widget.Toast;

/**
 * 周边搜索
 * @author h
 *
 */
public class PoiSearchNearByDemo extends BaseActivity {
	private PoiSearch poiSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		search();
	}

	private void search() {
		poiSearch = PoiSearch.newInstance();
		poiSearch.setOnGetPoiSearchResultListener(new MyListener());
		
		PoiNearbySearchOption nearbyOption = new PoiNearbySearchOption();
		nearbyOption.location(hmPos);// 设置中心点
		nearbyOption.radius(1000);// 设置半径 单位是米
		nearbyOption.keyword("加油站");// 关键字
		poiSearch.searchNearby(nearbyOption);
	}
	
	class MyListener implements OnGetPoiSearchResultListener{

		@Override
		public void onGetPoiDetailResult(PoiDetailResult result) {
			if(result==null||SearchResult.ERRORNO.RESULT_NOT_FOUND==result.error){
				Toast.makeText(getApplicationContext(), "未搜索到结果", 0).show();
				return;
			}
			
			String text = result.getAddress()+ "::" + result.getCommentNum() + result.getEnvironmentRating();
			Toast.makeText(getApplicationContext(), text, 0).show();
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
			
			PoiDetailSearchOption detailOption = new PoiDetailSearchOption();
			detailOption.poiUid(poiInfo.uid);// 设置poi的uid
			poiSearch.searchPoiDetail(detailOption);
			return super.onPoiClick(index);
		}
		
	}
}
