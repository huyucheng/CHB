package cn.itheima.baidu;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

/**
 * ����
 * 
 * @author h
 * 
 */
public class HelloWorld extends Activity {
	private static final String TAG = "HelloWorld";
	private MyBaduSdkReceiver baduSdkReceiver;
	private BaiduMap baiduMap;
	private MapView mapview;
	private double latitude = 40.050966;// γ��
	private double longitude = 116.303128;// ����
	private LatLng hmPos = new LatLng(latitude, longitude);// ����

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
		// BaiduMap: ��������ĳһ��MapView �� ��ת���ƶ������ţ��¼�����

		// ������ͼ״̬��Ҫ�����ı仯 ʹ�ù�����MapStatusUpdateFactory����
		MapStatusUpdate mapstatusUpdate = MapStatusUpdateFactory.zoomTo(19);// Ĭ�ϵļ���12
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

	private void initManager() {
		SDKInitializer.initialize(getApplicationContext()); // ���ܴ���Activity��������ȫ��Context
		baduSdkReceiver = new MyBaduSdkReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);// ע���������
		filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR); // ע��keyУ����
		registerReceiver(baduSdkReceiver, filter);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ��ת���ƶ�������
		switch (keyCode) {
		case KeyEvent.KEYCODE_1:
			// �Ŵ�
			// �Ŵ��ͼ���ż��� ÿ�ηŴ�һ������
			MapStatusUpdate zoomInStatus = MapStatusUpdateFactory.zoomIn();
			baiduMap.setMapStatus(zoomInStatus);
			break;
		case KeyEvent.KEYCODE_2:
			// ��С
			// ��С��ͼ���ż��� ÿ����Сһ������
			MapStatusUpdate zooOutStatus = MapStatusUpdateFactory.zoomOut();
			baiduMap.setMapStatus(zooOutStatus);
			break;
		case KeyEvent.KEYCODE_3:
			// ��һ����Ϊ���� ��ת
			MapStatus mapStatus = baiduMap.getMapStatus();// ��ȡ��ͼ�ĵ�ǰ״̬
			float rotate = mapStatus.rotate;
			Log.d(TAG, "rotate:" + rotate);
			// ��ת ��Χ0~360
			MapStatus rotateStatus = new  MapStatus.Builder().rotate(rotate+30).build();
			// �����µ�MapStatus
			MapStatusUpdate rotateStatusUpdate = MapStatusUpdateFactory.newMapStatus(rotateStatus);
			baiduMap.setMapStatus(rotateStatusUpdate);
			
			break;
		case KeyEvent.KEYCODE_4:
			// ��һ��ֱ�� Ϊ�� ��ת Overlooking ����
			MapStatus mapStatusOver = baiduMap.getMapStatus();// ��ȡ��ͼ�ĵ�ǰ״̬
			float overlook = mapStatusOver.overlook;
			Log.d(TAG, "overlook:" + overlook);
			// 0~45
			MapStatus overStatus = new MapStatus.Builder().overlook(overlook-5).build();
			MapStatusUpdate overStatusUpdate = MapStatusUpdateFactory.newMapStatus(overStatus);
			baiduMap.setMapStatus(overStatusUpdate);
			break;
		case KeyEvent.KEYCODE_5:
			// �ƶ�
			MapStatusUpdate moveStatusUpdate = MapStatusUpdateFactory.newLatLng(new LatLng(40.065796,116.349868));
			// �������ĸ��µ�ͼ״̬ ��ʱ300����
			baiduMap.animateMapStatus(moveStatusUpdate);
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(baduSdkReceiver);
		mapview.onDestroy();
		super.onDestroy();
	}

	class MyBaduSdkReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String result = intent.getAction();
			if (result
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				// �������
				Toast.makeText(getApplicationContext(), "������", 0).show();
			} else if (result
					.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				// keyУ��ʧ��
				Toast.makeText(getApplicationContext(), "У��ʧ��", 0).show();
			}
		}

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
