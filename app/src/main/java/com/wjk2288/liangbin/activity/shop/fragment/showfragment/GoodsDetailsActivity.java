package com.wjk2288.liangbin.activity.shop.fragment.showfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.bean.details.TypeDetailsBean;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetServiceApi;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsDetailsActivity extends AppCompatActivity {

    @Bind(R.id.ib_back)
    ImageButton ibBack;
    @Bind(R.id.ib_cart)
    ImageButton ibCart;
    @Bind(R.id.tv_tonghua)
    TextView tvTonghua;
    @Bind(R.id.tv_jiaru_cart)
    TextView tvJiaruCart;
    @Bind(R.id.tv_zhijie_goumai)
    TextView tvZhijieGoumai;
    @Bind(R.id.webView)
    WebView webView;

    private Subscription subscription;
    private String goodsId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        goodsId = intent.getStringExtra("goodsId");

        requestData();


    }

    private void requestData() {
        onUnsebscriber();
        Observer<TypeDetailsBean> observer = new Observer<TypeDetailsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("TAG", "onError==" + e.getMessage());
            }

            @Override
            public void onNext(TypeDetailsBean typeDetailsBean) {

                String goods_url = typeDetailsBean.getData().getItems().getGoods_url();
                //设置webView
                initWebView(goods_url);
            }
        };

        NetServiceApi serviceApi = RequestNet.getIncetance().getRetrofit(NetUtils.GOODSDETAILS_BASE_URL).create(NetServiceApi.class);
        subscription = serviceApi
                .getGoodsDetails("Android",
                        goodsId,
                        "BF287AF953103F390674E73DDA18CFD8%7C639843030233268",
                        "1.0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

    private void initWebView(String goods_url) {
        WebSettings webSettings = webView.getSettings();
        //设置支持webView
        webSettings.setJavaScriptEnabled(true);

        //设置适配手机屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setDisplayZoomControls(true);

        webView.loadUrl(goods_url);
        webView.setWebViewClient(new WebViewClient());


    }

    @OnClick({R.id.ib_back, R.id.ib_cart, R.id.tv_tonghua, R.id.tv_jiaru_cart, R.id.tv_zhijie_goumai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_cart:
                break;
            case R.id.tv_tonghua:
                break;
            case R.id.tv_jiaru_cart:
                break;
            case R.id.tv_zhijie_goumai:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;

        }
    }

    public void onUnsebscriber() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;

        }
    }


}
