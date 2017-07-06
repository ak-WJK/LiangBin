package com.wjk2288.liangbin.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class GuideActivity extends AppCompatActivity {


    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.btn_splash_liangLin)
    Button btnSplashLiangLin;

    int[] icons = {R.drawable.feature1,
            R.drawable.feature2,
            R.drawable.feature3,
            R.drawable.feature4,
            R.drawable.feature5
    };
    private GifImageView gifImageView;
    private List<GifImageView> gifImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        gifImageViews = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            gifImageView = new GifImageView(this);
            gifImageView.setImageResource(icons[i]);
            gifImageViews.add(gifImageView);
        }

        //设置引导页面的gifviewPager
        viewpager.setAdapter(new GuidePagerAdapter());
        //给viewPager设置滑动的事件监听
        viewpager.addOnPageChangeListener(new MyAddOnPageChangeListener());

    }

    class MyAddOnPageChangeListener implements ViewPager.OnPageChangeListener {


        //页面滑动的回调
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //当页面选中时回调
        @Override
        public void onPageSelected(int position) {
            if (position == icons.length - 1) {
                btnSplashLiangLin.setVisibility(View.VISIBLE);
                startAnimation();

            } else {
                btnSplashLiangLin.setVisibility(View.GONE);
                btnSplashLiangLin.clearAnimation();
            }

            //设置跳转页面
            btnSplashLiangLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    //存储启动状态
                    SpUtils.getInstance(GuideActivity.this).save("istart", true);
                }
            });


        }

        /**
         * 当页面滑动状态改变的时候回调  三种状态
         * ViewPager.SCROLL_STATE_DRAGGING   拖动状态
         * ViewPager.SCROLL_STATE_IDLE       空闲状态
         * ViewPager.SCROLL_STATE_SETTLING   惯性状态
         */

        @Override
        public void onPageScrollStateChanged(int state) {


        }
    }


    //btn显示的动画
    public void startAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(600);
        btnSplashLiangLin.startAnimation(alphaAnimation);
    }


    class GuidePagerAdapter extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            GifImageView gifImageView = gifImageViews.get(position);
            container.addView(gifImageView);


            return gifImageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return gifImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


}
