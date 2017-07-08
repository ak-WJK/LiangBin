package com.wjk2288.liangbin.activity.shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.wjk2288.liangbin.activity.shop.base.BasePager.context;

/**
 * Created by Administrator on 2017/7/8.
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeViewHodler> {


    private List<TypeBean.DataBean.ItemsBean> beanList = new ArrayList<>();
    public onItemClickListener listener;

    @Override
    public TypeViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_type_item, parent, false);
        return new TypeViewHodler(view);
    }

    @Override
    public void onBindViewHolder(TypeViewHodler holder, final int position) {
        holder.setData(position, beanList);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return beanList == null ? 0 : beanList.size();
    }

    static class TypeViewHodler extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_type_icon)
        ImageView ivTypeIcon;


        public TypeViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setData(int position, List<TypeBean.DataBean.ItemsBean> beanList) {

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


    public void refreshData(List<TypeBean.DataBean.ItemsBean> datas) {
        if (datas != null && datas.size() > 0) {
            beanList.clear();
            beanList.addAll(datas);
            notifyDataSetChanged();

        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {

        this.listener = listener;
    }


}
