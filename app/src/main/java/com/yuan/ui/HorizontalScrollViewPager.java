package com.yuan.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/**
 * @author yuan
 * @date 2020/3/11 14:37
 */
public class HorizontalScrollViewPager extends ViewPager {
    public HorizontalScrollViewPager(Context context) {
        super(context);
    }

    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 起始坐标
     */
    private float startX;
    private float startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //请求父层视图不拦截，当前控件的事件
                getParent().requestDisallowInterceptTouchEvent(true);
                //1.记录起始坐标
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //2.来到新的坐标
                float endX = ev.getX();
                float endY = ev.getY();
                //3.计算偏移量
                float distanceX = endX - startX;
                float distanceY = endY - startY;
                //4.判断滑动方向
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    //水平方向滑动

                    if (getCurrentItem() == 0 && distanceX > 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }

//                    2.2，当滑动到ViewPager的最后一个页面，并且是从右到左滑动
                    else if ((getCurrentItem() == (getAdapter().getCount() - 1)) && distanceX < 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
//                    2.3，其他,中间部分
                    else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else {
                    //竖直方向滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
