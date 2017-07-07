package com.wjk2288.liangbin.activity.shop.pager;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.base.BasePager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/6.
 */

public class GiftPager extends BasePager {


    @Bind(R.id.iv_gift_jingxuan)
    ImageView ivGiftJingxuan;
    @Bind(R.id.iv_gift_jieri)
    ImageView ivGiftJieri;
    @Bind(R.id.iv_gift_aiqing)
    ImageView ivGiftAiqing;
    @Bind(R.id.iv_gift_shengri)
    ImageView ivGiftShengri;
    @Bind(R.id.iv_gift_pengyou)
    ImageView ivGiftPengyou;
    @Bind(R.id.iv_gift_haizi)
    ImageView ivGiftHaizi;
    @Bind(R.id.iv_gift_fumu)
    ImageView ivGiftFumu;
    @Bind(R.id.ll_gift_hint)
    LinearLayout llGiftHint;

    public GiftPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_gift, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_gift_jingxuan, R.id.iv_gift_jieri, R.id.iv_gift_aiqing, R.id.iv_gift_shengri, R.id.iv_gift_pengyou, R.id.iv_gift_haizi, R.id.iv_gift_fumu, R.id.ll_gift_hint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_gift_jingxuan:
                Toast.makeText(context, "精选", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_gift_jieri:
                break;
            case R.id.iv_gift_aiqing:
                break;
            case R.id.iv_gift_shengri:
                break;
            case R.id.iv_gift_pengyou:
                break;
            case R.id.iv_gift_haizi:
                break;
            case R.id.iv_gift_fumu:
                break;
            case R.id.ll_gift_hint:
                Toast.makeText(context, "妹纸要礼物le..", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
