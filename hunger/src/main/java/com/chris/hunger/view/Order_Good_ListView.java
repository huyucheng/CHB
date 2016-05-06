package com.chris.hunger.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by dell on 2016/3/9.
 */
public class Order_Good_ListView extends ListView {
    public Order_Good_ListView(Context context) {
        super(context);
    }

    public Order_Good_ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Order_Good_ListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//
//        return false;
//    }
}
