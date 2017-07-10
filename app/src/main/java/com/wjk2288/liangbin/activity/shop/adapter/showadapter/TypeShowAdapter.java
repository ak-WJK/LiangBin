package com.wjk2288.liangbin.activity.shop.adapter.showadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.bean.details.TypeShowBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/8.
 */

public class TypeShowAdapter extends RecyclerView.Adapter<TypeShowAdapter.TypeShowViewHodler> {
    private Context context;

    private List<TypeShowBean.DataBean.ItemsBean> datas = null;
    private onPagerClickListener listener;


    public TypeShowAdapter(Context context) {
        this.context = context;

        datas = new ArrayList<>();
    }

    @Override
    public TypeShowViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_typeshow_item, parent, false);
        return new TypeShowViewHodler(view);
    }

    @Override
    public void onBindViewHolder(TypeShowViewHodler holder, final int position) {
        holder.setData(datas, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPagerListener(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class TypeShowViewHodler extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_type_show_icon)
        ImageView ivTypeShowIcon;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_type)
        TextView tvType;
        @Bind(R.id.tv_zan)
        TextView tvZan;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_oldpric)
        TextView tvOldpric;


        public TypeShowViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(List<TypeShowBean.DataBean.ItemsBean> datas, int position) {

            TypeShowBean.DataBean.ItemsBean itemsBean = datas.get(position);
            String goods_name = itemsBean.getGoods_name();
            String brand_name = itemsBean.getBrand_info().getBrand_name();

            String like_count = itemsBean.getLike_count();

            String discount_price = itemsBean.getDiscount_price();

            String price = itemsBean.getPrice();

            String goods_image = itemsBean.getGoods_image();

            Glide.with(context)
                    .load(goods_image)
                    .into(ivTypeShowIcon);

            tvContent.setText(goods_name);
            tvType.setText(brand_name);
//            tvPrice.setText(discount_price);
            tvPrice.setText("¥" + price);
            tvZan.setText(like_count);


        }
    }


    public void refreshData(List<TypeShowBean.DataBean.ItemsBean> itemsBeanList, int pager) {

        if (itemsBeanList != null && itemsBeanList.size() > 0) {
            if (pager == 1) {
                datas.clear();
                datas.addAll(itemsBeanList);
                notifyDataSetChanged();
            } else {

                datas.addAll(itemsBeanList);
                notifyDataSetChanged();
            }


        }

    }

    public interface onPagerClickListener {
        void onPagerListener(int position);
    }

    public void setOnPagerClickListener(onPagerClickListener listener) {

        this.listener = listener;
    }


}
