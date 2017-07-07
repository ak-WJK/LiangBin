package com.wjk2288.liangbin.activity.shop.pager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.base.BasePager;
import com.wjk2288.liangbin.activity.shop.bean.SpecialBean;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetService;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/6.
 */

public class SpecialPager extends BasePager {


    private Subscription subscription;
    private RecyclerView recyclerview;
    private List<SpecialBean.DataBean.ItemsBean> itemsBeanList;

    public SpecialPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_special, null);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        return view;
    }

    @Override
    public void initData() {


        Observer<SpecialBean> observer = new Observer<SpecialBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("TAG", "SpecialPager==" + e.getMessage());

            }

            @Override
            public void onNext(SpecialBean specialBean) {

                itemsBeanList = specialBean.getData().getItems();

                SpecialAdapter adapter = new SpecialAdapter();

                recyclerview.setAdapter(adapter);
                recyclerview.setLayoutManager(new GridLayoutManager(context, 1));

            }
        };


        NetService service = RequestNet.getIncetance().getRetrofit(NetUtils.SPECIAL_BASE_URL).create(NetService.class);


        subscription = service
                .getSpecial("Android", 10, 1, "3780CB0808528F7CE99081D295EE8C0F%7C116941220826768", "626138098", "0516ed9429352c8e1e3bd11c63ba6f54", "1.0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    class SpecialAdapter extends RecyclerView.Adapter<SpecialViewHodler> {

        @Override
        public SpecialViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = inflate(context, R.layout.pager_special_item, null);
            View view = LayoutInflater.from(context).inflate(R.layout.pager_special_item, parent, false);

            return new SpecialViewHodler(view);
        }

        @Override
        public void onBindViewHolder(SpecialViewHodler holder, int position) {
            holder.setData(position);
        }

        @Override
        public int getItemCount() {
            return itemsBeanList == null ? 0 : itemsBeanList.size();
        }

    }

    class SpecialViewHodler extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_special_icon)
        ImageView ivSpecialIcon;
        @Bind(R.id.tv_special_iconcontent)
        TextView tvSpecialIconcontent;


        public SpecialViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            SpecialBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);
            tvSpecialIconcontent.setText(itemsBean.getTopic_name());
            Glide.with(context)
                    .load(itemsBean.getCover_img_new())
                    .into(ivSpecialIcon);


        }
    }


    public void onUnsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;

        }


    }


}
