package com.glooory.petal.mvp.model.api.service;

import com.glooory.petal.mvp.model.entity.PinListBean;
import com.glooory.petal.mvp.model.entity.search.SearchHintBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchBoardListBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchPinListBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchUserListBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardListBean;
import com.glooory.petal.mvp.model.entity.user.UserListBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
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
    Observable<SearchBoardListBean> getSearchedBoards(@Query("q") String keyword,
            @Query("page") int page,
            @Query("per_page") int perPageSize);

    // 根据关键字搜索用户
    // https://api.huaban.com/search/users/?q=text&page=1&per_page=20
    @GET("search/people")
    Observable<SearchUserListBean> getSearchedUsers(@Query("q") String keyword,
            @Query("page") int page,
            @Query("per_page") int perPageSize);


    // 分类浏览的网络请求

    // 请求该分类的采集
    // https://api.huaban.com/favourite/{type}/?limit=20
    @GET("favorite/{category}")
    Observable<PinListBean> getCategoryPins(@Path("category") String category,
            @Query("limit") int limit);

    // 请求该分类的采集
    // https://api.huaban.com/favorite/{type}/?limit=20&max=
    @GET("favorite/{category}")
    Observable<PinListBean> getCategoryPinsMore(@Path("category") String category,
            @Query("max") int maxId,
            @Query("limit") int limit);

    // 请求该分类的相关画板
    // https://api.huaban.com/boards/favorite/{type}/?limit=20
    @GET("boards/favorite/{category}")
    Observable<UserBoardListBean> getCategoryBoards(@Path("category") String category,
            @Query("limit") int limit);

    // 请求该分类的相关画板
    // https://api.huaban.com/boards/favorite/{type}/?limit=20&max=
    @GET("boards/favorite/{category}")
    Observable<UserBoardListBean> getCategoryBoardsMore(@Path("category") String category,
            @Query("max") int maxId,
            @Query("limit") int limit);

    // 请求该分类的相关的用户
    // https://api.huaban.com/users/favorite/{type}/?limit=20
    @GET("users/favorite/{category}")
    Observable<UserListBean> getCategoryUsers(@Path("category") String category,
            @Query("limit") int limit);

    // 请求该分类的相关的用户
    // https://api.huaban.com/users/favorite/{type}/?limit=20&max
    @GET("users/favorite/{category}")
    Observable<UserListBean> getCategoryUsersMore(@Path("category") String category,
            @Query("max") int maxId,
            @Query("limit") int limit);

}
