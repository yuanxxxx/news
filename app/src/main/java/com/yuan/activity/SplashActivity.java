package com.yuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.*;
import android.widget.RelativeLayout;
import com.yuan.R;
import com.yuan.utils.CacheUtils;

public class SplashActivity extends Activity {

    public static final String START_MAIN = "start_main";
    private RelativeLayout rl_splash_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rl_splash_root = (RelativeLayout) findViewById(R.id.rl_splash_root);

        //渐变、缩放、旋转动画
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(500);
        aa.setFillAfter(true);
        ScaleAnimation sa = new ScaleAnimation(0, 1,0,1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(500);
        sa.setFillAfter(true);
        RotateAnimation ra = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(500);
        ra.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(false);
        //同时播放三个动画
        animationSet.addAnimation(aa);
        animationSet.addAnimation(sa);
        animationSet.addAnimation(ra);
        animationSet.setDuration(2000);

        rl_splash_root.startAnimation(animationSet);

        animationSet.setAnimationListener(new MyAnimationListener());
    }

    class MyAnimationListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //判断是否进入过主页面

            Boolean isStartMain = CacheUtils.getBoolean(SplashActivity.this, START_MAIN);
            if (isStartMain) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
