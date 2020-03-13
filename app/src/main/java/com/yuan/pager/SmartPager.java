package com.yuan.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import com.yuan.base.BasePager;

public class SmartPager extends BasePager {

    public SmartPager(Context context) {
        super(context);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        // 设置标题
        tv_title.setText("智慧");

        // 绑定数据
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        fl_content.addView(textView);
    }

}
