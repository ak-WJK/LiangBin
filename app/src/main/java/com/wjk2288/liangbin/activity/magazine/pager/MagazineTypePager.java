package com.wjk2288.liangbin.activity.magazine.pager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.magazine.adapter.MagazineTypeAdapter;
import com.wjk2288.liangbin.activity.magazine.base.MagazineBasePager;
import com.wjk2288.liangbin.activity.magazine.bean.MagazineTypeBean;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetServiceApi;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/14.
 */

public class MagazineTypePager extends MagazineBasePager {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;


    private Subscription subscription;
    private MagazineTypeAdapter adapter;

    public MagazineTypePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.magazine_type_pager, null);
        ButterKnife.bind(this, view);


        adapter = new MagazineTypeAdapter(context);
        recyclerview.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerview.setAdapter(adapter);
        return view;
    }

    @Override
    public void initData() {

        requestData();

    }

    private void requestData() {
        NetServiceApi serviceApi = RequestNet.getIncetance().getRetrofit(NetUtils.MAGAZINE_TYPE_URL).create(NetServiceApi.class);
        Observer<MagazineTypeBean> observer = new Observer<MagazineTypeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("TAG", "onError==" + e.getMessage());

            }

            @Override
            public void onNext(MagazineTypeBean magazineTypeBean) {

                List<MagazineTypeBean.DataBean.ItemsBean> itemsBeanList = magazineTypeBean.getData().getItems();
                adapter.refreshData(itemsBeanList);


            }
        };

        subscription = serviceApi
                .getMagazineType("Android", "AFEC57DDE58BC9A1810C8E3561650110%7C606202010493468", "1.0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

    @Override
    public void initListener() {

    }


    public void onSubscriber() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;

        }
    }


}

