package com.wjk2288.liangbin.activity.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.base.BaseFragment;
import com.wjk2288.liangbin.activity.shop.fragment.DaRenFragment;
import com.wjk2288.liangbin.activity.shop.fragment.MagazineFragment;
import com.wjk2288.liangbin.activity.shop.fragment.SelfFragment;
import com.wjk2288.liangbin.activity.shop.fragment.SharedFragment;
import com.wjk2288.liangbin.activity.shop.fragment.ShoppingFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_fl)
    FrameLayout mainFl;
    @Bind(R.id.rb_mian_shop)
    RadioButton rbMianShop;
    @Bind(R.id.rb_mian_mgz)
    RadioButton rbMianMgz;
    @Bind(R.id.rb_mian_daren)
    RadioButton rbMianDaren;
    @Bind(R.id.rb_mian_shared)
    RadioButton rbMianShared;
    @Bind(R.id.rb_mian_self)
    RadioButton rbMianSelf;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;


    private int position;
    private ArrayList<BaseFragment> fragments;
    private Fragment fragment;
    private FragmentTransaction fm;
    private BaseFragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //设置按钮默认状态
        rgMain.check(R.id.rb_mian_shop);
        //初始化fragment
        initFragment();
        //设置点击切换的监听
        initListener();
        //默认选中商店页面
        checkedFragment(R.id.rb_mian_shop);


    }

    private void initFragment() {
        fragments = new ArrayList<>();

        fragments.add(new ShoppingFragment());
        fragments.add(new MagazineFragment());
        fragments.add(new DaRenFragment());
        fragments.add(new SharedFragment());
        fragments.add(new SelfFragment());

    }

    private void initListener() {


        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


                checkedFragment(checkedId);
            }
        });


    }

    private void checkedFragment(int checkedId) {
        switch (checkedId) {
            case R.id.rb_mian_shop:
                position = 0;
                break;
            case R.id.rb_mian_mgz:
                position = 1;
                break;
            case R.id.rb_mian_daren:
                position = 2;
                break;
            case R.id.rb_mian_shared:
                position = 3;
                break;
            case R.id.rb_mian_self:
                position = 4;
                break;

        }
        currentFragment = fragments.get(position);
        switchFragment(currentFragment);
    }


    private void switchFragment(BaseFragment currentFragment) {


        fm = getFragmentManager().beginTransaction();

//        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null && !currentFragment.isAdded()) {
            if (fragment != currentFragment) {
                if (fragment != null) {
                    fm.hide(fragment);
                }
                fm.add(R.id.main_fl, currentFragment);
            }
        } else {
            if (fragment != null) {
                fm.hide(fragment);
            }
            fm.show(currentFragment);
        }
        fragment = currentFragment;

        fm.commit();

    }


    public BaseFragment getShopFragment() {
        ShoppingFragment fragment = (ShoppingFragment) fragments.get(0);
        return fragment;
    }


    private boolean isback = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            new AlertDialog.Builder(this)
                    .setTitle("退出用")
                    .setMessage("客观慢走,欢迎下次光临")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();


            //双击退出应用

//            if (isback) {
//                finish();
//            }
//
//            isback = true;
//
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    isback = false;
//                }
//            }, 2000);

            return true;
        }


        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
