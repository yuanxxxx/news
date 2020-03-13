package com.yuan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.yuan.R;
import com.yuan.bean.NewsTitleBean;

import java.util.List;

/**
 * @author yuan
 */
public class LeftMenuLvAdapter extends BaseAdapter {

    private final List<NewsTitleBean.DataBean> textViews;
    private final Context context;

    public LeftMenuLvAdapter(List<NewsTitleBean.DataBean> views, Context context) {
        this.textViews = views;
        this.context = context;
    }

    @Override
    public int getCount() {
        return textViews.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") TextView tv = (TextView) View.inflate(context, R.layout.left_menu_textview, null);
        tv.setText(textViews.get(position).getTitle());
        return tv;
    }
}
