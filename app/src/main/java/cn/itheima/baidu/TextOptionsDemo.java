package cn.itheima.baidu;

import com.baidu.mapapi.map.TextOptions;

import android.graphics.Typeface;
import android.os.Bundle;

/**
 * ���ָ�����
 * @author h
 *
 */
public class TextOptionsDemo extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		draw();
	}

	private void draw() {
		TextOptions textOptions = new TextOptions();
		textOptions.fontColor(0x60FF0000)
		.text("���������") // ��������
		.position(hmPos) // λ��
		.fontSize(24)// �����С
		.typeface(Typeface.SERIF);// ����
//		.rotate(30);// ��ת
		baiduMap.addOverlay(textOptions);
	}
}
