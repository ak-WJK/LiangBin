package com.wjk2288.liangbin.activity.shop.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.wjk2288.liangbin.activity.shop.base.BaseFragment;

/**
 * Created by Administrator on 2017/7/6.
 */

public class DaRenFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    @Override
    protected void initData() {
        textView.setText("达人");
    }
}
