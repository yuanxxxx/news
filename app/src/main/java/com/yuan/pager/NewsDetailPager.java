package com.yuan.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.yuan.R;
import com.yuan.activity.MainActivity;
import com.yuan.base.BaseDetailPager;
import com.yuan.bean.NewsTitleBean;
import com.yuan.pager.tabpager.TabTopicPager;
import com.yuan.ui.TouchEventViewPager;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuan
 */
public class NewsDetailPager extends BaseDetailPager {

    private final NewsTitleBean.DataBean generalInfos;
    private IndicatorViewPager indicatorViewPager;
    private List<String> topics;
    private List<TabTopicPager> tabPagers;
    private LayoutInflater layoutInflater;

    @BindView(R.id.viewpagerindicator)
    private Indicator indicator;

    @BindView(R.id.viewpager)
    private TouchEventViewPager  viewPager;
    private IndicatorViewPager.IndicatorPagerAdapter adapter;

    protected NewsDetailPager(Context context, NewsTitleBean.DataBean dataBean) {
        super(context);
        this.generalInfos = dataBean;
        layoutInflater = LayoutInflater.from(context);
        List<NewsTitleBean.DataBean.ChildrenBean> children = this.generalInfos.getChildren();
        topics = new ArrayList<>();
        tabPagers = new ArrayList<>();
        for (NewsTitleBean.DataBean.ChildrenBean child: children) {
            topics.add(child.getTitle());
        }


        for (int i = 0; i < topics.size(); i++) {
            String topic = topics.get(i);
            TabTopicPager tabTopicPager = new TabTopicPager(context, generalInfos.getChildren().get(i));
            tabTopicPager.initView();
            tabPagers.add(tabTopicPager);
        }

        adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {
            @Override
            public int getCount() {
                return topics.size();
            }
            @Override
            public View getViewForTab(int position, View convertView, ViewGroup container) {
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.tab_news, container, false);
                }
                TextView textView = (TextView) convertView;
                textView.setText(topics.get(position));
                return textView;
            }
            @Override
            public View getViewForPage(int position, View convertView, ViewGroup container) {
                return tabPagers.get(position).rootView;
            }
        };

        indicatorViewPager.setAdapter(adapter);
        indicatorViewPager.setIndicatorScrollBar(new ColorBar(context, Color.RED, 2));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    isSlidingMenuEnabled(SlidingMenu.TOUCHMODE_FULLSCREEN);
                } else {
                    isSlidingMenuEnabled(SlidingMenu.TOUCHMODE_NONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.news_detail, null);
        indicator = (ScrollIndicatorView) view.findViewById(R.id.viewpagerindicator);
        viewPager = (TouchEventViewPager) view.findViewById(R.id.viewpager);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        return view;
    }

    @Override
    public void initData() {

    }

    private void isSlidingMenuEnabled(int mode) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(mode);
    }
}
