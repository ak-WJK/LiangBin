package com.wjk2288.liangbin.activity.shop.service;

import com.wjk2288.liangbin.activity.shop.bean.BrandBean;
import com.wjk2288.liangbin.activity.shop.bean.HomeBean;
import com.wjk2288.liangbin.activity.shop.bean.SpecialBean;
import com.wjk2288.liangbin.activity.shop.bean.TypeBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/6.
 */

public interface NetService {

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


}
