package com.wjk2288.liangbin.activity.shop.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.adapter.DarenAdapter;
import com.wjk2288.liangbin.activity.shop.base.BaseFragment;
import com.wjk2288.liangbin.activity.shop.bean.DaRenBean;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetServiceApi;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/6.
 */

public class DaRenFragment extends BaseFragment {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.ib_shop_search)
    ImageButton ibShopSearch;
    @Bind(R.id.ib_shop_back)
    ImageButton ibShopBack;
    @Bind(R.id.tv_shopping)
    TextView tvShopping;
    @Bind(R.id.ib_shop_cart)
    ImageButton ibShopCart;
    private DarenAdapter adapter;

    private int pager = 1;

    private boolean isselect = false;

    @Override
    public View initView() {
//        View view = LayoutInflater.from(context).inflate(R.layout.fragment_daren, null);
        View view = View.inflate(context, R.layout.fragment_daren, null);
        ButterKnife.bind(this, view);

        tvShopping.setText("达人");
        ibShopCart.setBackgroundResource(R.drawable.actionbar_navigation_menu);

        return view;
    }

    @Override
    protected void initData() {
        ibShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isselect = !isselect;
                if (isselect) {
                    ibShopCart.setBackgroundResource(R.drawable.close);
                } else {
                    ibShopCart.setBackgroundResource(R.drawable.actionbar_navigation_menu);

                }


            }
        });

        //设置适配器
        adapter = new DarenAdapter(context);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new GridLayoutManager(context, 3));

        //联网请求数据
        requestData();


    }

    private void requestData() {
        onUnsubscriber();
        Observer<DaRenBean> observer = new Observer<DaRenBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("TAG", "onErroR==" + e.getMessage());

            }

            @Override
            public void onNext(DaRenBean daRenBean) {

                List<DaRenBean.DataBean.ItemsBean> itemsBeanList = daRenBean.getData().getItems();

                adapter.refreshData(itemsBeanList, pager);


            }
        };

        NetServiceApi serviceApi = RequestNet.getIncetance().getRetrofit(NetUtils.DAREN_BASE_URL).create(NetServiceApi.class);


        subscription = serviceApi
                .getDaRen("Android", 18, pager, "BF287AF953103F390674E73DDA18CFD8|639843030233268", "1.0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
