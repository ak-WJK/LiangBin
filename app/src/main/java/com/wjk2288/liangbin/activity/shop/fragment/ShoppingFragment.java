package com.wjk2288.liangbin.activity.shop.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.base.BaseFragment;
import com.wjk2288.liangbin.activity.shop.base.BasePager;
import com.wjk2288.liangbin.activity.shop.bean.TypeBean;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.pager.BrandPager;
import com.wjk2288.liangbin.activity.shop.pager.GiftPager;
import com.wjk2288.liangbin.activity.shop.pager.HomePager;
import com.wjk2288.liangbin.activity.shop.pager.SpecialPager;
import com.wjk2288.liangbin.activity.shop.pager.TypePager;
import com.wjk2288.liangbin.activity.shop.service.NetService;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/6.
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
    private TextView textView;
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

        NetService service = RequestNet.getIncetance().getRetrofit().create(NetService.class);
        Observer<TypeBean> observer = new Observer<TypeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TypeBean typeBean) {
                Log.e("TAG", "typeBean==" + typeBean);

            }
        };


        subscription = service
                .getType("Android", "http://mobile.iliangcang.com/goods/goodsCategory?app_key=Android&sig=430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768", "1.0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


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
