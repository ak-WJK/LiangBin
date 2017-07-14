package com.wjk2288.liangbin.activity.magazine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.magazine.bean.MagazineAutherBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/7/14.
 */

public class MagazineAutherAdapter extends BaseAdapter {
    private Context context;
    private List<MagazineAutherBean.DataBean.ItemsBean> datas = new ArrayList<>();

    public MagazineAutherAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas == null ? null : datas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.magazine_auther_pager_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MagazineAutherBean.DataBean.ItemsBean itemsBean = datas.get(position);

        String author_name = itemsBean.getAuthor_name();

        String note = itemsBean.getNote();

        String thumb = itemsBean.getThumb();
        Glide.with(context)
                .load(thumb)
                .bitmapTransform(new RoundedCornersTransformation(context, 360, 5))
                .into(viewHolder.ivIcontouxiang);


        viewHolder.tvContent.setText(note);
        viewHolder.tvTextname.setText(author_name);


        return convertView;
    }

    public void refreshData(List<MagazineAutherBean.DataBean.ItemsBean> itemsBeanList) {
        if (itemsBeanList != null && itemsBeanList.size() >= 0) {
            datas.clear();
            datas.addAll(itemsBeanList);
            notifyDataSetChanged();
        }

    }

    static class ViewHolder {
        @Bind(R.id.iv_icontouxiang)
        ImageView ivIcontouxiang;
        @Bind(R.id.tv_textname)
        TextView tvTextname;
        @Bind(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
