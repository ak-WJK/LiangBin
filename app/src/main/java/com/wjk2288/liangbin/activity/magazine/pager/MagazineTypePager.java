package com.wjk2288.liangbin.activity.magazine.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wjk2288.liangbin.activity.magazine.base.MagazineBasePager;

/**
 * Created by Administrator on 2017/7/14.
 */

public class MagazineTypePager extends MagazineBasePager {
    public MagazineTypePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("类型");
        return textView;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
