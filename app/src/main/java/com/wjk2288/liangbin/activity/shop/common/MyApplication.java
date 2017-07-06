package com.wjk2288.liangbin.activity.shop.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/7/6.
 */

public class MyApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }


}
