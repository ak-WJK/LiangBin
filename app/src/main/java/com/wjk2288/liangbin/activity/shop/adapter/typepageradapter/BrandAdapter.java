package com.wjk2288.liangbin.activity.shop.adapter.typepageradapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.bean.typepagerbean.BrandBean;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.wjk2288.liangbin.activity.shop.base.BasePager.context;

/**
 * Created by Administrator on 2017/7/8.
 */

public class BrandAdapter extends BaseAdapter {


    private List<BrandBean.DataBean.ItemsBean> itemsBeanList;
    private onItemClickListener listener;

    public BrandAdapter() {
        itemsBeanList = new ArrayList<>();

    }

    @Override
    public int getCount() {
        LogUtils.e("TAG", "itesize 2222 ==" + itemsBeanList.size());
        return itemsBeanList == null ? 0 : itemsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.pager_brand_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final BrandBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);

        viewHolder.tvBrandName.setText(itemsBean.getBrand_name());
        Picasso.with(context)
                .load(itemsBean.getBrand_logo())
                .into(viewHolder.ivBrandIcon);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position, itemsBeanList);
                }

            }
        });


        return convertView;
    }

    public void refreshData(List<BrandBean.DataBean.ItemsBean> beanList, int pager) {

        if (beanList != null && beanList.size() >= 0) {

            if (pager == 1) {

                itemsBeanList.clear();
                itemsBeanList.addAll(beanList);
                notifyDataSetChanged();

            } else {

                itemsBeanList.addAll(beanList);
                notifyDataSetChanged();

                LogUtils.e("TAG", "itesize==" + itemsBeanList.size());

            }


        }

    }

    static class ViewHolder {
        @Bind(R.id.iv_brand_icon)
        ImageView ivBrandIcon;
        @Bind(R.id.tv_brand_name)
        TextView tvBrandName;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }


    public interface onItemClickListener {
        void onItemClick(int position, List<BrandBean.DataBean.ItemsBean> itemsBeanList);
    }

    public void setOnItemClickListener(onItemClickListener listener) {

        this.listener = listener;
    }


}