package com.glooory.petal.mvp.model.api.service;

import com.glooory.petal.app.Constants;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.PinListBean;
import com.glooory.petal.mvp.model.entity.collect.CollectResultBean;
import com.glooory.petal.mvp.model.entity.pindetail.CollectionInfoBean;
import com.glooory.petal.mvp.model.entity.pindetail.LikeResultBean;
import com.glooory.petal.mvp.model.entity.pindetail.PinDetailBean;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Glooory on 17/3/23.
 */

public interface PinService {

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

    // 获取某个采集的详细信息
    // https://api.huaban.com/pins/pin_id
    @GET("pins/{pin_id}")
    Observable<PinDetailBean> getPinDetailInfo(@Path("pin_id") int pinId);

    //根据pinId推荐相似的pin
    //https:api.huaban.com/pins/{pin_id}/recommend/
    @GET("pins/{pin_id}/recommend/")
    Observable<List<PinBean>> getRecommendedPins(@Path("pin_id") int pinId,
            @Query("page") int page, @Query("per_page") int perPage);

    // 对某个采集的喜欢或者取消喜欢的操作
    //https:api.huaban.com/pins/12345664/like
    //https:api.huaban.com/pins/12345664/unlike
    @POST("pins/{pin_id}/{operate}")
    Observable<LikeResultBean> likePin(
            @Path("pin_id") int pinId,
            @Path("operate") String operate
    );

    //采集图片前判断是否已经在自己的采集中
    //https:api.huaban.com/pins/134541447/repin?check=true
    @GET("pins/{via_id}/repin/")
    Observable<CollectionInfoBean> isCollected(
            @Path("via_id") int pinId,
            @Query("check") boolean check
    );

    //采集某个图片
    //https://api.huaban.com/pins/ body=board_id=17891564&text=描述内容&via=707423726
    @FormUrlEncoded
    @POST("pins/")
    Observable<CollectResultBean> collectPin(
            @Field("board_id") String boardId,
            @Field("text") String des,
            @Field("via") String pinsId
    );

    // 编辑修改某个采集的信息
    // https://api.huaban.com/pins/865002387?board_id=32026507&text=%E6%9D%A5%E8%87%AA%E7%9B%B8%E5%86%8C
    @FormUrlEncoded
    @POST("pins/{pin_id}")
    Observable<PinBean> editPin(@Path("pin_id") int pinId,
            @Field("board_id") String boardId,
            @Field("text") String des);

    // 删除某个采集
    // https://api.huaban.com/pins/864657103
    @DELETE("pins/{pin_id}")
    Observable<Void> deletePin(@Path("pin_id") int pinId);

}
