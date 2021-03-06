package com.wjk2288.liangbin.activity.shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.activity.CartActivity;
import com.wjk2288.liangbin.activity.shop.base.BaseFragment;
import com.wjk2288.liangbin.activity.shop.base.BasePager;
import com.wjk2288.liangbin.activity.shop.pager.BrandPager;
import com.wjk2288.liangbin.activity.shop.pager.GiftPager;
import com.wjk2288.liangbin.activity.shop.pager.HomePager;
import com.wjk2288.liangbin.activity.shop.pager.SpecialPager;
import com.wjk2288.liangbin.activity.shop.pager.TypePager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/6.
 *
 */

public class ShoppingFragment extends BaseFragment {


    @Bind(R.id.ib_shop_search)
    ImageButton ibShopSearch;
    @Bind(R.id.ib_shop_cart)
    ImageButton ibShopCart;
    @Bind(R.id.tab)
    TabLayout tab;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    String[] titles = {"分类", "品牌", "首页", "专题", "礼物"};
    private ArrayList<BasePager> pagers;


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_shopping, null);

        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    protected void initData() {

        initPager();

        viewpager.setAdapter(new MyPagerAdapter());
        //将tabLayout与viewPager建立联系
        tab.setupWithViewPager(viewpager);

        //设置默认选中的Tab
        tab.getTabAt(2).select();

    }

    private void initPager() {
        //装配shop的各个页面的集合
        pagers = new ArrayList<>();

        pagers.add(new TypePager(context));
        pagers.add(new BrandPager(context));
        pagers.add(new HomePager(context));
        pagers.add(new SpecialPager(context));
        pagers.add(new GiftPager(context));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.ib_shop_search, R.id.ib_shop_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_shop_search:
                Intent intent = new Intent(context, ScanActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_shop_cart:
                intent = new Intent(context, CartActivity.class);
                startActivity(intent);

                break;
        }
    }

    class MyPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            BasePager basePager = pagers.get(position);
            View rootView = basePager.initView();
            basePager.initData();
            /*
            *一定要在各个pager中初始化initView,要然让报
            * lang.IllegalArgumentException: Cannot add a null child view to a ViewGroup
            * */
            container.addView(rootView);

            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return super.getPageTitle(position);

            return titles[position];
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

}
