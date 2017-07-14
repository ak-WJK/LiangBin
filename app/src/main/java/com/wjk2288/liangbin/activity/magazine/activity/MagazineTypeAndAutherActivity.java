package com.wjk2288.liangbin.activity.magazine.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.magazine.base.MagazineBasePager;
import com.wjk2288.liangbin.activity.magazine.pager.MagazineAutherPager;
import com.wjk2288.liangbin.activity.magazine.pager.MagazineTypePager;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MagazineTypeAndAutherActivity extends AppCompatActivity {


    @Bind(R.id.tab)
    TabLayout tab;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tv_magazine_top)
    TextView tvMagazineTop;
    @Bind(R.id.rb_type)
    RadioButton rbType;
    @Bind(R.id.rb_auther)
    RadioButton rbAuther;
    @Bind(R.id.rg_magazine)
    RadioGroup rgMagazine;
    private String[] mTitle = {"分类", "作者"};
    private ArrayList<MagazineBasePager> pagers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine_type_and_auther);
        ButterKnife.bind(this);
        initPager();
        initTab();
        gadioGroupListener();
        rgMagazine.check(R.id.rb_type);
        viewpagerListener();


    }

    private void viewpagerListener() {
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rgMagazine.check(R.id.rb_type);
                } else {
                    rgMagazine.check(R.id.rb_auther);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void gadioGroupListener() {
        rgMagazine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_type:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.rb_auther:
                        viewpager.setCurrentItem(1);
                        break;
                }


            }
        });

        tvMagazineTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.magazine_back_top);
            }
        });


    }


    private void initTab() {
        //设置tablayout关联viewpager
        tab.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tab, 80, 80);
            }
        });
        tab.setupWithViewPager(viewpager);


    }

    private void initPager() {

        pagers = new ArrayList<>();
        pagers.add(new MagazineTypePager(this));
        pagers.add(new MagazineAutherPager(this));

        viewpager.setAdapter(new MyPagerAdapter(pagers));

    }


    class MyPagerAdapter extends PagerAdapter {

        private ArrayList<MagazineBasePager> pagers;

        public MyPagerAdapter(ArrayList<MagazineBasePager> pagers) {

            this.pagers = pagers;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            MagazineBasePager pager = pagers.get(position);

            View rootView = pager.initView();
            pager.initData();
            pager.initListener();
            container.addView(rootView);
            return rootView;
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
            return mTitle[position];
        }
    }


    //设置tablayout的下划线长度的
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }
}