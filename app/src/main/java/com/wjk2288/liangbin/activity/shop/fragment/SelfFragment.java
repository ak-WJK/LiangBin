package com.wjk2288.liangbin.activity.shop.fragment;


import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.activity.MainActivity;
import com.wjk2288.liangbin.activity.shop.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
;

/**
 * Created by Administrator on 2017/7/6.
 */

public class SelfFragment extends BaseFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_self, null);
        ButterKnife.bind(this, view);

        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setSupportActionBar(toolbar);

        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(mainActivity,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerToggle.syncState();

        //设置导航视图的点击事件

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

        });

        return view;
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
