package com.wjk2288.liangbin.activity.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.bean.DaRenBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/9.
 */

public class DarenAdapter extends RecyclerView.Adapter<DarenAdapter.DarenViewHodler> {


    private List<DaRenBean.DataBean.ItemsBean> datas = new ArrayList<>();
    private Context context;

    public DarenAdapter(Context context) {
        this.context = context;
    }

    @Override
    public DarenViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //此方式布局会使
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_daren_item, parent, false);
//        View view = View.inflate(context, R.layout.fragment_daren_item, null);
        return new DarenViewHodler(view);
    }

    @Override
    public void onBindViewHolder(DarenViewHodler holder, int position) {
        holder.setData(datas, position);

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    class DarenViewHodler extends RecyclerView.ViewHolder {


        @Bind(R.id.iv_daren_icon)
        ImageView ivDarenIcon;
        @Bind(R.id.tv_daren_name)
        TextView tvDarenName;
        @Bind(R.id.tv_daren_content)
        TextView tvDarenContent;


        public DarenViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(List<DaRenBean.DataBean.ItemsBean> datas, int position) {
            if (datas != null && datas.size() >= 0) {
                DaRenBean.DataBean.ItemsBean itemsBean = datas.get(position);

                String username = itemsBean.getUsername();
                String duty = itemsBean.getDuty();
                DaRenBean.DataBean.ItemsBean.UserImagesBean user_images = itemsBean.getUser_images();
                String orig = user_images.getOrig();

                tvDarenName.setText(username);
                tvDarenContent.setText(duty);

                Glide.with(context)
                        .load(orig)
                        .into(ivDarenIcon);


            }


        }
    }


    public void refreshData(List<DaRenBean.DataBean.ItemsBean> itemsBeanList, int pager) {
        if (itemsBeanList != null && itemsBeanList.size() >= 0) {
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


}
