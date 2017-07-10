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
import com.wjk2288.liangbin.activity.shop.bean.details.BrandDetailsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/10.
 */

public class BrandDetailsAdapter extends RecyclerView.Adapter<BrandDetailsAdapter.BrandDetailsViewHodlre> {


    private ArrayList<BrandDetailsBean.DataBean.ItemsBean> datas = new ArrayList<>();
    private Context context;
    private onItemClickListener listener;
    private int position;

    public BrandDetailsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BrandDetailsViewHodlre onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_typeshow_item, parent, false);
//        View view = View.inflate(context, R.layout.fragment_typeshow_item, null);
        return new BrandDetailsViewHodlre(view);
    }

    @Override
    public void onBindViewHolder(BrandDetailsViewHodlre holder, int position) {
        this.position =position;
        holder.setData(datas, position);

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class BrandDetailsViewHodlre extends RecyclerView.ViewHolder {

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


        public BrandDetailsViewHodlre(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(ArrayList<BrandDetailsBean.DataBean.ItemsBean> datas, final int position) {

            BrandDetailsBean.DataBean.ItemsBean bean = datas.get(position);



            String goods_image = bean.getGoods_image();
            if (datas != null && datas.size() >= 0) {

                Glide.with(context)
                        .load(goods_image)
                        .into(ivTypeShowIcon);

                tvContent.setText(bean.getGoods_name());
                tvPrice.setText(bean.getPrice());
                tvZan.setText(bean.getLike_count());
                tvType.setText(bean.getBrand_info().getBrand_name());

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(position);
                    }
                });

            }


        }
    }

    public int getPosition(){

        return position;
    }


    public void refreshData(List<BrandDetailsBean.DataBean.ItemsBean> itemsBeanList) {
        if (itemsBeanList != null && itemsBeanList.size() >= 0) {
            datas.clear();
            datas.addAll(itemsBeanList);
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
