package com.wjk2288.liangbin.activity.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.dao.UserDAO;
import com.wjk2288.liangbin.activity.shop.bean.CartBean;
import com.wjk2288.liangbin.activity.shop.bean.details.GoodsDetailsBean;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetServiceApi;
import com.wjk2288.liangbin.activity.shop.view.AddSubView;
import com.wjk2288.liangbin.activity.utils.LogUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wjk2288.liangbin.R.id.goods_name;
import static com.wjk2288.liangbin.R.id.id_flowlayout;
import static com.wjk2288.liangbin.activity.shop.base.BasePager.context;

public class GoodsDetailsActivity extends AppCompatActivity implements View.OnClickListener {
//

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


    private ImageButton btnBack;
    private ImageView goodsIcon;
    private TextView goodsName;
    private TextView goodsContent;
    private TextView goodsPrice;
    private TextView goodsAttrContent;

    private TagFlowLayout mFlowLayout;
    private TextView goodsAttrNumber;
    private AddSubView av;
    private Button btnGouMian;


    TagAdapter mAdapter;

    private Subscription subscription;
    private String goodsId;
    private PopupWindow popupWindow;
    private GoodsDetailsBean.DataBean goodsDetailsBeanData;
    private String attr_name;
    private GoodsDetailsBean.DataBean.ItemsBean.SkuInfoBean.AttrListBean bean;
    private Button btnAddCart;
    private CartBean cartBean;
    private ArrayList<String> lists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);


        //购物车存储类
        cartBean = new CartBean();

        Intent intent = getIntent();
        goodsId = intent.getStringExtra("goodsId");

        requestData();


    }

    private void requestData() {
        onUnsebscriber();
        Observer<GoodsDetailsBean> observer = new Observer<GoodsDetailsBean>() {


            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("TAG", "onError==" + e.getMessage());
            }

            @Override
            public void onNext(GoodsDetailsBean goodsDetailsBean) {
                goodsDetailsBeanData = goodsDetailsBean.getData();


                String goods_url = goodsDetailsBean.getData().getItems().getGoods_url();
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
                Intent intent = new Intent(GoodsDetailsActivity.this, CartActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_tonghua:
                Toast.makeText(GoodsDetailsActivity.this, "联系客服", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_jiaru_cart:

                showAddCartPopupWindow();


                break;
            case R.id.tv_zhijie_goumai:


                break;
        }
    }

    private void showAddCartPopupWindow() {


        final View popupView = LayoutInflater.from(context).inflate(R.layout.add_cart_popupwindow, null);


        btnBack = (ImageButton) popupView.findViewById(R.id.btn_back);
        goodsIcon = (ImageView) popupView.findViewById(R.id.goods_icon);
        goodsName = (TextView) popupView.findViewById(goods_name);
        goodsContent = (TextView) popupView.findViewById(R.id.goods_content);
        goodsPrice = (TextView) popupView.findViewById(R.id.goods_price);
        goodsAttrContent = (TextView) popupView.findViewById(R.id.goods_attr_content);
        mFlowLayout = (TagFlowLayout) popupView.findViewById(id_flowlayout);
        goodsAttrNumber = (TextView) popupView.findViewById(R.id.goods_attr_number);
        av = (AddSubView) popupView.findViewById(R.id.av);
        btnAddCart = (Button) popupView.findViewById(R.id.btn_add_cart);
        btnGouMian = (Button) popupView.findViewById(R.id.btn_goumain);

        btnBack.setOnClickListener(this);
        btnAddCart.setOnClickListener(this);
        btnGouMian.setOnClickListener(this);

        String goods_id = goodsDetailsBeanData.getItems().getGoods_id();
        cartBean.setGoodsId(goods_id);

        LogUtils.e("TAG", "goods_id==" + goods_id);

        String goods_image = goodsDetailsBeanData.getItems().getGoods_image();

        Glide.with(this).load(goods_image).into(goodsIcon);

        String brand_name = goodsDetailsBeanData.getItems().getBrand_info().getBrand_name();
        goodsName.setText(brand_name);

        //设置品牌名称到cartBean
        cartBean.setGoodsName(brand_name);

        LogUtils.e("TAG", "brand_name==" + brand_name);

        String goods_name = goodsDetailsBeanData.getItems().getGoods_name();
        goodsContent.setText(goods_name);

        //设置品牌描述
        cartBean.setGoodsContent(goods_name);

        LogUtils.e("TAG", "goods_name==" + goods_name);

        String type_name = goodsDetailsBeanData.getItems().getSku_info().get(0).getType_name();
        goodsAttrContent.setText(type_name);

        final List<GoodsDetailsBean.DataBean.ItemsBean.SkuInfoBean.AttrListBean> attrList = goodsDetailsBeanData.getItems().getSku_info().get(0).getAttrList();

        mFlowLayout.setAdapter(mAdapter = new TagAdapter(attrList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {

                TextView textView = (TextView) LayoutInflater.from(GoodsDetailsActivity.this).inflate(R.layout.tv, mFlowLayout, false);

                lists = new ArrayList<>();

                for (int i = 0; i < attrList.size(); i++) {
                    bean = attrList.get(i);
                    attr_name = bean.getAttr_name();
                    lists.add(attr_name);
                }


                //展示商品标签
                textView.setText(lists.get(position));


                return textView;
            }
        });


        //设置默认选中
        mAdapter.setSelectedList(0);


        //获取选中的集合
//        mFlowLayout.getSelectedList();

        String price1 = goodsDetailsBeanData.getItems().getSku_inv().get(0).getPrice();
        goodsPrice.setText("¥" + price1);

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {


                String price = goodsDetailsBeanData.getItems().getSku_inv().get(position).getPrice();

                goodsPrice.setText("¥" + price);

                //设置商品价格到bean类
                cartBean.setGoodsPrice("¥" + price);
                LogUtils.e("TAG", "price==" + price);

                //设置所选中的商品标签
                mAdapter.setSelectedList(position);

                //设置商品属性名称到购物车bean类中
                cartBean.setAttrName(lists.get(position));


                //设置选中的产品的展示图片
                String img_path = goodsDetailsBeanData.getItems().getSku_info().get(0).getAttrList().get(position).getImg_path();
                Glide.with(GoodsDetailsActivity.this).load(img_path).into(goodsIcon);

                //设置所选中商品的展示图片
                cartBean.setImageUrl(img_path);

                LogUtils.e("TAG", "imagepath==" + img_path);

                return true;
            }

        });

//        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
//            @Override
//            public void onSelected(Set<Integer> selectPosSet) {
//
////                mAdapter.setSelectedList(selectPosSet);
//            }
//        });


        av.setOnNumberChangerListener(new AddSubView.OnNumberChangerListener() {

            @Override
            public void onNumberChanger(int value) {
                if (value == 0) {
                    cartBean.setGoodsNumber(1);

                }

                cartBean.setGoodsNumber(value);
                LogUtils.e("TAG", "value= " + value);

            }
        });


        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        popupWindow.showAtLocation(tvJiaruCart, 0, 0, 50);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                popupWindow.dismiss();
                break;
            case R.id.btn_add_cart:
                LogUtils.e("TAG", "添加购物车");

                //数据添加到数据库
                UserDAO.getInstance().addGoods(cartBean);
                LogUtils.e("TAG", "添加成功");

                break;
            case R.id.btn_goumain:


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
