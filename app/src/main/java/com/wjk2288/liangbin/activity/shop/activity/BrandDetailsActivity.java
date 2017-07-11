package com.wjk2288.liangbin.activity.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.adapter.showadapter.BrandDetailsAdapter;
import com.wjk2288.liangbin.activity.shop.bean.details.BrandDetailsBean;
import com.wjk2288.liangbin.activity.shop.bean.details.BrandDetailsPagerBean;
import com.wjk2288.liangbin.activity.shop.bean.typepagerbean.BrandBean;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetServiceApi;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wjk2288.liangbin.activity.shop.base.BasePager.context;

public class BrandDetailsActivity extends AppCompatActivity {


    @Bind(R.id.iv_brand_logo)
    ImageView ivBrandLogo;
    @Bind(R.id.tv_gushi)
    RadioButton tvGushi;
    @Bind(R.id.tv_chanpin)
    RadioButton tvChanpin;
    @Bind(R.id.rg_chanpin_details)
    RadioGroup rgChanpinDetails;
    @Bind(R.id.tv_brand_gushi)
    TextView tvBrandGushi;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.ib_back)
    ImageButton ibBack;
    @Bind(R.id.tv_pinpai_name)
    TextView tvPinpaiName;
    private int brandId;

    private Subscription subscription;
    private List<BrandDetailsBean.DataBean.ItemsBean> itemsBeanList;
    private BrandDetailsAdapter adapter;
    private String brand_name;
    private BrandBean.DataBean.ItemsBean itemsBean;
    private String brand_desc;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_details);
        ButterKnife.bind(this);


        itemsBean = (BrandBean.DataBean.ItemsBean) getIntent().getSerializableExtra("bundle");
        position = getIntent().getIntExtra("position", 1);
        brandId = itemsBean.getBrand_id();

        brand_name = itemsBean.getBrand_name();

        adapter = new BrandDetailsAdapter(this);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));


        requestNet();
        initListener();
        initData();
        rgChanpinDetails.check(R.id.tv_chanpin);

    }

    private void initData() {

        tvPinpaiName.setText(brand_name);
        String brand_logo = itemsBean.getBrand_logo();
        Glide.with(this)
                .load(brand_logo)
                .bitmapTransform(new RoundedCornersTransformation(context, 360, 5))
                .into(ivBrandLogo);


    }

    private void initListener() {
        rgChanpinDetails.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.tv_gushi:


                        recyclerview.setVisibility(View.GONE);
                        tvBrandGushi.setVisibility(View.VISIBLE);

                        int position = adapter.getPosition();


                        //设置下标越界异常抛出防止崩溃
                        try {
                            brand_desc = itemsBeanList.get(position).getBrand_info().getBrand_desc();


                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

                        if (!TextUtils.isEmpty(brand_desc)) {
                            tvBrandGushi.setText(brand_desc);
                        }


                        break;

                    case R.id.tv_chanpin:
                        tvBrandGushi.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);
                        adapter.refreshData(itemsBeanList);

                        break;
                }
            }
        });


        //设置item的点击事件
        adapter.setOnItemClickListener(new BrandDetailsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {

                String goods_id = itemsBeanList.get(position).getGoods_id();
                LogUtils.e("TAG", "googs_id===" + goods_id);

//
//                String goods_url = itemsBeanList.get(position).getGoods_url();
////                LogUtils.e("TAG", "url----" + goods_url);
                requestDetailsNet(goods_id);


            }
        });


    }

    private void requestNet() {
        onUnsebscriber();
        NetServiceApi serviceApi = RequestNet.getIncetance().getRetrofit(NetUtils.BRAND_DETAILS_BASE_URL).create(NetServiceApi.class);

        Observer<BrandDetailsBean> observer = new Observer<BrandDetailsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("TAG", "onError===" + e.getMessage());
            }

            @Override
            public void onNext(BrandDetailsBean brandDetailsBean) {
                itemsBeanList = brandDetailsBean.getData().getItems();
                adapter.refreshData(itemsBeanList);

            }
        };

        subscription = serviceApi
                .getBrandDetails("Android",
                        brandId,
                        20,
                        1,
                        "430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768",
                        "1.0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void requestDetailsNet(String id) {
        onUnsebscriber();
        NetServiceApi serviceApi = RequestNet.getIncetance().getRetrofit(NetUtils.BRAND_DETAILS_BASE_URL_PAGER).create(NetServiceApi.class);

        Observer<BrandDetailsPagerBean> observer = new Observer<BrandDetailsPagerBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("TAG", "onError===" + e.getMessage());
            }

            @Override
            public void onNext(BrandDetailsPagerBean brandDetailsPagerBean) {
                String goods_url = brandDetailsPagerBean.getData().getItems().getGoods_url();
                Intent intent = new Intent(BrandDetailsActivity.this, GoodsDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean",  brandDetailsPagerBean);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        };

        subscription = serviceApi
                .getBrandDetailsPager("Android",
                        id,
                        "430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768",
                        "1.0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    @OnClick({R.id.ib_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
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
