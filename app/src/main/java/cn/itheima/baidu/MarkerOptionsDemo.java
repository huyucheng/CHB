package cn.itheima.baidu;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * mark������
 * 
 * @author h
 * 
 */
public class MarkerOptionsDemo extends BaseActivity {
	private View pop;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		draw();
		
		initPop();
		// ���ĳһ��Marker ���Դ��ϵ���pop
		
		
	}

	private void initPop() {
		// ����pop ��ӵ�mapview ����Ϊ����
		
		pop = View.inflate(getApplicationContext(), R.layout.pop, null);
		LayoutParams params = new MapViewLayoutParams.Builder()
		.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)// ���վ�γ������λ��
		.position(hmPos)// ���ܴ�null ����ΪmapModeʱ ��������position
		.width(MapViewLayoutParams.WRAP_CONTENT)
		.height(MapViewLayoutParams.WRAP_CONTENT)
		.build();
		mapview.addView(pop, params);
		pop.setVisibility(View.INVISIBLE);
		title = (TextView) pop.findViewById(R.id.title);
	}

	private void draw() {

		BitmapDescriptor bitmapDes = BitmapDescriptorFactory
				.fromResource(R.drawable.eat_icon);

		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(hmPos)// ����λ��
				.icon(bitmapDes)// ����ͼ��
				.draggable(true)// �����Ƿ������ק Ĭ���Ƿ�
				.title("����");// ���ñ���
		baiduMap.addOverlay(markerOptions);
		
		markerOptions = new MarkerOptions().title("��")
				.position(new LatLng(latitude + 0.001, longitude))
				.icon(bitmapDes);
		baiduMap.addOverlay(markerOptions);

		ArrayList<BitmapDescriptor> bitmaps = new ArrayList<BitmapDescriptor>();
		bitmaps.add(bitmapDes);
		bitmaps.add(BitmapDescriptorFactory.fromResource(R.drawable.icon_geo));
		markerOptions = new MarkerOptions().title("��")
				.position(new LatLng(latitude, longitude + 0.001))
				.icons(bitmaps)// ��ʾ���ͼƬ�����л� ֡����
				.period(10);// ���ö���֡ˢ��һ��ͼƬ��Դ��Marker�����ļ��ʱ�䣬ֵԽС����Խ��
		baiduMap.addOverlay(markerOptions);
		
		markerOptions = new MarkerOptions().title("������")
				.position(new LatLng(latitude - 0.001, longitude - 0.001))
				.icon(bitmapDes);
		baiduMap.addOverlay(markerOptions);

		baiduMap.setOnMarkerClickListener(new MyListener());
	}
	
	class MyListener implements OnMarkerClickListener{

		@Override
		public boolean onMarkerClick(Marker result) {
			// �����ʱ ����pop��λ�� ����Ϊ��ʾ
			LayoutParams params = new MapViewLayoutParams.Builder()
			.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)// ���վ�γ������λ��
			.position(result.getPosition())// ���ܴ�null
			.width(MapViewLayoutParams.WRAP_CONTENT)
			.height(MapViewLayoutParams.WRAP_CONTENT)
			.yOffset(-5)// ����position������ ��������ֵ �����Ǹ�ֵ
			.build();
			mapview.updateViewLayout(pop, params);
			pop.setVisibility(View.VISIBLE);
			title.setText(result.getTitle());
			
			return true;
		}
		
	}
}
