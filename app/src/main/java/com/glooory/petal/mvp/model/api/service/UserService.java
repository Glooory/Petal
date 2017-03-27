package com.glooory.petal.mvp.model.api.service;

import com.glooory.petal.mvp.model.entity.LatestEditBoardsBean;
import com.glooory.petal.mvp.model.entity.PinListBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.login.TokenBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardListBean;
import com.glooory.petal.mvp.model.entity.user.UserListBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Glooory on 17/3/4.
 */

public interface UserService {

    // 这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头

    // https://huaban.com/oauth/access_token/
    @FormUrlEncoded
    @POST
    Observable<TokenBean> getToken(
            @Url String url,
            @Field("grant_type") String grant,
            @Field("username") String username,
            @Field("password") String password);

    // 获取当前登录用户的信息
    @GET("users/me")
    Observable<UserBean> getUserMyself();

    // https://api.huaban.com/users/15246080
    // 获取用户的个人信息
    @GET("users/{user_id}")
    Observable<UserBean> getUser(@Path("user_id") String userId);

    // 获取我的画板集合信息
    // https://api.huaban.com/last_board/?extra=recommend_tags
    @GET("last_boards/")
    Observable<LatestEditBoardsBean> requestLatestBoardInfo(@Query("extra") String extra);

    // 用户的画板信息
    // https://api.huaban.com/users/12345678/boards?limit=20
    @GET("users/{user_id}/boards")
    Observable<UserBoardListBean> getBoards(@Path("user_id") String userId,
            @Query("limit") int limit);

    // https://api.huaban.com/users/12345678/boards?max=1123644&limit=20
    @GET("users/{user_id}/boards")
    Observable<UserBoardListBean> getBoardsMore(@Path("user_id") String userId,
            @Query("max") int max,
            @Query("limit") int limit);

    // 关注某个用户的操作
    // https://api.huaban.com/users/13643543/{follow or unfollow}
    @POST("users/{user_id}/{operate}")
    Observable<Void> followUser(@Path("user_id") String userId,
            @Path("operate") String operate);

    // 用户的所有采集信息
    // https:api.huaban.com/users/12345678/pins?limit=20
    @GET("users/{user_id}/pins")
    Observable<PinListBean> getUserPins(@Path("user_id") String userId,
            @Query("limit") int limit);

    // 用户的所有采集信息
    // https:api.huaban.com/users/12345678/pins?max=1234567&limit=20
    @GET("users/{user_id}/pins")
    Observable<PinListBean> getUserPinsMore(@Path("user_id") String userId,
            @Query("max") int maxPinId,
            @Query("limit") int limit);

    // 用户所喜欢的采集
    // https://api.huaban.com/users/12345678/likes?limit=20
    @GET("users/{user_id}/likes")
    Observable<PinListBean> getUserLikedPins(@Path("user_id") String userId,
            @Query("limit") int limit);

    // 用户所喜欢的采集
    // https://api.huaban.com/users/12345678/likes?max=1234567&limit=20
    @GET("users/{user_id}/likes")
    Observable<PinListBean> getUserLikedPinsMore(@Path("user_id") String userId,
            @Query("max") int maxPinId,
            @Query("limit") int limit);

    // 用户的粉丝
    // https://api.huaban.com/users/12345678/followers?limit=20
    @GET("users/{user_id}/followers")
    Observable<UserListBean> getUserFollowers(@Path("user_id") String userId,
            @Query("limit") int limit);

    // 用户的粉丝
    // https://api.huaban.com/users/12345678/followers?limit=20
    @GET("users/{user_id}/followers")
    Observable<UserListBean> getUserFollowersMore(@Path("user_id") String userId,
            @Query("max") int maxId,
            @Query("limit") int limit);

    // 用户所关注的用户
    // https://api.huaban.com/users/12345678/following?limit=20
    @GET("users/{user_id}/following")
    Observable<UserListBean> getUserFollowing(@Path("user_id") String userId,
            @Query("limit") int limit);

    // 用户所关注的用户
    // https://api.huaban.com/users/12345678/following?limit=20
    @GET("users/{user_id}/following")
    Observable<UserListBean> getUserFollowingMore(@Path("user_id") String userId,
            @Query("max") int maxId,
            @Query("limit") int limit);
}
