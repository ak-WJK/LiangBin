package com.wjk2288.liangbin.activity.magazine.base;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/7/14.
 */

public abstract class MagazineBasePager {

    public  Context context;
    public View rootView;

    public MagazineBasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    public abstract View initView();

    public abstract void initData();

    public abstract void initListener();


}
