package com.wjk2288.liangbin.activity.shop.fragment.showfragment;

import android.app.FragmentTransaction;
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
import com.wjk2288.liangbin.activity.activity.MainActivity;
import com.wjk2288.liangbin.activity.shop.activity.GoodsDetailsActivity;
import com.wjk2288.liangbin.activity.shop.adapter.showadapter.TypeShowAdapter;
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

public class TypeShowFragment extends BaseFragment implements View.OnClickListener {
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


    private TextView mTv_whole;
    private TextView mTv_levelone;
    private TextView mTv_leveltwo;
    private TextView mTv_levelthree;
    private TextView mTv_levelfour;
    private TextView mTv_levelfive;


    private int pager = 1;

    private PopupWindow popupWindow;
    private String[] catCode = {"0045", "0055", "0062", "0069", "0077",
            "0082", "0092", "0101", "0112", "0125", "0129", "0141", "0154", "0166",
            "0172", "0182", "0190", "0198", "0214"};
    private int position;
    private List<TypeShowBean.DataBean.ItemsBean> itemsBeanList;


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

        //接收传递过来的数据
        position = getArguments().getInt("position");


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
                itemsBeanList = typeShowBean.getData().getItems();
                adapter.refreshData(itemsBeanList, pager);

            }
        };


        onUnsubscriber();
        NetServiceApi serviceApi = RequestNet.getIncetance().getRetrofit(NetUtils.HOUSEHOME_BASE_URL).create(NetServiceApi.class);
        subscription = serviceApi
                .getTypeShow("Android", catCode[position], 10, 1, pager, "3D3968703BE211CC6D931C9D4F737FB4%7C290216330933368&v=1.0", "1.0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void initListener() {
        final MainActivity mainActivity = (MainActivity) context;
        final FragmentTransaction fm = mainActivity.getFragmentManager().beginTransaction();


        //设置返回的监听事件
        ibShopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                mainActivity.getFragmentManager().popBackStack();

                mainActivity.hideShopListFragment();
//

            }
        });


        //设置筛选的监听事件
        priceScreening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupWindow();
            }

        });


        //设置商品详情页面的点击监听
        adapter.setOnPagerClickListener(new TypeShowAdapter.onPagerClickListener() {
            @Override
            public void onPagerListener(int position) {

                TypeShowBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);


                String goods_id = itemsBean.getGoods_id();


                Intent intent = new Intent(context, GoodsDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemsBean", itemsBean);
                intent.putExtras(bundle);
                intent.putExtra("goodsId", goods_id);
                startActivity(intent);
            }
        });


    }

    private void showPopupWindow() {
        View converview = LayoutInflater.from(context).inflate(R.layout.fragment_typeshow_pricescreening_popu, null);
        popupWindow = new PopupWindow(converview, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        mTv_whole = (TextView) converview.findViewById(R.id.tv_whole);
        mTv_levelone = (TextView) converview.findViewById(R.id.tv_levelone);
        mTv_leveltwo = (TextView) converview.findViewById(R.id.tv_leveltwo);
        mTv_levelthree = (TextView) converview.findViewById(R.id.tv_levelthree);
        mTv_levelfour = (TextView) converview.findViewById(R.id.tv_levelfour);
        mTv_levelfive = (TextView) converview.findViewById(R.id.tv_levelfive);

        //初始化控件
        mTv_whole.setOnClickListener(this);
        mTv_levelone.setOnClickListener(this);
        mTv_leveltwo.setOnClickListener(this);
        mTv_levelthree.setOnClickListener(this);
        mTv_levelfour.setOnClickListener(this);
        mTv_levelfive.setOnClickListener(this);


        //显示popup

        //当popup弹出后所有的焦点都由popup处理
        popupWindow.setFocusable(true);
        //设置外部可点击的
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAtLocation(priceScreening, Gravity.TOP, 0, 0);

        //设置popup的动画效果
//        popupWindow.setAnimationStyle(R.style.popup_anim);


        //显示popup并显示在priceScreening此控件下
//        popupWindow.showAtLocation(priceScreening, Gravity.BOTTOM, 0, 0);
        popupWindow.showAsDropDown(priceScreening);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_whole:
                showToast("全部");
                popupWindow.dismiss();
                break;
            case R.id.tv_levelone:
                showToast("0 - 200");
                popupWindow.dismiss();
                break;
            case R.id.tv_leveltwo:
                showToast("201 - 500");
                popupWindow.dismiss();
                break;
            case R.id.tv_levelthree:
                showToast("501 - 1000");
                popupWindow.dismiss();
                break;
            case R.id.tv_levelfour:
                showToast("1001 - 3000");
                popupWindow.dismiss();
                break;
            case R.id.tv_levelfive:
                showToast("3000以上");
                popupWindow.dismiss();
                break;

        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
