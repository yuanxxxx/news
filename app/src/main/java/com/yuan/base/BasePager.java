package com.yuan.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.yuan.R;

/**
 * @author yuan
 */
public abstract class BasePager {

    /**
     * 上下文
     */
    public Context context;

    /**
     * 对应的View
     */
    public View rootView;

    /**
     * 顶端标题的相对布局
     */
    @BindView(R.id.rl_title)
    public RelativeLayout rl_title;

    /**
     * 标题
     */
    @BindView(R.id.tv_title)
    public TextView tv_title;

    /**
     * 左侧滑栏按钮
     */
    @BindView(R.id.ib_leftmenu)
    public ImageButton ib_leftmenu;

    /**
     * 内容帧布局
     */
    @BindView(R.id.fl_content)
    public FrameLayout fl_content;

    public BasePager(Context context) {
        this.context = context;
    }

    public void initView() {
        View view = View.inflate(context, R.layout.base_pager, null);

        // 绑定控件
        rl_title = (RelativeLayout) view.findViewById(R.id.rl_title);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ib_leftmenu = (ImageButton) view.findViewById(R.id.ib_leftmenu);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);

        // 绑定view
        rootView = view;
    }

    /**
     * 实现数据绑定
     */
    public abstract void initData();


}
