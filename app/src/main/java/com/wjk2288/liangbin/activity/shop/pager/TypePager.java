package com.wjk2288.liangbin.activity.shop.pager;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.activity.MainActivity;
import com.wjk2288.liangbin.activity.shop.adapter.typepageradapter.TypeAdapter;
import com.wjk2288.liangbin.activity.shop.base.BasePager;
import com.wjk2288.liangbin.activity.shop.bean.TypeBean;
import com.wjk2288.liangbin.activity.shop.fragment.showfragment.TypeShowFragment;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetServiceApi;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wjk2288.liangbin.R.id.recyclerview;

/**
 * Created by Administrator on 2017/7/6.
 */
public class TypePager extends BasePager {

    private Subscription subscription;


    private RecyclerView recyclerView;

    private MaterialRefreshLayout materialRefreshLayout;

    private static List<TypeBean.DataBean.ItemsBean> beanList;
    private TypeAdapter adapter;


    public TypePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {

//        View view = View.inflate(context,R.layout.pager_type, null);
        View view = LayoutInflater.from(context).inflate(R.layout.pager_type, null);

        recyclerView = (RecyclerView) view.findViewById(recyclerview);
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);


        adapter = new TypeAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));


        materialRefreshLayout.setWaveColor(R.color.bla);
        materialRefreshLayout.setIsOverLay(true);
        materialRefreshLayout.setWaveShow(true);
//        materialRefreshLayout.setLoadMore(true);

        return view;
    }

    @Override
    public void initData() {

        //请求数据
        RequestDatas();

        //刷新数据
        refreshListener();

        //设置监听
        setItemListener();


    }

    private void setItemListener() {
        adapter.setOnItemClickListener(new TypeAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TypeShowFragment showFragment = new TypeShowFragment();

                MainActivity mainActivity = (MainActivity) context;

                FragmentTransaction fm = mainActivity.getFragmentManager().beginTransaction();

                if (showFragment != null) {


                    //fragment之间传递数据
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    showFragment.setArguments(bundle);

                    //原动画没毛病
                    fm.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);


                    /*
                    *
                    *
                    * 动画有bug待解
                    *
                    *
                    * */
//                    fm.setCustomAnimations(R.anim.in_from_right, 0);

                    fm.replace(R.id.main_fl, showFragment);
                    fm.addToBackStack(null);
                    fm.commit();
                }


            }
        });


    }

    private void refreshListener() {
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {

                RequestDatas();

                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        materialRefreshLayout.finishRefresh();

                    }
                }, 3000);


            }
        });
    }

    private void RequestDatas() {
        onUnsubscribe();
        NetServiceApi service = RequestNet.getIncetance().getRetrofit(NetUtils.TYPE_BASE_URL).create(NetServiceApi.class);
        Observer<TypeBean> observer = new Observer<TypeBean>() {
            @Override
            public void onCompleted() {
                materialRefreshLayout.finishRefresh();
            }

            @Override
            public void onError(Throwable e) {
                materialRefreshLayout.finishRefresh();
            }

            @Override
            public void onNext(TypeBean typeBean) {

                beanList = typeBean.getData().getItems();

                adapter.refreshData(beanList);

            }
        };


        subscription = service
                .getType("Android", "430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768", "1.0")
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
