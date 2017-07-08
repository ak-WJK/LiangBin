package com.wjk2288.liangbin.activity.shop.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.bean.BrandBean;

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

    public BrandAdapter() {
        itemsBeanList = new ArrayList<>();

    }

    @Override
    public int getCount() {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.pager_brand_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        BrandBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);

        viewHolder.tvBrandName.setText(itemsBean.getBrand_name());
        Picasso.with(context)
                .load(itemsBean.getBrand_logo())
                .into(viewHolder.ivBrandIcon);


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

            }


        }

    }

    static class ViewHolder {
        @Bind(R.id.iv_brand_icon)
        ImageView ivBrandIcon;
        @Bind(R.id.tv_brand_name)
        TextView tvBrandName;
        @Bind(R.id.ib_brand_getinto)
        ImageButton ibBrandGetinto;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }


}