package com.wjk2288.liangbin.activity.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.bean.HomeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/7.
 */

public class HomeAdapter extends RecyclerView.Adapter {


    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_FOUR = 4;


    private Context context;

    private List<HomeBean.DataBean.ItemsBean.ListBean> listBeanLists = new ArrayList<>();

    public HomeAdapter(Context context) {
        this.context = context;

    }

    public void refreshData(List<HomeBean.DataBean.ItemsBean.ListBean> listBeanList) {

        listBeanLists.clear();

        listBeanLists.addAll(listBeanList);
        notifyDataSetChanged();

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
//
        switch (viewType) {
            case TYPE_ONE:
                viewHolder = new TypeOneViewHodler(LayoutInflater.from(context).inflate(R.layout.pager_home_item_typeone, parent, false));
                break;
            case TYPE_TWO:
                viewHolder = new TypeTwoViewHodler(LayoutInflater.from(context).inflate(R.layout.pager_home_item_typetwo, parent, false));
                break;
            case TYPE_FOUR:
                viewHolder = new TypeFourViewHodler(LayoutInflater.from(context).inflate(R.layout.pager_home_item_typefour, parent, false));
                break;

        }


//        if (viewType == TYPE_ONE) {
//
//            return new TypeOneViewHodler(View.inflate(context, R.layout.pager_home_item_typeone, null));
//        }

//        if (viewType == TYPE_TWO) {
//            return new TypeTwoViewHodler(View.inflate(context, R.layout.pager_home_item_typetwo, null));
//        }
//
//        if (viewType == TYPE_FOUR) {
//            return new TypeFourViewHodler(View.inflate(context, R.layout.pager_home_item_typefour, null));
//        }
//        return  null;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ONE) {
            TypeOneViewHodler typeOneViewHodler = (TypeOneViewHodler) holder;
            typeOneViewHodler.setData(listBeanLists, position);
        } else if (getItemViewType(position) == TYPE_TWO) {
            TypeTwoViewHodler typeTwoViewHodler = (TypeTwoViewHodler) holder;
            typeTwoViewHodler.setData(listBeanLists, position);
        } else if (getItemViewType(position) == TYPE_FOUR) {
            TypeFourViewHodler typeFourViewHodler = (TypeFourViewHodler) holder;
            typeFourViewHodler.setData(listBeanLists, position);

        }

    }

    @Override
    public int getItemCount() {

        return listBeanLists == null ? 0 : listBeanLists.size();

    }

    @Override
    public int getItemViewType(int position) {

        //要先得到数据类型
        String home_type = listBeanLists.get(position).getHome_type();

        int TYPE = Integer.parseInt(home_type);
        switch (TYPE) {

            case TYPE_ONE:
                TYPE = TYPE_ONE;
                break;
            case TYPE_TWO:
                TYPE = TYPE_TWO;
                break;
            case TYPE_FOUR:
                TYPE = TYPE_FOUR;
                break;
        }

        return TYPE;

    }


    class TypeOneViewHodler extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_home_oneicon)
        ImageView imageView;

        public TypeOneViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(List<HomeBean.DataBean.ItemsBean.ListBean> BeanList, int position) {

            HomeBean.DataBean.ItemsBean.ListBean bean = BeanList.get(position);

            String pic_url = bean.getOne().getPic_url();
            Glide.with(context)
                    .load(pic_url)
                    .into(imageView);


        }
    }


    class TypeTwoViewHodler extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_home_typeone)
        ImageView ivHomeTypeOne;
        @Bind(R.id.iv_home_typetwo)
        ImageView ivHomeTypeTwo;

        public TypeTwoViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(List<HomeBean.DataBean.ItemsBean.ListBean> listBeanLists, int position) {

            HomeBean.DataBean.ItemsBean.ListBean bean = listBeanLists.get(position);


            String pic_url1 = bean.getOne().getPic_url();

            Glide.with(context)
                    .load(pic_url1)
                    .into(ivHomeTypeOne);

            String pic_url2 = bean.getTwo().getPic_url();
            Glide.with(context)
                    .load(pic_url2)
                    .into(ivHomeTypeTwo);

        }
    }


    class TypeFourViewHodler extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_home_type1)
        ImageView imageViewone;
        @Bind(R.id.iv_home_type2)
        ImageView imageViewtwo;
        @Bind(R.id.iv_home_type3)
        ImageView imageViewthree;
        @Bind(R.id.iv_home_type4)
        ImageView imageViewfour;


        public TypeFourViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(List<HomeBean.DataBean.ItemsBean.ListBean> listBeanLists, int position) {

            HomeBean.DataBean.ItemsBean.ListBean bean = listBeanLists.get(position);

            String pic_url1 = bean.getOne().getPic_url();
            Glide.with(context)
                    .load(pic_url1)
                    .into(imageViewone);

            String pic_url2 = bean.getTwo().getPic_url();
            Glide.with(context)
                    .load(pic_url2)
                    .into(imageViewtwo);

            String pic_url3 = bean.getThree().getPic_url();
            Glide.with(context)
                    .load(pic_url3)
                    .into(imageViewthree);

            String pic_url4 = bean.getFour().getPic_url();
            Glide.with(context)
                    .load(pic_url4)
                    .into(imageViewfour);


        }
    }


}
