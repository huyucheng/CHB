package cn.itheima.baidu;

import com.baidu.mapapi.map.TextOptions;

import android.graphics.Typeface;
import android.os.Bundle;

/**
 * 文字覆盖物
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
		.text("黑马程序媛") // 文字内容
		.position(hmPos) // 位置
		.fontSize(24)// 字体大小
		.typeface(Typeface.SERIF);// 字体
//		.rotate(30);// 旋转
		baiduMap.addOverlay(textOptions);
	}
}
