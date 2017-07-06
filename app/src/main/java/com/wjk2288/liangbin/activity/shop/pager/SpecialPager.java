package com.wjk2288.liangbin.activity.shop.pager;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wjk2288.liangbin.activity.shop.base.BasePager;

/**
 * Created by Administrator on 2017/7/6.
 */

public class SpecialPager extends BasePager {


    private TextView textView;

    public SpecialPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        textView.setText("专题页面");

    }
}
