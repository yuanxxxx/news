package com.yuan.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yuan.R;

import java.util.zip.Inflater;

/**
 * @author yuan
 * @date 2020/3/14 15:35
 */
public class RefreshListView extends ListView {

    private TextView tv_refresh_title;

    private TextView tv_refresh_time;

    private LinearLayout ll_refresh_header;

    private LinearLayout ll_header_view;
    private int headerHeight;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
    }

    private void initHeaderView(Context context) {
        View ll_header_view = View.inflate(context, R.layout.refresh_header, null);
        tv_refresh_title = (TextView) ll_header_view.findViewById(R.id.tv_refresh_title);
        tv_refresh_time = (TextView) ll_header_view.findViewById(R.id.tv_refresh_time);
        ll_refresh_header = (LinearLayout) ll_header_view.findViewById(R.id.ll_refresh_header);

        ll_header_view.measure(0, 0);
        headerHeight = ll_header_view.getHeight();
        ll_header_view.setPadding(0, -headerHeight, 0, 0);

        this.addHeaderView(ll_header_view);
    }



}
