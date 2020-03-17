package com.yuan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yuan.R;

/**
 * @author yuan
 */
public class NewsDetailActivity extends Activity {

    // Content View Elements

    private RelativeLayout rl_title;
    private TextView tv_title;
    private ImageButton ib_back;
    private ImageButton ib_textsize;
    private ImageButton ib_share;
    private WebView web_view;
    private ProgressBar pb_loading;
    private WebSettings webSettings;

    // End Of Content View Elements

    private void bindViews() {

        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_textsize = (ImageButton) findViewById(R.id.ib_textsize);
        ib_share = (ImageButton) findViewById(R.id.ib_share);
        web_view = (WebView) findViewById(R.id.web_view);
        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        bindViews();
        initData();
    }

    private void initData() {
        String url = getIntent().getStringExtra("url");

        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb_loading.setVisibility(View.INVISIBLE);
            }
        });

        webSettings = web_view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(true);

        web_view.loadUrl(url);
    }
}
