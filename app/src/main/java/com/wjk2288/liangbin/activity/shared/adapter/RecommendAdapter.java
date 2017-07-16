package com.wjk2288.liangbin.activity.shared.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shared.bean.RecommedBean;
import com.wjk2288.liangbin.activity.shared.viewhodler.GifHodler;
import com.wjk2288.liangbin.activity.shared.viewhodler.HtmlHodler;
import com.wjk2288.liangbin.activity.shared.viewhodler.ImageHodler;
import com.wjk2288.liangbin.activity.shared.viewhodler.TextHodler;
import com.wjk2288.liangbin.activity.shared.viewhodler.VideoHodler;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/15.
 */

public class RecommendAdapter extends RecyclerView.Adapter {

    //视频类型
    private static final int TYPE_VIDEO = 0;
    //图片
    private static final int TYPE_IMAGE = 1;
    //文字
    private static final int TYPE_TEXT = 2;
    //GIF
    private static final int TYPE_GIF = 3;
    //html
    private static final int TYPE_HTML = 4;


    private List<RecommedBean.ListBean> datas = new ArrayList<>();

    private Context context;
    private final LayoutInflater inflater;

    public RecommendAdapter(Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    public void refeshData(List<RecommedBean.ListBean> recommedBeanList, int page) {

        if (recommedBeanList != null && recommedBeanList.size() >= 0) {

            LogUtils.e("TAG", "recommendBeanList == " + recommedBeanList.size());

            if (page == 1) {
                datas.clear();
                datas.addAll(recommedBeanList);
                notifyDataSetChanged();


            } else {

                datas.addAll(recommedBeanList);
                notifyDataSetChanged();

            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_VIDEO) {
            View view = inflater.inflate(R.layout.pager_shared_recommend_video_item, parent, false);
            return new VideoHodler(view);
        } else if (viewType == TYPE_IMAGE) {
            View view = inflater.inflate(R.layout.pager_shared_recommend_image_item, parent, false);
            return new ImageHodler(view);

        } else if (viewType == TYPE_GIF) {
            View view = inflater.inflate(R.layout.pager_shared_recommend_gif_item, parent, false);
            return new GifHodler(view);

        } else if (viewType == TYPE_TEXT) {
            View view = inflater.inflate(R.layout.pager_shared_recommend_text_item, parent, false);
            return new TextHodler(view);

        } else if (viewType == TYPE_HTML) {
            View view = inflater.inflate(R.layout.pager_shared_recommend_html_item, parent, false);
            return new HtmlHodler(view);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_VIDEO:
                VideoHodler videoHodler = (VideoHodler) holder;
                videoHodler.setData(position, datas, context);
                break;
            case TYPE_IMAGE:
                ImageHodler imageViewHodler = (ImageHodler) holder;
                imageViewHodler.setData(position, datas, context);
                break;
            case TYPE_GIF:
                GifHodler gifHodler = (GifHodler) holder;
                gifHodler.setData(position, datas, context);
                break;
            case TYPE_TEXT:
                TextHodler textHodler = (TextHodler) holder;
                textHodler.setData(position, datas, context);
                break;
            case TYPE_HTML:
                HtmlHodler htmlHodler = (HtmlHodler) holder;
                htmlHodler.setData(position, datas, context);
                break;


        }


    }


    @Override
    public int getItemCount() {

        return datas == null ? 0 : datas.size();
    }


    @Override
    public int getItemViewType(int position) {

        int itemViewType = -1;


        //此处有坑
//        if (datas != null && datas.size() >= 0) {
//
//            String type = datas.get(position).getType();
//        }


        String type = datas.get(position).getType();

//        LogUtils.e("TAG", "type===" + type);


        if ("video".equals(type)) {

            itemViewType = TYPE_VIDEO;
        } else if ("image".equals(type)) {

            itemViewType = TYPE_IMAGE;
        } else if ("text".equals(type)) {
            itemViewType = TYPE_TEXT;
        } else if ("gif".equals(type)) {

            itemViewType = TYPE_GIF;
        } else if ("html".equals(type)) {

            itemViewType = TYPE_HTML;
        }

        return itemViewType;

    }
}
