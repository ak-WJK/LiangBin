package com.wjk2288.liangbin.activity.shared.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shared.bean.SatinBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/7/16.
 */

public class SatinAdapter extends BaseAdapter {
    private Context context;

    private List<SatinBean.ListBean> datas = new ArrayList<>();

    public SatinAdapter(Context context) {

        this.context = context;
    }


    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.pager_shared_satin_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        String userIcon = datas.get(position).getU().getHeader().get(0);
        String userName = datas.get(position).getU().getName();
        String passtime = datas.get(position).getPasstime();

        String text = datas.get(position).getText();


        Glide.with(context).load(userIcon)
                .bitmapTransform(new RoundedCornersTransformation(context, 360, 0))
                .into(viewHolder.ivUserIcon);


        viewHolder.tvUserName.setText(userName);
        viewHolder.tvUpdataTime.setText(passtime);
        viewHolder.tvImageName.setText(text);


        String up = datas.get(position).getUp();
        int down = datas.get(position).getDown();
        int forward = datas.get(position).getForward();

        viewHolder.tvCai.setText(down + "");
        viewHolder.tvZan.setText(up);
        viewHolder.tvPinglun.setText(forward + "");


//        String content = datas.get(position).getTop_comments().get(0).getContent();
//        viewHolder.pinglunUserContent.setText(content);


        String name = datas.get(position).getU().getName();
        viewHolder.pinglunUserName.setText(name);


        return convertView;
    }


    public void refreshData(List<SatinBean.ListBean> satinBean) {
        if (satinBean != null) {

            datas.clear();
            datas.addAll(satinBean);
            notifyDataSetChanged();


        }

    }


    static class ViewHolder {
        @Bind(R.id.iv_user_icon)
        ImageView ivUserIcon;
        @Bind(R.id.tv_user_name)
        TextView tvUserName;
        @Bind(R.id.tv_updata_time)
        TextView tvUpdataTime;
        @Bind(R.id.tv_image_name)
        TextView tvImageName;
        @Bind(R.id.tv_zan)
        TextView tvZan;
        @Bind(R.id.tv_cai)
        TextView tvCai;
        @Bind(R.id.tv_shared)
        TextView tvShared;
        @Bind(R.id.tv_pinglun)
        TextView tvPinglun;
        @Bind(R.id.pinglun_user_name)
        TextView pinglunUserName;
        @Bind(R.id.pinglun_user_content)
        TextView pinglunUserContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
