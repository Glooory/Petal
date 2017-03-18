package com.glooory.petal.mvp.model.api.service;

import com.glooory.petal.mvp.model.entity.collect.CollectResultBean;
import com.glooory.petal.mvp.model.entity.pindetail.CollectionInfoBean;
import com.glooory.petal.mvp.model.entity.pindetail.LikeResultBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Glooory on 17/3/17.
 */

public interface OperateService {

    // 对某个采集的喜欢或者取消喜欢的操作
    //https:api.huaban.com/pins/12345664/like
    //https:api.huaban.com/pins/12345664/unlike
    @POST("pins/{pinId}/{operate}")
    Observable<LikeResultBean> likePin(
            @Path("pinId") int pinId,
            @Path("operate") String operate
            );

    //采集图片前判断是否已经在自己的采集中
    //https:api.huaban.com/pins/134541447/repin?check=true
    @GET("pins/{viaId}/repin/")
    Observable<CollectionInfoBean> isCollected(
            @Path("viaId") int viaId,
            @Query("check") boolean check
    );

    //采集某个图片
    //https://api.huaban.com/pins/ body=board_id=17891564&text=描述内容&via=707423726
    @FormUrlEncoded
    @POST("pins/")
    Observable<CollectResultBean> collect(
            @Field("board_id") String boardId,
            @Field("text") String des,
            @Field("via") String pinsId
    );

}
