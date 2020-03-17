package com.yuan.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.*;
import com.yuan.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yuan
 * @date 2020/3/14 15:35
 */
public class RefreshListView extends ListView {


    private TextView tv_refresh_title;

    private TextView tv_refresh_time;

    private LinearLayout ll_refresh_header;

    private LinearLayout ll_header_view;

    private ImageView iv_arrow;

    private int headerHeight;

    private ProgressBar pb_ring_footer;

    private TextView tv_footer_status;


    private float startX;

    private float startY = -1;


    public static final int DROP_DOWN = 0;

    public static final int RELEASE = 1;

    public static final int REFRESHING = 2;

    public int currentStatus;

    private Animation upAnimation;

    private Animation downAnimation;
    private ProgressBar iv_ring;
    private onRefreshListener onRefreshListener;
    private View footerView;
    private int footerHeight;
    private boolean isLoadMore;
    private View topNews;
    private int listViewOnScreenY = -1;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initAnimation();
        initFooterView(context);
    }

    private void initFooterView(Context context) {
        footerView = View.inflate(context, R.layout.load_more_footer, null);
        pb_ring_footer = (ProgressBar) footerView.findViewById(R.id.pb_ring_footer);
        tv_footer_status = (TextView) footerView.findViewById(R.id.tv_footer_status);
        footerView.measure(0, 0);
        footerHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerHeight, 0, 0);
        addFooterView(footerView);

        setOnScrollListener(new MyScrollListener());
    }

    private void initAnimation() {
        upAnimation = new RotateAnimation(0, -180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180, -360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);

    }

    private void initHeaderView(Context context) {
        ll_header_view = (LinearLayout) View.inflate(context, R.layout.refresh_header, null);
        tv_refresh_title = (TextView) ll_header_view.findViewById(R.id.tv_refresh_title);
        tv_refresh_time = (TextView) ll_header_view.findViewById(R.id.tv_refresh_time);
        ll_refresh_header = (LinearLayout) ll_header_view.findViewById(R.id.ll_refresh_header);
        iv_arrow = (ImageView) ll_header_view.findViewById(R.id.iv_arrow);
        iv_ring = (ProgressBar) ll_header_view.findViewById(R.id.iv_ring);
        ll_refresh_header.measure(0, 0);
        headerHeight = ll_refresh_header.getMeasuredHeight();
        ll_refresh_header.setPadding(0, -headerHeight, 0, 0);

        this.addHeaderView(ll_header_view);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = ev.getY();
                }

                boolean isDisplayTop = isDisplayTopNews();

                if (!isDisplayTop) {
                    // 加载更多
                    break;
                }


                if (currentStatus == REFRESHING) {
                    break;
                }

                float endY = ev.getY();
                float distanceY = endY - startY;
                if (distanceY > 0) {
                    int paddingTop = (int) (distanceY - headerHeight);
                    ll_refresh_header.setPadding(0, paddingTop, 0, 0);

                    if (paddingTop < 0 && currentStatus != DROP_DOWN) {
                        currentStatus = DROP_DOWN;
                        changeStateView();
                    }

                    if (paddingTop > 0 && currentStatus != RELEASE) {
                        currentStatus = RELEASE;
                        changeStateView();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (currentStatus == DROP_DOWN) {
                    ll_refresh_header.setPadding(0, -headerHeight, 0, 0);
                } else if (currentStatus == RELEASE) {
                    currentStatus = REFRESHING;
                    changeStateView();
                    ll_refresh_header.setPadding(0, 0, 0, 0);


                    // 回调接口
                    if (onRefreshListener != null) {
                        onRefreshListener.onDropDownRefresh();
                    }

                }
                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    private boolean isDisplayTopNews() {
        int[] locations = new int[2];
        if (listViewOnScreenY == -1) {
            getLocationOnScreen(locations);
            listViewOnScreenY = locations[1];
        }

        topNews.getLocationOnScreen(locations);
        int topNewsOnScreenY = locations[1];

        return listViewOnScreenY <= topNewsOnScreenY;
    }

    private void changeStateView() {
        switch (currentStatus) {
            case DROP_DOWN:
                iv_arrow.startAnimation(downAnimation);
                tv_refresh_title.setText("下拉刷新");
                break;
            case RELEASE:
                iv_arrow.startAnimation(upAnimation);
                tv_refresh_title.setText("松手刷新");
                break;
            case REFRESHING:
                tv_refresh_title.setText("正在刷新");
                iv_arrow.clearAnimation();
                iv_ring.setVisibility(VISIBLE);
                iv_arrow.setVisibility(GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 当刷新成功或失败的时候回调该方法
     * @param success 请求数据是否成功
     */
    public void onRefreshFinish(boolean success) {
        if (isLoadMore) {
            isLoadMore = false;
            footerView.setPadding(0, -footerHeight, 0, 0);
        } else {
            tv_refresh_title.setText("下拉刷新");
            currentStatus = DROP_DOWN;
            iv_ring.setVisibility(INVISIBLE);
            iv_arrow.setVisibility(VISIBLE);

            ll_refresh_header.setPadding(0, -headerHeight, 0, 0);

            if (success) {
                tv_refresh_time.setText(getCurrentTime());
            }
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public void addHeaderTopNewsView(View topnews) {
        topNews = topnews;

        if (topNews != null) {
            ll_header_view.addView(topnews);
        }
    }


    /**
     * 下拉刷新接口
     */
    public interface onRefreshListener {

        public void onDropDownRefresh();

        public void onLoadMore();

    }

    public void setOnRefreshListener (onRefreshListener listener) {
        this.onRefreshListener = listener;
    }


    class MyScrollListener implements OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // 静止或惯性滚动时 并且最后一条可见
            if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
                if (getLastVisiblePosition() >= getCount() - 1) {

                    // 显示更多布局
                    footerView.setPadding(8, 8, 8, 8);

                    // 状态改变
                    isLoadMore = true;

                    // 回调接口
                    if (onRefreshListener != null) {
                        onRefreshListener.onLoadMore();
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }
}
