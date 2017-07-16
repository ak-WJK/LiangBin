package com.wjk2288.liangbin.activity.shared.base;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/7/15.
 */

public abstract class SharedBasePager {

    private View rootVeiw;
    public Context context;

    public SharedBasePager(Context context) {
        this.context = context;
        rootVeiw = initView();
    }

    public abstract View initView();

    public abstract void initData();

    public abstract void initListener();

}
