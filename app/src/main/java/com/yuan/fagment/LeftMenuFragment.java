package com.yuan.fagment;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.ColorRes;
import com.yuan.activity.MainActivity;
import com.yuan.adapter.LeftMenuLvAdapter;
import com.yuan.base.BaseFragment;
import com.yuan.bean.NewsTitleBean;
import com.yuan.pager.NewsPager;
import com.yuan.utils.DensityUtils;

import java.util.List;

/**
 * @author yuan
 */
public class LeftMenuFragment extends BaseFragment {

    private ListView newsTitleLv;
    private List<NewsTitleBean.DataBean> datas;
    private LeftMenuLvAdapter adapter;
    private int prePosition;

    @Override
    public View initView() {
        newsTitleLv = new ListView(context);
        newsTitleLv.setDividerHeight(0);
        newsTitleLv.setPadding(0, DensityUtils.dip2px(context, 40), 0, 0);
        newsTitleLv.setCacheColorHint(Color.TRANSPARENT);
        newsTitleLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录点击的位置，变成红色
                prePosition = position;
                adapter.notifyDataSetChanged();//getCount()-->getView

                //2.把左侧菜单关闭
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();//关<->开

                //3.切换到对应的详情页面：新闻详情页面，专题详情页面，图组详情页面，互动详情页面
                switchPager(prePosition);
            }
        });
        return newsTitleLv;
    }

    private void switchPager(int position) {
        MainActivity activity = (MainActivity) context;
        ContentFragment contentFragment = activity.getContentFragment();
        NewsPager newsPager = contentFragment.getNewsPager();
        newsPager.switchPager(position);
    }

    @Override
    public void initData() {
        super.initData();
        adapter = new LeftMenuLvAdapter(datas, context);
        newsTitleLv.setAdapter(adapter);
    }

    public void setTopics(List<NewsTitleBean.DataBean> topics) {
        this.datas = topics;
    }
}
