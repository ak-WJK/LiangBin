package com.wjk2288.liangbin.activity.shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.daren.activity.DarenDetailsActivity;
import com.wjk2288.liangbin.activity.daren.adapter.DarenAdapter;
import com.wjk2288.liangbin.activity.daren.bean.DaRenShowBean;
import com.wjk2288.liangbin.activity.shop.base.BaseFragment;
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

public class DaRenFragment extends BaseFragment implements View.OnClickListener {

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

    private TextView mTv_default_tuijian;
    private TextView mTv_zuiduo_tuijian;
    private TextView mTv_zuishou_welcome;
    private TextView mTv_zuixin_tuijian;
    private TextView mTv_zuixin_jinru;
    private PopupWindow popupWindow;
    private List<DaRenShowBean.DataBean.ItemsBean> itemsBeanList;


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
                    showPopupWindow();

                } else {
                    ibShopCart.setBackgroundResource(R.drawable.actionbar_navigation_menu);
                    popupWindow.dismiss();
                }


            }


        });


        //设置适配器
        adapter = new DarenAdapter(context);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new GridLayoutManager(context, 3));

        //联网请求数据
        requestData();

        //设置监听
        initListener();


    }

    private void initListener() {
        adapter.setOnItemClickListener(new DarenAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                DaRenShowBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);
                Intent intent = new Intent(context, DarenDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemsBean", itemsBean);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }

    private void showPopupWindow() {
        View popupView = LayoutInflater.from(context).inflate(R.layout.fragment_daren_screening_popu, null);
        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        bindViews(popupView);

        //设置popup在弹出后所有焦点都是popup处理
        popupWindow.setFocusable(true);


        popupWindow.setOutsideTouchable(false);

//        popupWindow.setTouchable(true);
//        popupWindow.setFocusable(true);


        //设置外部可点击的
//        popupWindow.setOutsideTouchable(false);


//        popupWindow.showAtLocation(ibShopCart, Gravity.BOTTOM, 0, 0);
        popupWindow.showAsDropDown(ibShopCart, 0, 25);

        //设置popup的dismiss的监听实现按钮的变化
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ibShopCart.setBackgroundResource(R.drawable.actionbar_navigation_menu);
            }
        });

    }

    private void bindViews(View popupView) {

        mTv_default_tuijian = (TextView) popupView.findViewById(R.id.tv_default_tuijian);
        mTv_zuiduo_tuijian = (TextView) popupView.findViewById(R.id.tv_zuiduo_tuijian);
        mTv_zuishou_welcome = (TextView) popupView.findViewById(R.id.tv_zuishou_welcome);
        mTv_zuixin_tuijian = (TextView) popupView.findViewById(R.id.tv_zuixin_tuijian);
        mTv_zuixin_jinru = (TextView) popupView.findViewById(R.id.tv_zuixin_jinru);

        mTv_default_tuijian.setOnClickListener(this);
        mTv_zuiduo_tuijian.setOnClickListener(this);
        mTv_zuishou_welcome.setOnClickListener(this);
        mTv_zuixin_tuijian.setOnClickListener(this);
        mTv_zuixin_jinru.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_default_tuijian:
                showToast("默认推荐");
                dismissPopup();
                break;
            case R.id.tv_zuiduo_tuijian:
                showToast("最多推荐");
                dismissPopup();
                break;
            case R.id.tv_zuishou_welcome:
                showToast("最受欢迎");
                dismissPopup();
                break;
            case R.id.tv_zuixin_tuijian:
                showToast("最新推荐");
                dismissPopup();
                break;
            case R.id.tv_zuixin_jinru:
                showToast("最新加入");
                dismissPopup();
                break;
        }

    }

    private void dismissPopup() {
        popupWindow.dismiss();
        ibShopCart.setBackgroundResource(R.drawable.actionbar_navigation_menu);
    }


    private void requestData() {
        onUnsubscriber();
        Observer<DaRenShowBean> observer = new Observer<DaRenShowBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("TAG", "onErroR==" + e.getMessage());

            }

            @Override
            public void onNext(DaRenShowBean daRenBean) {

                itemsBeanList = daRenBean.getData().getItems();

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


}
