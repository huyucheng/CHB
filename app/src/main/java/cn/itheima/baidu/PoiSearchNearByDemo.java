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
 * �ܱ�����
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
		nearbyOption.location(hmPos);// �������ĵ�
		nearbyOption.radius(1000);// ���ð뾶 ��λ����
		nearbyOption.keyword("����վ");// �ؼ���
		poiSearch.searchNearby(nearbyOption);
	}
	
	class MyListener implements OnGetPoiSearchResultListener{

		@Override
		public void onGetPoiDetailResult(PoiDetailResult result) {
			if(result==null||SearchResult.ERRORNO.RESULT_NOT_FOUND==result.error){
				Toast.makeText(getApplicationContext(), "δ���������", 0).show();
				return;
			}
			
			String text = result.getAddress()+ "::" + result.getCommentNum() + result.getEnvironmentRating();
			Toast.makeText(getApplicationContext(), text, 0).show();
		}

		@Override
		public void onGetPoiResult(PoiResult result) {
			if(result==null||SearchResult.ERRORNO.RESULT_NOT_FOUND==result.error){
				Toast.makeText(getApplicationContext(), "δ���������", 0).show();
				return;
			}
			PoiOverlay overlay = new MyPoiOverlay(baiduMap);// ����poi�ĸ�����
			baiduMap.setOnMarkerClickListener(overlay);// ���¼��ַ���overlay��overlay���ܴ������¼�
			overlay.setData(result);// ���ý��
			overlay.addToMap();// �������Ľ����ӵ���ͼ��
			overlay.zoomToSpan();// ���ŵ�ͼ��ʹ����Overlay���ں��ʵ���Ұ�� ע�� �÷���ֻ��Marker���͵�overlay��Ч
		}
		
	}
	class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap arg0) {
			super(arg0);
		}
		
		@Override
		public boolean onPoiClick(int index) {
			PoiResult poiResult = getPoiResult();
			PoiInfo poiInfo = poiResult.getAllPoi().get(index);// �õ�������Ǹ�poi��Ϣ
			String text = poiInfo.name + "," + poiInfo.address;
			Toast.makeText(getApplicationContext(), text, 0).show();
			
			PoiDetailSearchOption detailOption = new PoiDetailSearchOption();
			detailOption.poiUid(poiInfo.uid);// ����poi��uid
			poiSearch.searchPoiDetail(detailOption);
			return super.onPoiClick(index);
		}
		
	}
}
