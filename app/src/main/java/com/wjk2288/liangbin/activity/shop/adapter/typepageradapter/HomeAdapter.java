package com.wjk2288.liangbin.activity.shop.adapter.typepageradapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.shop.activity.HomeDetailsActivity;
import com.wjk2288.liangbin.activity.shop.bean.typepagerbean.HomeBean;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

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
    private static final int TYPE_SIX = 6;


    private Context context;

    private List<HomeBean.DataBean.ItemsBean.ListBeanX> listBeanLists = new ArrayList<>();


    public HomeAdapter(Context context) {
        this.context = context;
    }

    public void refreshData(List<HomeBean.DataBean.ItemsBean.ListBeanX> beanList, int pager) {

        if (beanList != null & beanList.size() >= 0) {
            if (pager == 1) {

                listBeanLists.clear();
                listBeanLists.addAll(beanList);
                notifyDataSetChanged();

            } else {

                listBeanLists.addAll(beanList);
                notifyDataSetChanged();

            }

        }


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
            case TYPE_SIX:
                viewHolder = new TypeSixViewHodler(LayoutInflater.from(context).inflate(R.layout.pager_home_item_typesix, parent, false));
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

        HomeBean.DataBean.ItemsBean.ListBeanX listBeanX = listBeanLists.get(position);

        if (getItemViewType(position) == TYPE_ONE) {
            TypeOneViewHodler typeOneViewHodler = (TypeOneViewHodler) holder;
            typeOneViewHodler.setData(listBeanX, position);
        } else if (getItemViewType(position) == TYPE_TWO) {
            TypeTwoViewHodler typeTwoViewHodler = (TypeTwoViewHodler) holder;
            typeTwoViewHodler.setData(listBeanX, position);
        } else if (getItemViewType(position) == TYPE_FOUR) {
            TypeFourViewHodler typeFourViewHodler = (TypeFourViewHodler) holder;
            typeFourViewHodler.setData(listBeanX, position);
        } else if (getItemViewType(position) == TYPE_SIX) {
            TypeSixViewHodler typeSixViewHodler = (TypeSixViewHodler) holder;
            typeSixViewHodler.setData(listBeanX, position);
        }

    }

    @Override
    public int getItemCount() {

        return listBeanLists == null ? 0 : listBeanLists.size();

    }

    @Override
    public int getItemViewType(int position) {

        //要先得到数据类型
        int TYPE = listBeanLists.get(position).getHome_type();


//        int TYPE = Integer.parseInt(home_type);
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
            case TYPE_SIX:
                TYPE = TYPE_SIX;
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

        public void setData(final HomeBean.DataBean.ItemsBean.ListBeanX listBeanX, final int position) {

            String pic_url = listBeanX.getOne().getPic_url();


            Glide.with(context)
                    .load(pic_url)
                    .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String topic_url = listBeanX.getOne().getTopic_url();
                    String title = listBeanX.getOne().getTopic_name();
                    Intent intent = new Intent(context, HomeDetailsActivity.class);
                    intent.putExtra("url", topic_url);
                    intent.putExtra("name", title);
                    context.startActivity(intent);

                }
            });


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

        public void setData(final HomeBean.DataBean.ItemsBean.ListBeanX listBeanX, final int position) {


            String pic_url1 = listBeanX.getOne().getPic_url();


            Glide.with(context)
                    .load(pic_url1)
                    .into(ivHomeTypeOne);

            String pic_url2 = listBeanX.getTwo().getPic_url();
            Glide.with(context)
                    .load(pic_url2)
                    .into(ivHomeTypeTwo);


            ivHomeTypeOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String topic_url = listBeanX.getOne().getTopic_url();
                    String title = listBeanX.getOne().getTopic_name();
                    Intent intent = new Intent(context, HomeDetailsActivity.class);
                    intent.putExtra("url", topic_url);
                    intent.putExtra("name", title);
                    context.startActivity(intent);


                }
            });
            ivHomeTypeTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String topic_url = listBeanX.getTwo().getTopic_url();
                    String title = listBeanX.getTwo().getTopic_name();
                    Intent intent = new Intent(context, HomeDetailsActivity.class);
                    intent.putExtra("url", topic_url);
                    intent.putExtra("name", title);
                    context.startActivity(intent);

                }
            });


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

        public void setData(final HomeBean.DataBean.ItemsBean.ListBeanX listBeanX, final int position) {

            String pic_url1 = listBeanX.getOne().getPic_url();

            Glide.with(context)
                    .load(pic_url1)
                    .into(imageViewone);

            String pic_url2 = listBeanX.getTwo().getPic_url();
            Glide.with(context)
                    .load(pic_url2)
                    .into(imageViewtwo);

            String pic_url3 = listBeanX.getThree().getPic_url();
            Glide.with(context)
                    .load(pic_url3)
                    .into(imageViewthree);

            String pic_url4 = listBeanX.getFour().getPic_url();
            Glide.with(context)
                    .load(pic_url4)
                    .into(imageViewfour);


            imageViewone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String topic_url = listBeanX.getOne().getTopic_url();
                    String title = listBeanX.getOne().getTopic_name();
                    Intent intent = new Intent(context, HomeDetailsActivity.class);
                    intent.putExtra("url", topic_url);
                    intent.putExtra("name", title);
                    context.startActivity(intent);


                }
            });
            imageViewtwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String topic_url = listBeanX.getTwo().getTopic_url();
                    String title = listBeanX.getTwo().getTopic_name();
                    Intent intent = new Intent(context, HomeDetailsActivity.class);
                    intent.putExtra("url", topic_url);
                    intent.putExtra("name", title);
                    context.startActivity(intent);


                }
            });


            imageViewthree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String topic_url = listBeanX.getThree().getTopic_url();
                    String title = listBeanX.getThree().getTopic_name();
                    Intent intent = new Intent(context, HomeDetailsActivity.class);
                    intent.putExtra("url", topic_url);
                    intent.putExtra("name", title);
                    context.startActivity(intent);


                }
            });
            imageViewfour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String topic_url = listBeanX.getFour().getTopic_url();
                    String title = listBeanX.getFour().getTopic_name();
                    Intent intent = new Intent(context, HomeDetailsActivity.class);
                    intent.putExtra("url", topic_url);
                    intent.putExtra("name", title);
                    context.startActivity(intent);


                }
            });


        }
    }


    class TypeSixViewHodler extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_home_sixicon)
        ImageView ivHomeSixicon;
        @Bind(R.id.banner)
        Banner banner;


        public TypeSixViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setData(final HomeBean.DataBean.ItemsBean.ListBeanX listBeanX, final int position) {

            ArrayList<String> images = new ArrayList<>();
            String pic_url = listBeanX.getList().get(0).getPic_url();
            String pic_url2 = listBeanX.getList().get(1).getPic_url();
            String pic_url1 = listBeanX.getPic_url();
            images.add(pic_url);
            images.add(pic_url1);
            images.add(pic_url2);


            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(images);
            //banner设置方法全部调用完毕时最后调用
            banner.start();


            ivHomeSixicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String topic_url = listBeanX.getList().get(position).getPic_url();

                    String title = listBeanX.getList().get(position).getTopic_name();
                    Intent intent = new Intent(context, HomeDetailsActivity.class);
                    intent.putExtra("url", topic_url);
                    intent.putExtra("name", title);
                    context.startActivity(intent);
                }
            });


        }

        public class GlideImageLoader extends ImageLoader {


            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {

                Glide.with(context).load(path).into(imageView);


            }

        }


    }


}
