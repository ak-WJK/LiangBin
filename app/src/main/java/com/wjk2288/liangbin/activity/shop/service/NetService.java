package com.wjk2288.liangbin.activity.shop.service;

import com.wjk2288.liangbin.activity.shop.bean.TypeBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/6.
 */

public interface NetService {
    @GET("goodsCategory")
    Observable<TypeBean> getType(@Query("app_key") String appkey, @Query("sig") String sig, @Query("v") String v);


}
