package com.yuan;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Fresco
        Fresco.initialize(this);
    }
}
