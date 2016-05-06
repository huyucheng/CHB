package cn.itheima.baidu;

import android.os.Bundle;

import com.baidu.mapapi.map.CircleOptions;

/**
 * ����Բ
 * @author h
 *
 */
public class CircleOptionsDemo extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		draw();
	}

	private void draw() {

		CircleOptions circleOptions = new CircleOptions();
		// �� ���Լ���������
		circleOptions.center(hmPos)// Բ��
		.radius(1000)// �뾶 ��λ����
		.fillColor(0x60FF0000);// ͸���� �� �� ��
//		.stroke(new Stroke(10, 0x600FF000));// �߿� ����1 �߿� ����2 ��ɫ
		// �� �Ѹ�������ӵ���ͼ��
		baiduMap.addOverlay(circleOptions);
	}
}
