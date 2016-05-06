package cn.itheima.baidu;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

public class BaseActivity extends Activity {
	protected BaiduMap baiduMap;
	protected MapView mapview;
	protected double latitude = 40.050966;// γ��
	protected double longitude = 116.303128;// ����
	protected LatLng hmPos = new LatLng(latitude, longitude);// ����
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initManager();

		setContentView(R.layout.common);

		init();
	}
	private void initManager() {
//		SDKInitializer.initialize(getApplicationContext()); // ���ܴ���Activity��������ȫ��Context
	}

	private void init() {
		// ���õ�ͼ����V2.X 3-19 V1.X 3-18��
		// �� �޸����ļ��ĸ�ʽ �Ż��˿ռ��ʹ�ã����� 110M 15M��
		// �� �����˼��� 3DЧ����18 19��

		mapview = (MapView) findViewById(R.id.mapview);

		baiduMap = mapview.getMap();
		// BaiduMap: ��������ĳһ��MapView �� ��ת���ƶ������ţ��¼�����

		// ������ͼ״̬��Ҫ�����ı仯 ʹ�ù�����MapStatusUpdateFactory����
		MapStatusUpdate mapstatusUpdate = MapStatusUpdateFactory.zoomTo(15);// Ĭ�ϵļ���12
		// �������ż���
		baiduMap.setMapStatus(mapstatusUpdate);

		// LatLng latlng = new LatLng(arg0, arg1);// ���� ��γ�� ����1 γ�� ����2 ����
		MapStatusUpdate mapstatusUpdatePoint = MapStatusUpdateFactory
				.newLatLng(hmPos);
		// �������ĵ� Ĭ�����찲��
		baiduMap.setMapStatus(mapstatusUpdatePoint);

		// mapview.showZoomControls(false);// Ĭ����true ��ʾ���Ű�ť
		//
		// mapview.showScaleControl(false);// Ĭ����true ��ʾ���
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
