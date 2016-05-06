package cn.itheima.baidu;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

public class LayerDemo extends Activity {
	private BaiduMap baiduMap;
	private MapView mapview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initManager();

		setContentView(R.layout.common);

		init();
	}

	private void init() {
		mapview = (MapView) findViewById(R.id.mapview);
		baiduMap = mapview.getMap();

		baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
	}

	private void initManager() {
		SDKInitializer.initialize(getApplicationContext());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 // ��ͼ ��ͨͼ ����ͼ
		switch (keyCode) {
		case KeyEvent.KEYCODE_1:
			// ��ͼ
			// ���õ�ͼ���� MAP_TYPE_NORMAL ��ͨͼ�� MAP_TYPE_SATELLITE ����ͼ
			baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			baiduMap.setTrafficEnabled(false);
			break;
		case KeyEvent.KEYCODE_2:
			// ����ͼ
			baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			baiduMap.setTrafficEnabled(false);
			break;
		case KeyEvent.KEYCODE_3:
			// ��ͨͼ
			// �Ƿ�򿪽�ͨͼ��
			baiduMap.setTrafficEnabled(true);
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		mapview.onDestroy();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		mapview.onResume();
		super.onResume();
	}

	@Override
	protected void onPause() {
		mapview.onPause();
		super.onPause();
	}
}
