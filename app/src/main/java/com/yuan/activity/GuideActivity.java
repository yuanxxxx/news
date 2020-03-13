package com.yuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.yuan.R;
import com.yuan.utils.CacheUtils;
import com.yuan.utils.DensityUtils;

import java.util.ArrayList;

import static com.yuan.activity.SplashActivity.START_MAIN;

/**
 * @author yuan
 */
public class GuideActivity extends Activity {
    private static final String TAG = "GuideActivity";
    private ViewPager viewpager_start_main;
    private Button btn_start_main;
    private ArrayList<ImageView> imageViews;
    private LinearLayout ll_points_normal;
    private ImageView iv_red_point;
    private int disOfTwoPoints;
    private int pointSizeInPx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        imageViews = new ArrayList<>();
        btn_start_main = (Button) findViewById(R.id.btn_start_main);
        viewpager_start_main = (ViewPager) findViewById(R.id.viewpager_start_main);
        ll_points_normal = (LinearLayout) findViewById(R.id.ll_points_normal);
        iv_red_point = (ImageView) findViewById(R.id.iv_red_point);

        int[] imageIds = new int[]{
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3
        };

        // 添加viewpager的资源
        for (int imageId : imageIds) {
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(imageId);
            imageViews.add(imageView);
        }

        // 添加三个小灰点
        pointSizeInPx = DensityUtils.dip2px(this, 10);
        Log.e(TAG, String.valueOf(pointSizeInPx));
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.point_normal);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointSizeInPx, pointSizeInPx);
            if (i != 0) {
                params.leftMargin = pointSizeInPx;
            }
            imageView.setLayoutParams(params);
            ll_points_normal.addView(imageView);
        }

        iv_red_point.setBackgroundResource(R.drawable.point_red);

        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        viewpager_start_main.setAdapter(new MyPagerAdapter());

        viewpager_start_main.addOnPageChangeListener(new MyOnPageChangeListener());

        btn_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtils.putBoolean(GuideActivity.this, START_MAIN, true);
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 获取两个灰色点的间距
     */
    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            iv_red_point.getViewTreeObserver().removeGlobalOnLayoutListener(MyOnGlobalLayoutListener.this);
            disOfTwoPoints = ll_points_normal.getChildAt(1).getLeft() - ll_points_normal.getChildAt(0).getLeft();
        }
    }


    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int leftMargin = (int) (position * disOfTwoPoints + disOfTwoPoints * positionOffset);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
            layoutParams.leftMargin = leftMargin;
        }

        @Override
        public void onPageSelected(int position) {
            if (position == imageViews.size() - 1) {
                btn_start_main.setVisibility(View.VISIBLE);
            } else {
                btn_start_main.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeViewAt(position);
        }
    }
}
