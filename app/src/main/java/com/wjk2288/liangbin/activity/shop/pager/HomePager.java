package com.wjk2288.liangbin.activity.shop.pager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.adapter.typepageradapter.HomeAdapter;
import com.wjk2288.liangbin.activity.shop.base.BasePager;
import com.wjk2288.liangbin.activity.shop.bean.typepagerbean.HomeBean;
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

import static com.wjk2288.liangbin.R.id.refresh;

/**
 * Created by Administrator on 2017/7/6.
 */

public class HomePager extends BasePager {

    public Subscription subscription;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(refresh)
    MaterialRefreshLayout materialRefreshLayout;
    @Bind(R.id.ib_top)
    ImageButton ibTop;

    private HomeAdapter adapter;

    private int pager = 1;
    private List<HomeBean.DataBean.ItemsBean.ListBeanX> listBeanXList;


    public HomePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_home, null);
        ButterKnife.bind(this, view);

        adapter = new HomeAdapter(context);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        //设置刷新
        materialRefreshLayout.setWaveColor(R.color.bla);
        materialRefreshLayout.setIsOverLay(true);
        materialRefreshLayout.setWaveShow(true);
        materialRefreshLayout.setLoadMore(true);


        return view;
    }

    @Override
    public void initData() {
        //请求数据
        requestData(pager);

        //刷新的监听
        refreshListener();


    }


    private void refreshListener() {
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {

                pager = 1;
                requestData(pager);

                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                    }
                }, 3000);


            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {

                pager++;
                requestData(pager);


                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                }, 3000);


            }
        });


    }

    private void requestData(final int pager) {
        onSubscriber();
        Observer<HomeBean> observer = new Observer<HomeBean>() {
            @Override
            public void onCompleted() {
                materialRefreshLayout.finishRefresh();
                materialRefreshLayout.finishRefreshLoadMore();

            }

            @Override
            public void onError(Throwable e) {
                materialRefreshLayout.finishRefresh();
                materialRefreshLayout.finishRefreshLoadMore();

                LogUtils.e("TAG", "HomePager==" + e.getMessage());
            }

            @Override
            public void onNext(HomeBean homeBean) {
                listBeanXList = homeBean.getData().getItems().getList();

                adapter.refreshData(listBeanXList, pager);

            }
        };

        NetServiceApi service = RequestNet.getIncetance().getRetrofit(NetUtils.HOME_BASE_URL).create(NetServiceApi.class);

        subscription = service
                .getHome("Android",
                        20,
                        pager,
                        "3780CB0808528F7CE99081D295EE8C0F%7C116941220826768",
                        "626138098",
                        "0516ed9429352c8e1e3bd11c63ba6f54",
                        "1.0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public void onSubscriber() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }


    @OnClick(R.id.ib_top)
    public void onViewClicked() {
        recyclerview.smoothScrollToPosition(0);


    }
}
