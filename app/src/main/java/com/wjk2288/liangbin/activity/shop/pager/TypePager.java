package com.wjk2288.liangbin.activity.shop.pager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.base.BasePager;
import com.wjk2288.liangbin.activity.shop.bean.TypeBean;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wjk2288.liangbin.R.id.recyclerview;

/**
 * Created by Administrator on 2017/7/6.
 */
public class TypePager extends BasePager {

    private Subscription subscription;


    private RecyclerView recyclerView;

    private static List<TypeBean.DataBean.ItemsBean> beanList;


    public TypePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {

//        View view = View.inflate(context,R.layout.pager_type, null);
        View view = LayoutInflater.from(context).inflate(R.layout.pager_type, null);

        recyclerView = (RecyclerView) view.findViewById(recyclerview);

        return view;
    }

    @Override
    public void initData() {
        onUnsubscribe();
        NetService service = RequestNet.getIncetance().getRetrofit(NetUtils.TYPE_BASE_URL).create(NetService.class);
        Observer<TypeBean> observer = new Observer<TypeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TypeBean typeBean) {

                beanList = typeBean.getData().getItems();

                TypeAdapter adapter = new TypeAdapter();

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(context, 2));


            }
        };


        subscription = service
                .getType("Android", "http://mobile.iliangcang.com/goods/goodsCategory?app_key=Android&sig=430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768", "1.0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

    class TypeAdapter extends RecyclerView.Adapter<TypeViewHodler> {
        @Override
        public TypeViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.pager_type_item, parent, false);
            return new TypeViewHodler(view);
        }

        @Override
        public void onBindViewHolder(TypeViewHodler holder, int position) {
            holder.setData(position);

        }

        @Override
        public int getItemCount() {
            return beanList == null ? 0 : beanList.size();
        }

    }


    static class TypeViewHodler extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_type_icon)
        ImageView ivTypeIcon;


        public TypeViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {

            TypeBean.DataBean.ItemsBean itemsBean = beanList.get(position);
            String img = itemsBean.getCover_img();


//            Picasso.with(context)
//                    .load(img)
////                    .transform(new )
//                    .into(ivTypeIcon);

            Glide.with(context)
                    .load(img)
                    .error(R.drawable.bg_next_lottery_bottom)
                    .placeholder(R.drawable.bg_next_lottery_bottom)
                    .bitmapTransform(new RoundedCornersTransformation(context, 15, 5))
                    .into(ivTypeIcon);


        }
    }

    public void onUnsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }


}
