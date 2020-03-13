package com.yuan.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.yuan.R;
import com.yuan.fagment.ContentFragment;
import com.yuan.fagment.LeftMenuFragment;
import com.yuan.utils.DensityUtils;

/**
 * @author yuan
 */
public class MainActivity extends SlidingActivity {

    public static final String MAIN_CONTENT_TAG = "main_content_tag";
    public static final String LEFTMENU_TAG = "leftmenu_tag";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置主页面
        setContentView(R.layout.activity_content);
        // 设置左侧页面
        setBehindContentView(R.layout.activity_leftmenu);
        SlidingMenu menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        // 设置滑动模式：滑动边缘，全屏滑动，不可以滑动
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置主页占据的宽度
        menu.setBehindOffset(DensityUtils.dip2px(MainActivity.this, 200));
        initFragment();
    }

    private void initFragment() {
        // 得到FM
        FragmentManager fm = getSupportFragmentManager();
        // 开启事务
        FragmentTransaction fT = fm.beginTransaction();
        // 替换
        fT.replace(R.id.fl_leftmenu, new LeftMenuFragment(), LEFTMENU_TAG);
        fT.replace(R.id.fl_content, new ContentFragment(), MAIN_CONTENT_TAG);
        // 提交
        fT.commit();
    }

    public LeftMenuFragment getLeftMenuFragment() {
        return (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag(LEFTMENU_TAG);
    }

    public ContentFragment getContentFragment() {
        return (ContentFragment) getSupportFragmentManager().findFragmentByTag(MAIN_CONTENT_TAG);

    }
}
