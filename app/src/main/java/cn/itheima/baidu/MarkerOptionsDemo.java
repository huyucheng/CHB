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
 * mark覆盖物
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
		// 点击某一个Marker 在脑袋上弹出pop
		
		
	}

	private void initPop() {
		// 加载pop 添加到mapview 设置为隐藏
		
		pop = View.inflate(getApplicationContext(), R.layout.pop, null);
		LayoutParams params = new MapViewLayoutParams.Builder()
		.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)// 按照经纬度设置位置
		.position(hmPos)// 不能传null 设置为mapMode时 必须设置position
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
		markerOptions.position(hmPos)// 设置位置
				.icon(bitmapDes)// 设置图标
				.draggable(true)// 设置是否可以拖拽 默认是否
				.title("黑马");// 设置标题
		baiduMap.addOverlay(markerOptions);
		
		markerOptions = new MarkerOptions().title("向北")
				.position(new LatLng(latitude + 0.001, longitude))
				.icon(bitmapDes);
		baiduMap.addOverlay(markerOptions);

		ArrayList<BitmapDescriptor> bitmaps = new ArrayList<BitmapDescriptor>();
		bitmaps.add(bitmapDes);
		bitmaps.add(BitmapDescriptorFactory.fromResource(R.drawable.icon_geo));
		markerOptions = new MarkerOptions().title("向东")
				.position(new LatLng(latitude, longitude + 0.001))
				.icons(bitmaps)// 显示多个图片来回切换 帧动画
				.period(10);// 设置多少帧刷新一次图片资源，Marker动画的间隔时间，值越小动画越快
		baiduMap.addOverlay(markerOptions);
		
		markerOptions = new MarkerOptions().title("向西南")
				.position(new LatLng(latitude - 0.001, longitude - 0.001))
				.icon(bitmapDes);
		baiduMap.addOverlay(markerOptions);

		baiduMap.setOnMarkerClickListener(new MyListener());
	}
	
	class MyListener implements OnMarkerClickListener{

		@Override
		public boolean onMarkerClick(Marker result) {
			// 当点击时 更新pop的位置 设置为显示
			LayoutParams params = new MapViewLayoutParams.Builder()
			.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)// 按照经纬度设置位置
			.position(result.getPosition())// 不能传null
			.width(MapViewLayoutParams.WRAP_CONTENT)
			.height(MapViewLayoutParams.WRAP_CONTENT)
			.yOffset(-5)// 距离position的像素 向下是正值 向上是负值
			.build();
			mapview.updateViewLayout(pop, params);
			pop.setVisibility(View.VISIBLE);
			title.setText(result.getTitle());
			
			return true;
		}
		
	}
}
