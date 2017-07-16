package com.wjk2288.liangbin.activity.magazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.magazine.bean.MagazineTypeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/14.
 */

public class MagazineTypeAdapter extends RecyclerView.Adapter<MagazineTypeAdapter.TypeHodler> {


    private List<MagazineTypeBean.DataBean.ItemsBean> datas = new ArrayList<>();

    private Context context;

    public MagazineTypeAdapter(Context context) {

        this.context = context;
    }

    @Override
    public TypeHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.magazine_type_pager_item, parent, false);
        return new TypeHodler(view);
    }

    @Override
    public void onBindViewHolder(TypeHodler holder, int position) {

        holder.setData(position, datas);

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    class TypeHodler extends RecyclerView.ViewHolder {


        @Bind(R.id.type_icon)
        ImageView typeIcon;
        @Bind(R.id.type_content)
        TextView typeContent;


        public TypeHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position, List<MagazineTypeBean.DataBean.ItemsBean> datas) {
            String cat_name = datas.get(position).getCat_name();
            String thumb = datas.get(position).getThumb();

            typeContent.setText(cat_name);

//            typeIcon.setImageAlpha(99);

            Glide.with(context)
                    .load(thumb)
                    .into(typeIcon);


        }
    }

    public void refreshData(List<MagazineTypeBean.DataBean.ItemsBean> itemsBeanList) {
        if (itemsBeanList != null && itemsBeanList.size() >= 0) {
            datas.clear();
            datas.addAll(itemsBeanList);
            notifyDataSetChanged();

        }


    }

}
