package com.yuan.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import com.yuan.base.BasePager;

public class GovPager extends BasePager {

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        // 设置标题
        tv_title.setText("政要");

        // 绑定数据
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        fl_content.addView(textView);
    }

    public GovPager(Context context) {
        super(context);
        initView();
    }
}
