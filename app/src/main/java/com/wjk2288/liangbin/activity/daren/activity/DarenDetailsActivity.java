package com.wjk2288.liangbin.activity.daren.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.daren.adapter.DaRenDetailsAdapter;
import com.wjk2288.liangbin.activity.daren.adapter.GuanZhuAndFenSiAdapter;
import com.wjk2288.liangbin.activity.daren.bean.DaRenDetailsBean;
import com.wjk2288.liangbin.activity.daren.bean.DaRenShowBean;
import com.wjk2288.liangbin.activity.daren.bean.renqibean.FenSiBean;
import com.wjk2288.liangbin.activity.daren.bean.renqibean.TuiJianBean;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetServiceApi;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wjk2288.liangbin.activity.shop.base.BasePager.context;

public class DarenDetailsActivity extends AppCompatActivity {

    public Subscription subscription;
    @Bind(R.id.ib_shop_back)
    ImageButton ibShopBack;
    @Bind(R.id.tv_shopping)
    TextView tvShopping;
    @Bind(R.id.iv_daren_touxiang)
    ImageView ivDarenTouxiang;
    @Bind(R.id.tv_daren_name)
    TextView tvDarenName;
    @Bind(R.id.tv_daren_hangye)
    TextView tvDarenHangye;
    @Bind(R.id.tv_daren_sixin)
    TextView tvDarenSixin;
    @Bind(R.id.tv_daren_guanzhu)
    TextView tvDarenGuanzhu;
    @Bind(R.id.rb_daren_xihuan)
    RadioButton rbDarenXihuan;
    @Bind(R.id.rb_daren_tuijian)
    RadioButton rbDarenTuijian;
    @Bind(R.id.rb_guanzhu)
    RadioButton rbGuanzhu;
    @Bind(R.id.rb_daren_fensi)
    RadioButton rbDarenFensi;
    @Bind(R.id.rg_daren)
    RadioGroup rgDaren;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;


    private DaRenDetailsAdapter adapter;
    private DaRenDetailsBean.DataBean dataBean;
    private String user_id;
    private GuanZhuAndFenSiAdapter guanZhuadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daren_details);
        ButterKnife.bind(this);

        DaRenShowBean.DataBean.ItemsBean itemsBean = (DaRenShowBean.DataBean.ItemsBean) getIntent().getSerializableExtra("itemsBean");
        String uid = itemsBean.getUid();
        //设置头像等信息
        setDaRenData(itemsBean);

        requestData(uid);
        //联网是分线程 直接写此处报空指针
