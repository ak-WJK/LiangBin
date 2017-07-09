package com.wjk2288.liangbin.activity.shop.pager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.adapter.typepageradapter.BrandAdapter;
import com.wjk2288.liangbin.activity.shop.base.BasePager;
import com.wjk2288.liangbin.activity.shop.bean.typepagerbean.BrandBean;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetServiceApi;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/6.
 */

public class BrandPager extends BasePager {


    private Subscription subscription;

    private ListView listview;

    private MaterialRefreshLayout materialRefreshLayout;


    private int pager = 1;
    private BrandAdapter adapter;

    public BrandPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_brand, null);
        listview = (ListView) view.findViewById(R.id.listview);
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);


        materialRefreshLayout.setWaveColor(R.color.bla);
        materialRefreshLayout.setIsOverLay(true);
        materialRefreshLayout.setWaveShow(true);
        materialRefreshLayout.setLoadMore(true);


        return view;
    }

    @Override
    public void initData() {

        adapter = new BrandAdapter();
        //设置适配器
        listview.setAdapter(adapter);

        //请求数据
        RequestDatas(pager);

        //设置刷新和加载
        refreshDataListener();

    }


    private void refreshDataListener() {
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {

                //下拉刷新时,刷新的是首页的页面
                pager = 1;
                RequestDatas(pager);

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

                RequestDatas(pager);

                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        materialRefreshLayout.finishRefreshLoadMore();

                    }
                }, 3000);

            }
        });
    }

    /**
     * load data
     *
     * @param pager
     */
    private void RequestDatas(final int pager) {

        onUnsubscribe();

        Observer<BrandBean> observer = new Observer<BrandBean>() {
            @Override
            public void onCompleted() {
                materialRefreshLayout.finishRefresh();
                materialRefreshLayout.finishRefreshLoadMore();

            }

            @Override
            public void onError(Throwable e) {
                materialRefreshLayout.finishRefresh();
                materialRefreshLayout.finishRefreshLoadMore();
                Log.e("TAG", "onError==" + e.getMessage());

            }

            @Override
            public void onNext(BrandBean brandBean) {


                List<BrandBean.DataBean.ItemsBean> itemsBeanList = brandBean.getData().getItems();


                //--------------
                adapter.refreshData(itemsBeanList, pager);


            }
        };

        NetServiceApi service = RequestNet.getIncetance().getRetrofit(NetUtils.BRAND_BASE_URL).create(NetServiceApi.class);
        subscription = service
                .getBrand("Android", 20, pager, "430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768", "1.0")
                .subscribeOn(Schedulers.newThread())
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
