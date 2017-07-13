package com.wjk2288.liangbin.activity.shop.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.magazine.adapter.MagazineAdapter;
import com.wjk2288.liangbin.activity.magazine.bean.MagazineBean;
import com.wjk2288.liangbin.activity.shop.base.BaseFragment;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/6.
 */

public class MagazineFragment extends BaseFragment {


    @Bind(R.id.textSwitcher)
    TextSwitcher textSwitcher;
    @Bind(R.id.tv_magazine)
    TextView tvMagazine;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    private MagazineAdapter adapter;
    private ArrayList<MagazineBean> mgzBeanList;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            adapter.refreshData(mgzBeanList);
        }
    };


    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_magazine, null);

        ButterKnife.bind(this, view);

        adapter = new MagazineAdapter(context);
        recyclerview.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerview.setAdapter(adapter);


        return view;
    }

    @Override
    protected void initData() {
        final String url = "http://mobile.iliangcang.com/topic/magazineList?app_key=Android&author_id=1&sig=2FA0974FFF1BC3DFA562AA63C8B5A84F%257C118265010131868&v=1.0";

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Request request = new Request.Builder()
                        .url(url)
                        .build();
                new OkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.e("TAG", "onFailure==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String json = response.body().string();

                        analysisJson(json);


                    }
                });
            }
        }).start();


//        onUnsubscriber();
//        Observer<MagazineBean> observer = new Observer<MagazineBean>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
////            public void onError(Throwable e) {
//                LogUtils.e("TAG", "onError==" + e.getMessage());
//
//            }
//
//            @Override
//            public void onNext(MagazineBean magazineBean) {
//                MagazineBean.DataBean.ItemBean itemBean = magazineBean.getData().getItems();
//
//                LogUtils.e("TAG", "itemBean==" + itemBean.toString());
//
//
//                adapter.refreshData(itemBean);
//
//
//            }
//        };
//
//        NetServiceApi serviceApi = RequestNet.getIncetance().getRetrofit(NetUtils.MAGAZINE_BASE_URL).create(NetServiceApi.class);
//        subscription = serviceApi.getMagazine("Android", 1, "2FA0974FFF1BC3DFA562AA63C8B5A84F%7C118265010131868", "1.0")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);

//
    }

    private void analysisJson(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);

            JSONObject dataObject = jsonObject.optJSONObject("data");
            JSONObject itemObject = dataObject.optJSONObject("items");
//                    Iterator<String> iterator = itemObject.keys();
            JSONArray keys = itemObject.optJSONArray("keys");
            JSONObject infosObject = itemObject.optJSONObject("infos");

            mgzBeanList = new ArrayList<>();

            for (int i = 0; i < keys.length(); i++) {
                //解析出每一个values的值
                JSONArray jsonArray = infosObject.optJSONArray(keys.get(i) + "");
                for (int j = 0; j < jsonArray.length(); j++) {

                    JSONObject jsonObjectItem = jsonArray.optJSONObject(j);
                    MagazineBean magazineBean = new MagazineBean();

                    String topic_name = jsonObjectItem.optString("topic_name");
                    magazineBean.setTopic_name(topic_name);

                    String cover_img_new = jsonObjectItem.optString("cover_img_new");
                    magazineBean.setCover_img_new(cover_img_new);

                    String addtime = jsonObjectItem.optString("addtime");
                    magazineBean.setAddtime(addtime);



                    String cat_name = jsonObjectItem.optString("cat_name");
                    magazineBean.setCat_name(cat_name);

                    magazineBean.setMonthInfo(keys.get(i) + "");

                    mgzBeanList.add(magazineBean);


                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                adapter.refreshData(mgzBeanList);
//            }
//        });

        handler.sendEmptyMessage(0);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.textSwitcher, R.id.tv_magazine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textSwitcher:
                break;
            case R.id.tv_magazine:
                break;
        }
    }
}