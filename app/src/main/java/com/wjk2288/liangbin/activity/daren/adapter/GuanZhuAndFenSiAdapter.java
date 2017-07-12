package com.wjk2288.liangbin.activity.daren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.daren.bean.renqibean.FenSiBean;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/12.
 */

public class GuanZhuAndFenSiAdapter extends RecyclerView.Adapter<GuanZhuAndFenSiAdapter.GuanZhuAndFenSiAdapterViewHodler> {


    private ArrayList<FenSiBean.DataBean.ItemsBean.UsersBean> datas = new ArrayList<>();

    private Context context;

    public GuanZhuAndFenSiAdapter(Context context) {

        this.context = context;
    }

    @Override
    public GuanZhuAndFenSiAdapterViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daren_details_pager_item_guanzhu, parent, false);
        return new GuanZhuAndFenSiAdapterViewHodler(view);
    }

    @Override
    public void onBindViewHolder(GuanZhuAndFenSiAdapterViewHodler holder, int position) {
        holder.setData(position, datas);

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void refreshDataFenSi(List<FenSiBean.DataBean.ItemsBean.UsersBean> usersBeanList) {
        if (usersBeanList != null) {
            LogUtils.e("TAG", "usersDatas == " + usersBeanList.size());
            datas.clear();
            datas.addAll(usersBeanList);
            notifyDataSetChanged();

        }
    }


    class GuanZhuAndFenSiAdapterViewHodler extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_fensi)
        ImageView ivFensi;
        @Bind(R.id.tv_nicheng)
        TextView tvNicheng;
        @Bind(R.id.ll)
        LinearLayout ll;

        public GuanZhuAndFenSiAdapterViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position, ArrayList<FenSiBean.DataBean.ItemsBean.UsersBean> datas) {

            String orig = datas.get(position).getUser_image().getOrig();

            String user_name = datas.get(position).getUser_name();
            LogUtils.e("TAG", "username" + user_name);

            Glide.with(context)
                    .load(orig)
                    .into(ivFensi);
            tvNicheng.setText(user_name);


        }
    }

}
