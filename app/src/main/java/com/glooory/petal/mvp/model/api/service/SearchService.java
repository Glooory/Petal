package com.glooory.petal.mvp.model.api.service;

import com.glooory.petal.mvp.model.entity.search.SearchHintBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Glooory on 17/3/30.
 */

public interface SearchService {

    //搜索关键字
    //https:api.huaban.com/search/hint?q=%df&cdfd
    @GET("search/hint/")
    Observable<SearchHintBean> getSearchHint(@Query("q") String key);

}
