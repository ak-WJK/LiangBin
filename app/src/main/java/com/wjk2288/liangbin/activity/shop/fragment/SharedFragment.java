package com.wjk2288.liangbin.activity.shop.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shared.base.SharedBasePager;
import com.wjk2288.liangbin.activity.shared.pager.SharedRecommendPager;
import com.wjk2288.liangbin.activity.shared.pager.SharedSatinPager;
import com.wjk2288.liangbin.activity.shop.base.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/6.
 */

public class SharedFragment extends BaseFragment {


    @Bind(R.id.tab)
    SegmentTabLayout tab;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private String[] title = {"推荐", "段子"};
    private ArrayList<SharedBasePager> pagers;


    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_shared, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        initViewPager();
        initListener();


    }

    private void initListener() {
        tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab.setCurrentTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initViewPager() {
        pagers = new ArrayList<>();
        pagers.add(new SharedRecommendPager(context));
        pagers.add(new SharedSatinPager(context));

        viewpager.setAdapter(new MyAdapter());
        tab.setTabData(title);


    }

    class MyAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SharedBasePager basePager = pagers.get(position);
            View rootVeiw = basePager.initView();
            basePager.initData();
            basePager.initListener();
            container.addView(rootVeiw);


            return rootVeiw;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
