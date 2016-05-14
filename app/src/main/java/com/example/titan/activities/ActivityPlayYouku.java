package com.example.titan.activities;

/**
 * Created by TITAN on 2015/5/9.
 */

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.titan.materialtest.R;

public class ActivityPlayYouku extends ActionBarActivity {

    private Button btn_reload;
    private Toolbar toolbar;
    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_play_youku);

        final ProgressBar bar = (ProgressBar)findViewById(R.id.playProgressBar);
        btn_reload = (Button) findViewById(R.id.btn_reload);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();


        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String URL = getIntent().getStringExtra("video_link");
        webSettings.setJavaScriptEnabled(true);


        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url+"?x");
                return true;
            }
        });

        //设置页面进度条
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (bar.getVisibility() == View.INVISIBLE) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        btn_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });

        mWebView.loadUrl(URL+"?x");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings){

            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {

        mWebView.reload();
        mWebView.clearCache(true);
        super.onPause();
    }
}