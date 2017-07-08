package com.wjk2288.liangbin.activity.shop.service;

import com.wjk2288.liangbin.activity.shop.bean.BrandBean;
import com.wjk2288.liangbin.activity.shop.bean.HomeBean;
import com.wjk2288.liangbin.activity.shop.bean.SpecialBean;
import com.wjk2288.liangbin.activity.shop.bean.TypeBean;
import com.wjk2288.liangbin.activity.shop.bean.details.TypeShowBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/6.
 */

public interface NetServiceApi {

    //类型
    @GET("goodsCategory")
    Observable<TypeBean> getType(@Query("app_key") String appkey,
                                 @Query("sig") String sig,
                                 @Query("v") String v);


    //品牌
    @GET("brandList")
    Observable<BrandBean> getBrand(@Query("app_key") String appkey, @Query("count") int count, @Query("page") int page, @Query("sig") String sig, @Query("v") String v);


    //专题
    @GET("shopSpecial")
    Observable<SpecialBean> getSpecial(@Query("app_key") String appkey,
                                       @Query("count") int count,
                                       @Query("page") int page,
                                       @Query("sig") String sig,
                                       @Query("uid") String uid,
                                       @Query("user_token") String userToken,
                                       @Query("v") String v
    );


//    @GET("goodsList")
//    Observable<GiftBean> getGift(@Query("app_key") String appkey,
//                                 @Query("count") int count,
//                                 @Query("list_id") int listId,
//                                 @Query("page") int pager,
//                                 @Query("sig") String sig,
//                                 @Query("v") String v
//    );

    //首页
    @GET("newShopHome")
    Observable<HomeBean> getHome(@Query("app_key") String appkey,
                                 @Query("count") int count,
                                 @Query("page") int page,
                                 @Query("sig") String sig,
                                 @Query("uid") String uid,
                                 @Query("user_token") String userToken,
                                 @Query("v") String v
    );


    //家居
    @GET("goodsShare")
    Observable<TypeShowBean> getTypeShow(@Query("app_key") String appkey,
                                         @Query("cat_code") String catCode,
                                         @Query("count") int count,
                                         @Query("coverId") int coverId,
                                         @Query("page") int page,
                                         @Query("sig") String sig,
                                         @Query("v") String v
    );


    //家具


    //文具


    //数码


    //玩乐


    //厨卫


    //美食


    //男装


    //女装


    //童装


    //鞋包


    //配饰


    //美护


    //户外


    //植物


    //图书


    //礼物


    //推荐


    //艺术


}
