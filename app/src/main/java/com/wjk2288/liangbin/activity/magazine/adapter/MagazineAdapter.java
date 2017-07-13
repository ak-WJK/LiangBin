package com.wjk2288.liangbin.activity.magazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.magazine.bean.MagazineBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/13.
 */

public class MagazineAdapter extends RecyclerView.Adapter<MagazineAdapter.MagazineViewHodler> {

    private Context context;

    private ArrayList<MagazineBean> datas = new ArrayList<>();
    private onTimeChangedClickListener listener;
    boolean delayedTime = true;
    private String monthInfo;

    private int position;

    public MagazineAdapter(Context context) {

        this.context = context;
    }

    @Override
    public MagazineViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_magazine_item, parent, false);
        return new MagazineViewHodler(view);
    }

    @Override
    public void onBindViewHolder(MagazineViewHodler holder, final int position) {
        this.position = position;
        holder.setData(position, datas);
        itemAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTimeClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return datas == null ? 0 : datas.size();
    }


    class MagazineViewHodler extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_magazine_icon)
        ImageView ivMagazineIcon;
        @Bind(R.id.tv_magazine_miaoshu)
        TextView tvMagazineMiaoshu;
        @Bind(R.id.tv_magazine_type)
        TextView tvMagazineType;
        @Bind(R.id.tv_magazine_time)
        TextView tvMagazineTime;

        public MagazineViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setData(int position, ArrayList<MagazineBean> datas) {

            String cover_img_new = datas.get(position).getCover_img_new();
            ivMagazineIcon.setImageAlpha(99);
            Glide.with(context).load(cover_img_new).into(ivMagazineIcon);

            String topic_name = datas.get(position).getTopic_name();
            tvMagazineMiaoshu.setText(topic_name);

            String cat_name = datas.get(position).getCat_name();
            tvMagazineType.setText("--- " + cat_name + " ---");

            monthInfo = datas.get(position).getMonthInfo();
            String date = monthInfo.substring(5);


            if (position == 0) {
                tvMagazineTime.setVisibility(View.VISIBLE);

            } else {

                String preMonthInfo = datas.get(position - 1).getMonthInfo();
                if (monthInfo.equals(preMonthInfo)) {
                    tvMagazineTime.setVisibility(View.GONE);

                } else {
                    tvMagazineTime.setVisibility(View.VISIBLE);
                }
            }

            tvMagazineTime.setText("---  " + date + "  ---");


        }
    }

    public void refreshData(ArrayList<MagazineBean> mgzBeanList) {

        if (mgzBeanList != null && mgzBeanList.size() >= 0) {
            datas.clear();
            datas.addAll(mgzBeanList);
            notifyDataSetChanged();
        }


    }


    //动画效果

    public void itemAnimation(View itemView, int position) {

        itemView.setTranslationY(250);//动画的距离
        itemView.setAlpha(0f);//设置透明

        itemView.animate()
                .translationY(0)
                .alpha(1f)
                .setStartDelay(delayedTime ? 40 * (position) : 0)
                .setInterpolator(new DecelerateInterpolator(0.6f))
                .setDuration(200)
                .start();


    }


    public interface onTimeChangedClickListener {
        void onTimeClick(int position);
    }


    public void setOnTimeChangedClickListener(onTimeChangedClickListener listener) {

        this.listener = listener;
    }

    public int getPosition() {
        return position;
    }

}

