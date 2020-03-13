package com.yuan.fagment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.OnCheckedChanged;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yuan.R;
import com.yuan.activity.MainActivity;
import com.yuan.base.BaseFragment;
import com.yuan.base.BasePager;
import com.yuan.pager.*;
import com.yuan.utils.LogUtils;

import java.util.ArrayList;

/**
 * @author yuan
 */
public class ContentFragment extends BaseFragment {

    public static final String CONTENT_FRAGMENT = "ContentFragment";
    public ViewPager vp_content;
    public RadioGroup rg_bottom;
    public ArrayList<BasePager> pagers;

    @Override
    public void initData() {
        super.initData();
        initPagerArray();
        // 设置pagerAdapter
        vp_content.setAdapter(new ContentFragmentPagerAdapter());
        rg_bottom.setOnCheckedChangeListener(new BottomRgOnCheckedChangeListener());
        vp_content.addOnPageChangeListener(new ContentVpOnPageChangeListener());

        // 设置第一个显示的页面
        rg_bottom.check(R.id.rb_home);
        pagers.get(0).initData();
        isSlidingMenuEnabled(SlidingMenu.TOUCHMODE_NONE);
    }

    @Override
    public View initView() {
        LogUtils.e(CONTENT_FRAGMENT, "View初始化");
        View view = View.inflate(context, R.layout.content_fragment, null);
        vp_content = (ViewPager) view.findViewById(R.id.vp_content);
        rg_bottom = (RadioGroup) view.findViewById(R.id.rg_bottom);
        initData();
        return view;
    }

    /**
     * 初始化pagers集合
     */
    private void initPagerArray() {
        pagers = new ArrayList<>();
        pagers.add(new HomePager(context));
        pagers.add(new NewsPager(context));
        pagers.add(new SmartPager(context));
        pagers.add(new GovPager(context));
        pagers.add(new SettingPager(context));
    }

    public NewsPager getNewsPager() {
        return (NewsPager) pagers.get(1);
    }

    class ContentFragmentPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pagers.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BasePager pager = pagers.get(position);
            container.addView(pager.rootView);
            return pager.rootView;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    class BottomRgOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    vp_content.setCurrentItem(0);
                    isSlidingMenuEnabled(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_news:
                    vp_content.setCurrentItem(1);
                    isSlidingMenuEnabled(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                case R.id.rb_smart:
                    vp_content.setCurrentItem(2);
                    isSlidingMenuEnabled(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_gov:
                    vp_content.setCurrentItem(3);
                    isSlidingMenuEnabled(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_setting:
                    vp_content.setCurrentItem(4);
                    isSlidingMenuEnabled(SlidingMenu.TOUCHMODE_NONE);
                    break;
                default:
            }
        }
    }

    class ContentVpOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            pagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void isSlidingMenuEnabled(int mode) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(mode);
    }
}
