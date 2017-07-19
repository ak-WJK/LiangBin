package com.wjk2288.liangbin.activity.magazine.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wjk2288.liangbin.R;

public class MagazineWebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine_web_view);
        webView = (WebView) findViewById(R.id.webView);

        String url = getIntent().getStringExtra("url");


        initWebView(url);

    }

    private void initWebView(String url) {

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }
}
