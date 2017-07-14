package com.wjk2288.liangbin.activity.magazine.pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.magazine.adapter.MagazineAutherAdapter;
import com.wjk2288.liangbin.activity.magazine.base.MagazineBasePager;
import com.wjk2288.liangbin.activity.magazine.bean.MagazineAutherBean;
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

public class MagazineAutherPager extends MagazineBasePager {

    public Subscription subscription;
    @Bind(R.id.listview)
    ListView listview;
    private MagazineAutherAdapter adapter;

    public MagazineAutherPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.magazine_auther_pager, null);
        ButterKnife.bind(this, view);
        adapter = new MagazineAutherAdapter(context);
        listview.setAdapter(adapter);

        return view;
    }

    @Override
    public void initData() {

        requestNet();


    }

    private void requestNet() {
        NetServiceApi serviceApi = RequestNet.getIncetance().getRetrofit(NetUtils.MAGAZINE_AUTHER_URL).create(NetServiceApi.class);

        Observer<MagazineAutherBean> observer = new Observer<MagazineAutherBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("TAG", "onError==" + e.getMessage());

            }

            @Override
            public void onNext(MagazineAutherBean magazineAutherBean) {
                List<MagazineAutherBean.DataBean.ItemsBean> itemsBeanList = magazineAutherBean.getData().getItems();
                adapter.refreshData(itemsBeanList);

            }
        };

        subscription = serviceApi
                .getMagazineAuther("Android", "2FA0974FFF1BC3DFA562AA63C8B5A84F%7C118265010131868", "1.0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

    @Override
    public void initListener() {


    }

    public void onUnsubscriber() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }


}
