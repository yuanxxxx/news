package com.yuan.base;

import android.content.Context;
import android.view.View;

/**
 * @author xieyu
 */
public abstract class BaseDetailPager {


    public final Context context;

    /**
     * 存储pager代表的view
     */
    public View rootView;

    protected BaseDetailPager(Context context) {
        this.context = context;
        rootView = initView();
    }


    public abstract View initView();

    public abstract void initData();
}
