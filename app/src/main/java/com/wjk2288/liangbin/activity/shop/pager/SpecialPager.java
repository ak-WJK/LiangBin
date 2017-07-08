package com.wjk2288.liangbin.activity.shop.pager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.adapter.SpecialAdapter;
import com.wjk2288.liangbin.activity.shop.base.BasePager;
import com.wjk2288.liangbin.activity.shop.bean.SpecialBean;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetServiceApi;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/6.
 */

public class SpecialPager extends BasePager {


    private Subscription subscription;
    private RecyclerView recyclerview;

    private MaterialRefreshLayout materialRefreshLayout;


    private int pager = 1;
    private SpecialAdapter adapter;


    public SpecialPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_special, null);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);

        //设置适配器
        adapter = new SpecialAdapter();
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new GridLayoutManager(context, 1));


        //设置刷新
        materialRefreshLayout.setWaveColor(R.color.bla);
        materialRefreshLayout.setIsOverLay(true);
        materialRefreshLayout.setWaveShow(true);
        materialRefreshLayout.setLoadMore(true);

        return view;
    }

    @Override
    public void initData() {

        //联网请求数据
        requestData(pager);


        //设置刷新的监听

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

        onUnsubscribe();
        Observer<SpecialBean> observer = new Observer<SpecialBean>() {
            @Override
            public void onCompleted() {
                materialRefreshLayout.finishRefresh();
                materialRefreshLayout.finishRefreshLoadMore();
            }

            @Override
            public void onError(Throwable e) {
                materialRefreshLayout.finishRefresh();
                materialRefreshLayout.finishRefreshLoadMore();
                LogUtils.e("TAG", "SpecialPager==" + e.getMessage());

            }

            @Override
            public void onNext(SpecialBean specialBean) {

                List<SpecialBean.DataBean.ItemsBean> itemsBeanList = specialBean.getData().getItems();

                adapter.refreshData(itemsBeanList, pager);


            }
        };


        NetServiceApi service = RequestNet.getIncetance().getRetrofit(NetUtils.SPECIAL_BASE_URL).create(NetServiceApi.class);


        subscription = service
                .getSpecial("Android", 10, pager, "3780CB0808528F7CE99081D295EE8C0F%7C116941220826768", "626138098", "0516ed9429352c8e1e3bd11c63ba6f54", "1.0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public void onUnsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;

        }


    }


}
