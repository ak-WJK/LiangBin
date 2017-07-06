package com.wjk2288.liangbin.activity.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/7/6.
 */

public class SpUtils {

    private static SharedPreferences sp;
    private static final String IS_START = "isStart";

    private SpUtils() {

    }

    private static SpUtils spUtils = new SpUtils();

    public static SpUtils getInstance(Context context) {
        sp = context.getSharedPreferences(IS_START, MODE_PRIVATE);
        return spUtils;
    }


    public void save(String key, Object values) {
        SharedPreferences.Editor edit = sp.edit();
        if (values instanceof Boolean) {
            edit.putBoolean(key, (Boolean) values).commit();
        }

        if (values instanceof String) {
            edit.putString(key, (String) values).commit();
        }

    }


    public boolean getBooleanValues(String key) {
        return sp.getBoolean(key, false);
    }


}
