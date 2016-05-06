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
 * ��Χ������
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
		.include(new LatLng(40.049233, 116.302675))// �����ĵ�
		.include(new LatLng(40.050645, 116.303695))// ���ϵĵ�
		.build();
		boundOption.bound(latlngBounds);// ����������Χ
		boundOption.keyword("����վ");// �����ؼ���
		poiSearch.searchInBound(boundOption);// ֻ�ǰѷ�Χ����������poi����Ϊ ��ͼ�����ģ�ͬʱ����������Χ���poi
	}
	
	class MyListener implements OnGetPoiSearchResultListener{

		@Override
		public void onGetPoiDetailResult(PoiDetailResult result) {
			
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
			return super.onPoiClick(index);
		}
		
	}
}
