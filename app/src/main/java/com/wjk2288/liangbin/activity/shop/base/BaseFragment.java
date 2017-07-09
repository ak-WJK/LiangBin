package com.wjk2288.liangbin.activity.shop.base;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import rx.Subscription;

/**
 * Created by Administrator on 2017/7/5.
 */

public abstract class BaseFragment extends Fragment {

    public Subscription subscription;


    public Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return initView();

    }

    public abstract View initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

    }

    protected abstract void initData();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    public void onUnsubscriber() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
