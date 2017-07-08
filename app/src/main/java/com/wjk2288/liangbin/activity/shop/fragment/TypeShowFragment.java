package com.wjk2288.liangbin.activity.shop.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.adapter.TypeShowAdapter;
import com.wjk2288.liangbin.activity.shop.base.BaseFragment;
import com.wjk2288.liangbin.activity.shop.bean.details.TypeShowBean;
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
 * Created by Administrator on 2017/7/8.
 */

public class TypeShowFragment extends BaseFragment {
    @Bind(R.id.ib_shop_search)
    ImageButton ibShopSearch;
    @Bind(R.id.ib_shop_back)
    ImageButton ibShopBack;
    @Bind(R.id.ib_shop_cart)
    ImageButton ibShopCart;
    @Bind(R.id.price_screening)
    TextView priceScreening;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    private TypeShowAdapter adapter;

    private int pager = 1;


    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_typeshow, null);
        ButterKnife.bind(this, view);
        ibShopSearch.setVisibility(View.GONE);
        ibShopBack.setVisibility(View.VISIBLE);

        return view;
    }


    @Override
    protected void initData() {

        //设置适配器
        adapter = new TypeShowAdapter(context);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new GridLayoutManager(context, 2));


        //请求网络
        requestData();

        //设置点击事件
        initListener();

    }

    private void requestData() {

        Observer<TypeShowBean> observer = new Observer<TypeShowBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("TAG", "onError==" + e.getMessage());
            }

            @Override
            public void onNext(TypeShowBean typeShowBean) {
                List<TypeShowBean.DataBean.ItemsBean> itemsBeanList = typeShowBean.getData().getItems();
                adapter.refreshData(itemsBeanList, pager);

            }
        };


        onUnsubscriber();
        NetServiceApi serviceApi = RequestNet.getIncetance().getRetrofit(NetUtils.HOUSEHOME_BASE_URL).create(NetServiceApi.class);
        subscription = serviceApi
                .getTypeShow("Android", "0045", 10, 1, 1, "3D3968703BE211CC6D931C9D4F737FB4%7C290216330933368&v=1.0", "1.0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    private void initListener() {
//        ibShopBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "111111111", Toast.LENGTH_SHORT).show();
//                MainActivity mainActivity = (MainActivity) context;
//                FragmentTransaction fm = mainActivity.getFragmentManager().beginTransaction();
//                Fragment shopFragment = mainActivity.getShopFragment();
//
//                fm.replace(R.id.main_fl,shopFragment);
//
//
//            }
//        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
