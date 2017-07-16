package com.wjk2288.liangbin.activity.shared.pager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shared.adapter.RecommendAdapter;
import com.wjk2288.liangbin.activity.shared.base.SharedBasePager;
import com.wjk2288.liangbin.activity.shared.bean.RecommedBean;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/15.
 */

public class SharedRecommendPager extends SharedBasePager {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;

    private int page = 1;
    private RecommendAdapter adapter;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            adapter.refeshData(recommedBeanList, page);

        }
    };
    private List<RecommedBean.ListBean> recommedBeanList;


    public SharedRecommendPager(Context context) {
        super(context);

    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_shared_recommend, null);
        ButterKnife.bind(this, view);

        adapter = new RecommendAdapter(context);
        recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(adapter);

        return view;
    }

    @Override
    public void initData() {
        requestData(page);


    }

    private void requestData(int page) {
        final String url = "http://c.api.budejie.com/topic/list/jingxuan/" + page + "/budejie-android-6.7.2/0-20.json?market=baidu&udid=864394010202606&appname=baisibudejie&os=4.4.2&client=android&visiting=&mac=70%3A18%3A8B%3AC8%3A4E%3AC2&ver=6.7.2";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                new OkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.e("TAG", "onFailure == " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        anaysisJson(json);

                    }
                });
            }
        }).start();


    }

    private void anaysisJson(String json) {

        RecommedBean recommedBean = new Gson().fromJson(json, RecommedBean.class);
        recommedBeanList = recommedBean.getList();

        handler.sendEmptyMessage(0);

    }

    @Override
    public void initListener() {

    }
}
