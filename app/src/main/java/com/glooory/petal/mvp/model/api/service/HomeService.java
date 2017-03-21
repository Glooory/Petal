package com.glooory.petal.mvp.model.api.service;

import com.glooory.petal.app.Constants;
import com.glooory.petal.mvp.model.entity.PinListBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Glooory on 17/2/20.
 */

public interface HomeService {

    //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头

    //获取最新的采集
    //https://api.huaban.com/all?limit=20
    @GET("all")
    Observable<PinListBean> getLatestAllPins(
            @Query(Constants.HTTP_QUERY_LIMIT) int limit);

    //获取最新的采集
    //https://api.huaban.com/all?max={pin_id}&limit=20
    @GET("all")
    Observable<PinListBean> getLatestAllPinsNext(
            @Query(Constants.HTTP_QUERY_MAX) int maxPinId,
            @Query(Constants.HTTP_QUERY_LIMIT) int limit);

    //获取用户关注的用户和花瓣更新
    //https:api.huaban.com/following?limit=20
    @GET("following")
    Observable<PinListBean> getLatestFollowingPins(
            @Query(Constants.HTTP_QUERY_LIMIT) int limit);

    //获取用户关注的用户和花瓣更新
    //https:api.huaban.com/following?max={pin_id}&limit=20
    @GET("following")
    Observable<PinListBean> getLatestFollowingPinsNext(
            @Query(Constants.HTTP_QUERY_MAX) int maxPinId,
            @Query(Constants.HTTP_QUERY_LIMIT) int limit);

    //获取热门采集
    //https://api.huaban.com/popular?limit=20
    @GET("popular")
    Observable<PinListBean> getLatestPopularPins(
            @Query(Constants.HTTP_QUERY_LIMIT) int limit);

    //获取热门采集
    //https://api.huaban.com/popular?max={pin_id}&limit=20
    @GET("popular")
    Observable<PinListBean> getLatestPopularPinsNext(
            @Query(Constants.HTTP_QUERY_MAX) int maxPinId,
            @Query(Constants.HTTP_QUERY_LIMIT) int limit);

}
