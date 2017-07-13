package com.wjk2288.liangbin.activity.shop.adapter.typepageradapter;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
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

    private int lastAnimatedPosition = -1;
    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;
    private int itemCount = 0;


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

        runEnterAnimation(holder.itemView, position);

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

    private void runEnterAnimation(View view, int position) {

        if (animationsLocked) return;//animationsLocked是布尔类型变量，一开始为false，确保仅屏幕一开始能够显示的item项才开启动画

        if (position >= lastAnimatedPosition) {//lastAnimatedPosition是int类型变量，一开始为-1，这两行代码确保了recycleview滚动式回收利用视图时不会出现不连续的效果
            lastAnimatedPosition = position;
            view.setTranslationY(250);//相对于原始位置下方400
            view.setAlpha(0.f);//完全透明
            //每个item项两个动画，从透明到不透明，从下方移动到原来的位置
            //并且根据item的位置设置延迟的时间，达到一个接着一个的效果
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 40 * (position) : 0)//根据item的位置设置延迟时间，达到依次动画一个接一个进行的效果
                    .setInterpolator(new DecelerateInterpolator(0.6f))//设置动画效果为在动画开始的地方快然后慢
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
//                            animationsLocked = false;//确保仅屏幕一开始能够显示的item项才开启动画，也就是说屏幕下方还没有显示的item项滑动时是没有动画效果
                        }
                    })
                    .start();
        }
    }


}