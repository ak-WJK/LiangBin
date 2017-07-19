package com.wjk2288.liangbin.activity.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.bean.CartBean;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/20.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHodler> {

    private ArrayList<CartBean> datas = new ArrayList<>();
    private Context context;

    public CartAdapter(Context context) {


        this.context = context;
    }

    @Override
    public CartHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, null);
        return new CartHodler(view);
    }

    @Override
    public void onBindViewHolder(CartHodler holder, int position) {
        holder.setData(position,datas);

    }

    @Override
    public int getItemCount() {
        LogUtils.e("TAG", "datas === " + datas.size());
        return datas.size();
    }

    public void refreshData(CartBean cartGoods) {
        if (cartGoods != null) {
            datas.clear();
            datas.add(cartGoods);
            notifyDataSetChanged();
        }


    }


    class CartHodler extends RecyclerView.ViewHolder {

        public CartHodler(View itemView) {
            super(itemView);
        }

        public void setData(int position, ArrayList<CartBean> datas) {


        }
    }
}
