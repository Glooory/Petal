package com.glooory.petal.mvp.model.api.service;

import com.glooory.petal.mvp.model.entity.search.SearchHintBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchPinListBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardListBean;
import com.glooory.petal.mvp.model.entity.user.UserListBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Glooory on 17/3/30.
 */

public interface SearchService {

    // 搜索关键字
    // https:api.huaban.com/search/hint?q=%df&cdfd
    @GET("search/hint/")
    Observable<SearchHintBean> getSearchHint(@Query("q") String key);

    // 根据关键字搜索采集
    // https://api.huaban.com/search/?q=text&page=1&per_page=20
    @GET("search")
    Observable<SearchPinListBean> getSearchedPins(@Query("q") String keyword,
            @Query("page") int page,
            @Query("per_page") int perPageSize);

    // 根据关键字搜索画板
    // https://api.huaban.com/search/boards/?q=text&page=1&per_page=20
    @GET("search/boards")
    Observable<UserBoardListBean> getSearchedBoards(@Query("q") String keyword,
            @Query("page") int page,
            @Query("per_page") int perPageSize);

    // 根据关键字搜索用户
    // https://api.huaban.com/search/users/?q=text&page=1&per_page=20
    @GET("search/people")
    Observable<UserListBean> getSearchedUsers(@Query("q") String keyword,
            @Query("page") int page,
            @Query("per_page") int perPageSize);

}
