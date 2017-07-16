package com.wjk2288.liangbin.activity.shared.viewhodler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shared.bean.RecommedBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/7/16.
 */

public class TextHodler extends RecyclerView.ViewHolder {


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

    public TextHodler(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(int position, List<RecommedBean.ListBean> datas, Context context) {

        //用户名称
        String name = datas.get(position).getU().getName();

        String headerUrl = datas.get(position).getU().getHeader().get(0);
        String passtime = datas.get(position).getPasstime();
        String text = datas.get(position).getText();
        tvImageName.setText(text);
        tvUserName.setText(name);
        tvUpdataTime.setText(passtime);
        Glide.with(context)
                .load(headerUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, 360, 0))
                .into(ivUserIcon);

        //赞
        String up = datas.get(position).getUp();
        String share_url = datas.get(position).getShare_url();
        int down = datas.get(position).getDown();
        int forward = datas.get(position).getForward();

        String cai = String.valueOf(down);
        String fenxiang = String.valueOf(forward);

        tvZan.setText(up);

        tvCai.setText(cai);
        tvShared.setText(fenxiang);

        //评论
        RecommedBean.ListBean.TopCommentsBean.UBeanXX uBeanXX = null;
        try {

            uBeanXX = datas.get(position).getTop_comments().get(position).getU();

            String pingLunName = uBeanXX.getName();

            pinglunUserName.setText(pingLunName);


            String content = null;

            content = datas.get(position).getTop_comments().get(position).getContent();


            tvPinglun.setText(content);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
