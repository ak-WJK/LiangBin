package com.wjk2288.liangbin.activity.shared.pager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shared.adapter.SatinAdapter;
import com.wjk2288.liangbin.activity.shared.base.SharedBasePager;
import com.wjk2288.liangbin.activity.shared.bean.SatinBean;
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

public class SharedSatinPager extends SharedBasePager {


    @Bind(R.id.listview)
    ListView listview;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            adapter.refreshData(listBeen);

        }
    };
    private SatinBean satinBean;
    private SatinAdapter adapter;
    private List<SatinBean.ListBean> listBeen;

    public SharedSatinPager(Context context) {
        super(context);

    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_shared_satin, null);
        ButterKnife.bind(this, view);

        adapter = new SatinAdapter(context);
        listview.setAdapter(adapter);

        return view;
    }

    @Override
    public void initData() {

        String url = "http://s.budejie.com/topic/tag-topic/64/hot/budejie-android-6.6.3/0-20.json";

        final Request request = new Request.Builder()
                .url(url)
                .build();
        new OkHttpClient().newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.e("TAG", "onFailure==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String json = response.body().string();
                        anaysisJson(json);
                    }
                });

    }

    private void anaysisJson(String json) {

        satinBean = new Gson().fromJson(json, SatinBean.class);
        listBeen = satinBean.getList();
        handler.sendEmptyMessage(0);


    }

    @Override
    public void initListener() {

    }
}
