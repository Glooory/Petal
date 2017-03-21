package com.glooory.petal.mvp.model.api.service;

import com.glooory.petal.mvp.model.entity.LatestEditBoardsBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.login.TokenBean;

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
}
