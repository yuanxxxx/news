package com.yuan.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TopicDetailPager extends com.yuan.base.BaseDetailPager {
    protected TopicDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        return new TextView(context);
    }

    @Override
    public void initData() {

    }
}
