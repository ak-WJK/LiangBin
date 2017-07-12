package com.wjk2288.liangbin.activity.daren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.daren.bean.renqibean.TuiJianBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by Administrator on 2017/7/11.
 */

public class DaRenDetailsAdapter extends RecyclerView.Adapter {


    private Context context;


    private ArrayList<TuiJianBean.DataBean.ItemsBean.GoodsBean> datas = new ArrayList<>();


    private View mHeaderView;

    private DaRenDetailsViewHodler viewHodler;

    private String goods_image;


    public DaRenDetailsAdapter(Context context) {
        this.context = context;


    }


    @Override
    public DaRenDetailsViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {


        mHeaderView = LayoutInflater.from(context).inflate(R.layout.daren_details_pager_item, parent, false);
        return new DaRenDetailsViewHodler(mHeaderView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        viewHodler = (DaRenDetailsViewHodler) holder;
        viewHodler.setData(position, datas);


    }

    @Override
    public int getItemCount() {

        return datas == null ? 0 : datas.size();
    }

    class DaRenDetailsViewHodler extends RecyclerView.ViewHolder {

        public Subscription subscription;

        @Bind(R.id.iv_zhuzuo)
        ImageView ivZhuzuo;


        public DaRenDetailsViewHodler(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setData(final int position, final ArrayList<TuiJianBean.DataBean.ItemsBean.GoodsBean> datas) {


            TuiJianBean.DataBean.ItemsBean.GoodsBean goodsBeen = null;


            goodsBeen = datas.get(position);

            if (goodsBeen != null) {

                String goods_image = goodsBeen.getGoods_image();

                Glide.with(context)
                        .load(goods_image)
                        .into(ivZhuzuo);
            }


        }

    }


    public void refreshData(List<TuiJianBean.DataBean.ItemsBean.GoodsBean> goodsBeenList) {
        if (goodsBeenList != null) {


            datas.clear();
            datas.addAll(goodsBeenList);
            notifyDataSetChanged();

        }


    }


}
