package com.wjk2288.liangbin.activity.shop.pager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.base.BasePager;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/7/6.
 */
public class TypePager extends BasePager {


    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    private TextView textView;

    public TypePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.pager_type, null);

//        recyclerview
//        recyclerview.setAdapter();

        return view;
    }

    @Override
    public void initData() {
        // textView.setText("类型页面");

    }
}
