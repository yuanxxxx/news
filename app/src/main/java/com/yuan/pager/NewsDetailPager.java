package com.yuan.pager;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.yuan.R;
import com.yuan.base.BaseDetailPager;
import com.yuan.bean.NewsTitleBean;
import com.yuan.pager.tabpager.TabTopicPager;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailPager extends BaseDetailPager {

    private final NewsTitleBean.DataBean generalInfos;
    private IndicatorViewPager indicatorViewPager;
    private List<String> topics;
    private List<TabTopicPager> tabPagers;
    private LayoutInflater layoutInflater;

    @BindView(R.id.viewpagerindicator)
    private Indicator indicator;

    @BindView(R.id.viewpager)
    private ViewPager viewPager;
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
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.news_detail, null);
        indicator = (ScrollIndicatorView) view.findViewById(R.id.viewpagerindicator);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        return view;
    }

    @Override
    public void initData() {

    }
}
