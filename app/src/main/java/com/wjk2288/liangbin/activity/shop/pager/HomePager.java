package com.wjk2288.liangbin.activity.shop.pager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.adapter.HomeAdapter;
import com.wjk2288.liangbin.activity.shop.base.BasePager;
import com.wjk2288.liangbin.activity.shop.bean.HomeBean;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetService;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/6.
 */

public class HomePager extends BasePager {

    public Subscription subscription;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    private HomeAdapter adapter;
    private List<HomeBean.DataBean.ItemsBean.ListBean> listBeanList;

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

        return view;
    }

    @Override
    public void initData() {
        onSubscriber();
        Observer<HomeBean> observer = new Observer<HomeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("TAG", "HomePager==" + e.getMessage());
            }

            @Override
            public void onNext(HomeBean homeBean) {
                listBeanList = homeBean.getData().getItems().getList();

                adapter.refreshData(listBeanList);

            }
        };

        NetService service = RequestNet.getIncetance().getRetrofit(NetUtils.HOME_BASE_URL).create(NetService.class);

        subscription = service
                .getHome("Android",
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


}
