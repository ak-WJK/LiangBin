package com.wjk2288.liangbin.activity.shop.base;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/7/6.
 */

public abstract class BasePager {
    public Context context;
    private View rootView;

    public BasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    public abstract View initView();

    public abstract void initData();
}
