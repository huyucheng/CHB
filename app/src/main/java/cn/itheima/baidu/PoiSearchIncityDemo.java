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
 * ����������
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
		cityOption.city("����");
		cityOption.keyword("����վ");
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
				Toast.makeText(getApplicationContext(), "δ���������", 0).show();
				return;
			}
			String text = "��" + result.getTotalPageNum() + "ҳ����"
					+ result.getTotalPoiNum() + "������ǰ��"
					+ result.getCurrentPageNum() + "ҳ����ǰҳ"
					+ result.getCurrentPageCapacity() + "��";
			
			Toast.makeText(getApplicationContext(), text, 1).show();
			baiduMap.clear();// ��յ�ͼ���е� Overlay �������Լ� InfoWindow
			PoiOverlay overlay = new MyPoiOverlay(baiduMap);// ����poi�ĸ�����
			baiduMap.setOnMarkerClickListener(overlay);// ���¼��ַ���overlay��overlay���ܴ������¼�
			overlay.setData(result);// ���ý��
			
			overlay.addToMap();// �������Ľ����ӵ���ͼ��
			overlay.zoomToSpan();// ���ŵ�ͼ��ʹ����Overlay���ں��ʵ���Ұ�� ע��
									// �÷���ֻ��Marker���͵�overlay��Ч
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
			PoiInfo poiInfo = poiResult.getAllPoi().get(index);// �õ�������Ǹ�poi��Ϣ
			String text = poiInfo.name + "," + poiInfo.address;
			Toast.makeText(getApplicationContext(), text, 0).show();
			return super.onPoiClick(index);
		}

	}
}
