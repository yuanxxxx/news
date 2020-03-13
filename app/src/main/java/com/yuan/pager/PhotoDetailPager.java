package com.yuan.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class PhotoDetailPager extends com.yuan.base.BaseDetailPager {
    protected PhotoDetailPager(Context context) {
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
