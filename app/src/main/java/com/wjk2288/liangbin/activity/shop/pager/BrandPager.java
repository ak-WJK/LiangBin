package com.wjk2288.liangbin.activity.shop.pager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.base.BasePager;
import com.wjk2288.liangbin.activity.shop.bean.BrandBean;
import com.wjk2288.liangbin.activity.shop.net.NetUtils;
import com.wjk2288.liangbin.activity.shop.net.RequestNet;
import com.wjk2288.liangbin.activity.shop.service.NetService;

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

public class BrandPager extends BasePager {


    private Subscription subscription;

    private ListView listview;


    private List<BrandBean.DataBean.ItemsBean> itemsBeanList;

    private int pager = 1;

    public BrandPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_brand, null);
        listview = (ListView) view.findViewById(R.id.listview);

        return view;
    }

    @Override
    public void initData() {

        Observer<BrandBean> observer = new Observer<BrandBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError==" + e.getMessage());

            }

            @Override
            public void onNext(BrandBean brandBean) {


                itemsBeanList = brandBean.getData().getItems();

                BrandAdapter adapter = new BrandAdapter();
                listview.setAdapter(adapter);

            }
        };

        NetService service = RequestNet.getIncetance().getRetrofit(NetUtils.BRAND_BASE_URL).create(NetService.class);
        subscription = service
                .getBrand("Android", 20, 1, "430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768", "1.0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }



    class BrandAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return itemsBeanList == null ? 0 : itemsBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemsBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.pager_brand_item, null);
                viewHolder = new ViewHolder(convertView);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            BrandBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);

            viewHolder.tvBrandName.setText(itemsBean.getBrand_name());
            Picasso.with(context)
                    .load(itemsBean.getBrand_logo())
                    .into(viewHolder.ivBrandIcon);


            return convertView;
        }


    }

    static class ViewHolder {
        @Bind(R.id.iv_brand_icon)
        ImageView ivBrandIcon;
        @Bind(R.id.tv_brand_name)
        TextView tvBrandName;
        @Bind(R.id.ib_brand_getinto)
        ImageButton ibBrandGetinto;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void onUnsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }


}
