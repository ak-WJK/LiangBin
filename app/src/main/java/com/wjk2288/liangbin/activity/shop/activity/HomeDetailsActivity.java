package com.wjk2288.liangbin.activity.shop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wjk2288.liangbin.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeDetailsActivity extends AppCompatActivity {

    @Bind(R.id.ib_back)
    ImageButton ibBack;
    @Bind(R.id.tv_biaoti)
    TextView tvBiaoti;
    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_details);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("name");
        tvBiaoti.setText(title);

        initWebView(url);


    }

    private void initWebView(String url) {
        WebSettings webSettings = webView.getSettings();
        //设置支持webView
        webSettings.setJavaScriptEnabled(true);

        //设置适配手机屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setDisplayZoomControls(true);

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());


    }

    @OnClick(R.id.ib_back)
    public void onViewClicked() {
        finish();
    }
}
