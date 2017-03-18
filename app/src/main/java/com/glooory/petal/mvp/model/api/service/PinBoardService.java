package com.glooory.petal.mvp.model.api.service;

import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.pindetail.PinDetailBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Glooory on 17/3/17.
 */

public interface PinBoardService {

    // 获取某个采集的详细信息
    // https://api.huaban.com/pins/pinId
    @GET("pins/{pinId}")
    Observable<PinDetailBean> getPinDetailInfo(@Path("pinId") int pinId);

    //根据pinId推荐相似的pin
    //https:api.huaban.com/pins/{pinId}/recommend/
    @GET("pins/{pinId}/recommend/")
    Observable<List<PinBean>> getRecommendedPins(@Path("pinId") int pinId,
            @Query("page") int page, @Query("per_page") int perPage);

}