//        initData();
        initListener();

    }

    private void setDaRenData(DaRenShowBean.DataBean.ItemsBean itemsBean) {
        String username = itemsBean.getUsername();
        tvShopping.setText(username);

        String orig = itemsBean.getUser_images().getOrig();
        tvDarenName.setText(username);

        Glide.with(context).load(orig).into(ivDarenTouxiang);

        String duty = itemsBean.getDuty();
        tvDarenHangye.setText(duty);


    }

    private void initListener() {


        rgDaren.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_daren_tuijian:


                        String recommendation_count = dataBean.getItems().getRecommendation_count();
                        int recommendationCount = Integer.parseInt(recommendation_count);


                        if (recommendationCount != 0) {
                            adapter = new DaRenDetailsAdapter(DarenDetailsActivity.this);
                            recyclerview.setAdapter(adapter);
                            recyclerview.setVisibility(View.VISIBLE);

                            GridLayoutManager manager = new GridLayoutManager(DarenDetailsActivity.this, 2);
                            recyclerview.setLayoutManager(manager);

                            requestData("masterListInfo", user_id);
                            LogUtils.e("TAG", "user id==" + user_id);

                        } else {
                            recyclerview.setVisibility(View.GONE);
                        }


                        break;
                    case R.id.rb_daren_xihuan:


                        adapter = new DaRenDetailsAdapter(DarenDetailsActivity.this);
                        recyclerview.setAdapter(adapter);


                        String like_count = dataBean.getItems().getLike_count();
                        int likeCount = Integer.parseInt(like_count);


                        if (likeCount != 0) {
                            recyclerview.setVisibility(View.VISIBLE);
                            GridLayoutManager manager = new GridLayoutManager(DarenDetailsActivity.this, 2);
                            recyclerview.setLayoutManager(manager);


                            requestData("masterLike", user_id);

                        } else {
                            recyclerview.setVisibility(View.GONE);

                        }


                        break;
                    case R.id.rb_guanzhu:

                        String following_count = dataBean.getItems().getFollowing_count();
                        int followingCount = Integer.parseInt(following_count);


                        if (followingCount != 0) {

                            guanZhuadapter = new GuanZhuAndFenSiAdapter(DarenDetailsActivity.this);
                            recyclerview.setAdapter(guanZhuadapter);


                            recyclerview.setVisibility(View.VISIBLE);

                            GridLayoutManager manager = new GridLayoutManager(DarenDetailsActivity.this, 3);

                            recyclerview.setLayoutManager(manager);
                            requestDataGuanZhu("masterFollow", user_id);

                        } else {
                            recyclerview.setVisibility(View.GONE);

                        }


                        break;
                    case R.id.rb_daren_fensi:


                        String followed_count = dataBean.getItems().getFollowed_count();
                        int followedCount = Integer.parseInt(followed_count);


                        if (followedCount != 0) {

                            guanZhuadapter = new GuanZhuAndFenSiAdapter(DarenDetailsActivity.this);
                            recyclerview.setAdapter(guanZhuadapter);


                            recyclerview.setVisibility(View.VISIBLE);

                            GridLayoutManager manager = new GridLayoutManager(DarenDetailsActivity.this, 3);
                            recyclerview.setLayoutManager(manager);
                            requestDataGuanZhu("masterFollowed", user_id);

                        } else {
                            recyclerview.setVisibility(View.GONE);

                        }


                        break;
                }
            }
        });


        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy == 0) {

                }

            }
        });


    }

    private void initData() {

        String like_count = dataBean.getItems().getLike_count();
        String recommendation_count = dataBean.getItems().getRecommendation_count();
        String following_count = dataBean.getItems().getFollowing_count();
        String followed_count = dataBean.getItems().getFollowed_count();


        String like = rbDarenXihuan.getText().toString();
        String xihuan = String.format(like, like_count);
        rbDarenXihuan.setText(xihuan);

        String s = rbDarenTuijian.getText().toString();
        String tuijian = String.format(s, recommendation_count);
        rbDarenTuijian.setText(tuijian);


        String s2 = rbGuanzhu.getText().toString();
        String guanzhu = String.format(s2, following_count);
        rbGuanzhu.setText(guanzhu);

        String s3 = rbDarenFensi.getText().toString();
        String fensi = String.format(s3, followed_count);
        rbDarenFensi.setText(fensi);

        //设置选中的rb
        rgDaren.check(R.id.rb_daren_tuijian);


    }

    private void requestData(String uid) {
        onUnsubscriber();
        NetServiceApi serviceApi = RequestNet.getIncetance().getRetrofit(NetUtils.DAREN_BASE_DETAILS_URL).create(NetServiceApi.class);
        Observer<DaRenDetailsBean> observer = new Observer<DaRenDetailsBean>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DaRenDetailsBean daRenDetailsBean) {
                dataBean = daRenDetailsBean.getData();
                user_id = dataBean.getItems().getUser_id();
                LogUtils.e("TAG", "user id " + user_id);

                initData();

//
            }
        };

        subscription = serviceApi
                .getDaRenDetails("Android",
                        12,
                        uid,
                        1,
                        "BF287AF953103F390674E73DDA18CFD8%7C639843030233268",
                        "1.0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }


    public void onUnsubscriber() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;

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

    @OnClick(R.id.ib_shop_back)
    public void onViewClicked() {
        finish();
    }


    private void requestData(String values, String uid) {
        onUnsubscriber();
        NetServiceApi serviceApi = RequestNet.getIncetance().getRetrofit(NetUtils.DAREN_RENQI_BASE_DETAILS_URL).create(NetServiceApi.class);
        Observer<TuiJianBean> observer = new Observer<TuiJianBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TuiJianBean tuiJianBean) {

                List<TuiJianBean.DataBean.ItemsBean.GoodsBean> goodsBeanList = tuiJianBean.getData().getItems().getGoods();

                adapter.refreshData(goodsBeanList);


            }
        };

        subscription = serviceApi
                .getDaRenRenQi(values,
                        "Android",
                        10,
                        uid,
                        1,
                        "BF287AF953103F390674E73DDA18CFD8|639843030233268",
                        "1.0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }


    private void requestDataGuanZhu(String values, String uid) {
        onUnsubscriber();
        NetServiceApi serviceApi = RequestNet.getIncetance().getRetrofit(NetUtils.DAREN_RENQI_BASE_DETAILS_URL).create(NetServiceApi.class);
        Observer<FenSiBean> observer = new Observer<FenSiBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(FenSiBean fenSiBean) {

                List<FenSiBean.DataBean.ItemsBean.UsersBean> usersBeanList = fenSiBean.getData().getItems().getUsers();

                guanZhuadapter.refreshDataFenSi(usersBeanList);


            }
        };

        subscription = serviceApi
                .getDaRenRenQi2(values,
                        "Android",
                        10,
                        uid,
                        1,
                        "BF287AF953103F390674E73DDA18CFD8|639843030233268",
                        "1.0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }


}
