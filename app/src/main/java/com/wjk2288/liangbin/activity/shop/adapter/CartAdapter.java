package com.wjk2288.liangbin.activity.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.bean.CartBean;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/20.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHodler> {


    private ArrayList<CartBean> datas = new ArrayList<>();
    private Context context;
    private onClickItemListener listener;
    private TextView tvCartTotalprice;
    private CheckBox cbCartSelect;

    public CartAdapter(Context context, TextView tvCartTotalprice, CheckBox cbCartSelect) {
        this.context = context;
        this.tvCartTotalprice = tvCartTotalprice;
        this.cbCartSelect = cbCartSelect;

        //显示总价格
        showTotalPrice();

        //是否全选
        allCheck();
    }

    @Override
    public CartHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, null);
        return new CartHodler(view);

    }

    @Override
    public void onBindViewHolder(final CartHodler holder, final int position) {
        holder.setData(position, datas);

        //设置默认checkBox为选中状态
        final CartBean cartBean = datas.get(position);
        holder.cbCheckbox.setChecked(cartBean.isChecked());

        //设置checkBox的状态跟随item的点击改变
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartBean.setChecked(!cartBean.isChecked());
                notifyDataSetChanged();

                //显示总价格
                showTotalPrice();
                //是否全选
                allCheck();
            }
        });


    }


    @Override
    public int getItemCount() {

        return datas.size();
    }

    public void refreshData(ArrayList<CartBean> cartGoods) {
        if (cartGoods != null) {
            datas.clear();
            datas.addAll(cartGoods);
            notifyDataSetChanged();
        }


    }


    class CartHodler extends RecyclerView.ViewHolder {

        @Bind(R.id.cb_checkbox)
        CheckBox cbCheckbox;
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_goods_content)
        TextView tvGoodsContent;
        @Bind(R.id.tv_goods_attr)
        TextView tvGoodsAttr;
        @Bind(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @Bind(R.id.tv_goods_number)
        TextView tvGoodsNumber;

        public CartHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        public void setData(int position, ArrayList<CartBean> datas) {

            String imageUrl = datas.get(position).getImageUrl();

            Glide.with(context).load(imageUrl).into(ivIcon);

            String goodsName = datas.get(position).getGoodsName();
            tvGoodsAttr.setText(goodsName);

            String goodsContent = datas.get(position).getGoodsContent();
            tvGoodsContent.setText(goodsContent);

            String goodsPrice = datas.get(position).getGoodsPrice();
            tvGoodsPrice.setText("￥" + goodsPrice);

            int goodsNumber = datas.get(position).getGoodsNumber();

            tvGoodsNumber.setText("x" + goodsNumber);
            String attrName = datas.get(position).getAttrName();

            tvGoodsAttr.setText(attrName);


        }
    }


    //显示价格
    public void showTotalPrice() {
        double totalPrice = getTotalPrice();
        tvCartTotalprice.setText("总计:" + "￥" + totalPrice);

    }


    //计算价格
    public double getTotalPrice() {
        double result = 0;
        if (datas != null && datas.size() >= 0) {
            for (int i = 0; i < datas.size(); i++) {
                CartBean cartBean = datas.get(i);
                String goodsPrice = cartBean.getGoodsPrice();
                LogUtils.e("TAG", "goodsPrice==" + goodsPrice);

                int goodsNumber = cartBean.getGoodsNumber();
                LogUtils.e("TAG", "goodsNumber== " + goodsNumber);

                if (cartBean.isChecked()) {
                    result = result + Double.parseDouble(goodsPrice) * goodsNumber;
                }
            }
        }

        return result;
    }

    //是否全选
    public void allCheck() {

        if (datas != null && datas.size() >= 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                CartBean cartBean = datas.get(i);
                if (!cartBean.isChecked()) {
                    cbCartSelect.setChecked(false);
                } else {
                    number++;
                }
            }

            if (number == datas.size()) {
                cbCartSelect.setChecked(true);
            }


        } else {

            cbCartSelect.setChecked(false);
        }


    }

    //全部取消选中
    public void allCheckNone(boolean isCheck) {
        if (datas != null && datas.size() >= 0) {
            for (int i = 0; i < datas.size(); i++) {
                CartBean cartBean = datas.get(i);
                cartBean.setChecked(isCheck);
                notifyItemChanged(i);
            }
        } else {
            cbCartSelect.setChecked(false);
        }


    }


    public interface onClickItemListener {
        void onItemClick(int position);

    }

    public void setOnClickItemListener(onClickItemListener listener) {

        this.listener = listener;
    }


}
