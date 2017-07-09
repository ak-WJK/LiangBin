package com.wjk2288.liangbin.activity.shop.adapter.typepageradapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.bean.typepagerbean.SpecialBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.wjk2288.liangbin.activity.shop.base.BasePager.context;

/**
 * Created by Administrator on 2017/7/8.
 */

public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.SpecialViewHodler> {


    List<SpecialBean.DataBean.ItemsBean> itemsBeanList;

    public SpecialAdapter() {
        itemsBeanList = new ArrayList<>();
    }

    @Override
    public SpecialViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = inflate(context, R.layout.pager_special_item, null);
        View view = LayoutInflater.from(context).inflate(R.layout.pager_special_item, parent, false);

        return new SpecialViewHodler(view);
    }

    @Override
    public void onBindViewHolder(SpecialViewHodler holder, int position) {
        holder.setData(itemsBeanList, position);
    }

    @Override
    public int getItemCount() {
        return itemsBeanList == null ? 0 : itemsBeanList.size();
    }

    public void refreshData(List<SpecialBean.DataBean.ItemsBean> datas, int pager) {
        if (datas != null && datas.size() > 0) {
            if (pager == 1) {
                itemsBeanList.clear();
                itemsBeanList.addAll(datas);
                notifyDataSetChanged();

            } else {

                itemsBeanList.addAll(datas);
                notifyDataSetChanged();


            }
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

        public void setData(List<SpecialBean.DataBean.ItemsBean> itemsBeanList, int position) {
            SpecialBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);
            tvSpecialIconcontent.setText(itemsBean.getTopic_name());
            Glide.with(context)
                    .load(itemsBean.getCover_img_new())
                    .into(ivSpecialIcon);


        }


    }
}