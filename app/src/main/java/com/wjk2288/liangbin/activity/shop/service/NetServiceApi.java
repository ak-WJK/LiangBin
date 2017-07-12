package com.wjk2288.liangbin.activity.shop.service;

import com.wjk2288.liangbin.activity.daren.bean.DaRenDetailsBean;
import com.wjk2288.liangbin.activity.daren.bean.DaRenShowBean;
import com.wjk2288.liangbin.activity.daren.bean.renqibean.FenSiBean;
import com.wjk2288.liangbin.activity.daren.bean.renqibean.TuiJianBean;
import com.wjk2288.liangbin.activity.shop.bean.TypeBean;
import com.wjk2288.liangbin.activity.shop.bean.details.BrandDetailsBean;
import com.wjk2288.liangbin.activity.shop.bean.details.BrandDetailsPagerBean;
import com.wjk2288.liangbin.activity.shop.bean.details.TypeDetailsBean;
import com.wjk2288.liangbin.activity.shop.bean.details.TypeShowBean;
import com.wjk2288.liangbin.activity.shop.bean.typepagerbean.BrandBean;
import com.wjk2288.liangbin.activity.shop.bean.typepagerbean.HomeBean;
import com.wjk2288.liangbin.activity.shop.bean.typepagerbean.SpecialBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
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
    Observable<BrandBean> getBrand(@Query("app_key") String appkey,
                                   @Query("count") int count,
                                   @Query("page") int page,
                                   @Query("sig") String sig,
                                   @Query("v") String v);

    //品牌展示
    @GET("brandShopList")
    Observable<BrandDetailsBean> getBrandDetails(@Query("app_key") String appkey,
                                                 @Query("brand_id") int brandId,
                                                 @Query("count") int count,
                                                 @Query("page") int page,
                                                 @Query("sig") String sig,
                                                 @Query("v") String v);

    //品牌详情
    @GET("goodsDetail")
    Observable<BrandDetailsPagerBean> getBrandDetailsPager(@Query("app_key") String appkey,
                                                           @Query("goods_id") String brandId,
                                                           @Query("sig") String sig,
                                                           @Query("v") String v);


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

    //商品详情
    @GET("goodsDetail")
    Observable<TypeDetailsBean> getGoodsDetails(@Query("app_key") String appkey,
                                                @Query("goods_id") String goodId,
                                                @Query("sig") String sig,
                                                @Query("v") String v
    );


    //达人页面
    @GET("masterList")
    Observable<DaRenShowBean> getDaRen(@Query("app_key") String appkey,
                                       @Query("count") int count,
                                       @Query("page") int page,
                                       @Query("sig") String sig,
                                       @Query("v") String v
    );

    //达人详情页面
    @GET("masterFollowed")
    Observable<DaRenDetailsBean> getDaRenDetails(@Query("app_key") String appkey,
                                                 @Query("count") int count,
                                                 @Query("owner_id") String uId,
                                                 @Query("page") int page,
                                                 @Query("sig") String sig,
                                                 @Query("v") String v
    );

    //达人详情页面
    @GET("{value}")
    Observable<TuiJianBean> getDaRenRenQi(@Path("value") String values,
                                          @Query("app_key") String appkey,
                                          @Query("count") int count,
                                          @Query("owner_id") String uId,
                                          @Query("page") int page,
                                          @Query("sig") String sig,
                                          @Query("v") String v

    );


    //达人详情页面2
    @GET("{value}")
    Observable<FenSiBean> getDaRenRenQi2(@Path("value") String values,
                                         @Query("app_key") String appkey,
                                         @Query("count") int count,
                                         @Query("owner_id") String uId,
                                         @Query("page") int page,
                                         @Query("sig") String sig,
                                         @Query("v") String v

    );


}
