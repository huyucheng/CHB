package cn.itheima.baidu;

import android.os.Bundle;
import android.view.KeyEvent;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

/**
 * ��λ
 * 
 * @author h
 * 
 */
public class LocationDemo extends BaseActivity {
	public LocationClient mLocationClient;
	public BDLocationListener myListener;
	private BitmapDescriptor geo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		lacate();
	}

	private void lacate() {
		mLocationClient = new LocationClient(getApplicationContext());
		myListener = new MyListener();
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(5000);// ���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);// ���صĶ�λ���������ַ��Ϣ
		option.setNeedDeviceDirect(true);// ���صĶ�λ��������ֻ���ͷ�ķ���
		mLocationClient.setLocOption(option);
		geo = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_geo);
		MyLocationConfiguration configuration = new MyLocationConfiguration(
				MyLocationConfiguration.LocationMode.FOLLOWING, true, geo);
		baiduMap.setMyLocationConfigeration(configuration);// ���ö�λ��ʾ��ģʽ
		baiduMap.setMyLocationEnabled(true);// �򿪶�λͼ��
	}
	
	@Override
	protected void onStart() {
		mLocationClient.start();
		super.onStart();
	}

	@Override
	protected void onPause() {
		mLocationClient.stop();
		super.onPause();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_1:
			// ����
			baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
					MyLocationConfiguration.LocationMode.NORMAL, true, geo));// ���ö�λ��ʾ��ģʽ
			break;
		case KeyEvent.KEYCODE_2:
			// ����
			baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
					MyLocationConfiguration.LocationMode.COMPASS, true, geo));// ���ö�λ��ʾ��ģʽ
			break;
		case KeyEvent.KEYCODE_3:
			// ����
			baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
					MyLocationConfiguration.LocationMode.FOLLOWING, true, geo));// ���ö�λ��ʾ��ģʽ
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	class MyListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation result) {
			if (result != null) {
				MyLocationData data = new MyLocationData.Builder()
						.latitude(result.getLatitude())
						.longitude(result.getLongitude()).build();
				baiduMap.setMyLocationData(data);
			}
		}

	}
}
