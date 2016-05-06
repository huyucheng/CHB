package cn.itheima.baidu;

import com.baidu.mapapi.SDKInitializer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private static String[] objects = new String[] { "hello world", "ͼ��",
			"Բ�θ�����", "չʾ����", "marker������", "���η�Χ������", "Բ������", "ȫ������", "�ݳ�·��",
			"����·��", "��������", "�ҵ�λ��" };
	private static Class[] clazzs = new Class[] { HelloWorld.class,
			LayerDemo.class, CircleOptionsDemo.class, TextOptionsDemo.class,
			MarkerOptionsDemo.class, PoiSearchInBoundsDemo.class,
			PoiSearchNearByDemo.class, PoiSearchIncityDemo.class,
			DrivingRouteOverlayDemo.class, WalkingRouteOverlayDemo.class,
			TransitRouteOverlayDemo.class ,LocationDemo.class};
	private ListView list;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.list);

		adapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.item, objects);

		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						clazzs[position]);
				startActivity(intent);
			}
		});

		SDKInitializer.initialize(getApplicationContext()); // ���ܴ���Activity��������ȫ��Context
	}

}
