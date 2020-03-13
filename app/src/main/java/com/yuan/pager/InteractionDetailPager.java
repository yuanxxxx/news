package com.yuan.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class InteractionDetailPager extends com.yuan.base.BaseDetailPager {
    protected InteractionDetailPager(Context context) {
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
