package com.glooory.petal.mvp.model.api.service;

import com.glooory.petal.mvp.model.entity.FollowerListBean;
import com.glooory.petal.mvp.model.entity.PinListBean;
import com.glooory.petal.mvp.model.entity.board.BoardSingleBean;
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardSingleBean;

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

public interface BoardService {

    // 关注某个画板
    // https:api.huaban.com/boards/{boardId}/{follow or unfollow}
    @POST("boards/{board_id}/{operate}")
    Observable<FollowBoardResultBean> followBoard(@Path("board_id") String boardId,
            @Path("operate") String operate);

    // 新建画板
    // https://api.huaban.com/boards  body=category=game&description=war craft&title=midnight
    @FormUrlEncoded
    @POST("boards/")
    Observable<UserBoardSingleBean> createBoard(@Field("title") String title,
            @Field("description") String description,
            @Field("category") String category);

    // 修改某个画板的信息
    // https://api.huaban.com/boards/29646779 category=photography&description=birds&title=birds
    @FormUrlEncoded
    @POST("boards/{board_id}")
    Observable<UserBoardSingleBean> editBoard(@Path("board_id") String boardId,
            @Field("title") String title,
            @Field("description") String des,
            @Field("category") String category);

    // 删除某个画板
    // https://api.huaban.com/boards/29653031 POST BODY= _method=DELETE
    @FormUrlEncoded
    @POST("boards/{board_id}")
    Observable<UserBoardSingleBean> deleteBoard(@Path("board_id") String boardId,
            @Field("_method") String operate);

    // 根据boardId获取board信息
    // https:api.huaban.com/boards/boardId
    @GET("boards/{board_id}")
    Observable<BoardSingleBean> getBoard(@Path("board_id") String boardId);

    // 根据画板的 id 获取采集
    // https:api.huaban.com/boards/1234566/pins?limit=20
    @GET("boards/{board_id}/pins")
    Observable<PinListBean> getBoardPins(@Path("board_id") String boardId,
            @Query("limit") int limit);

    // 根据画板的 id 获取采集
    // https:api.huaban.com/boards/1234566/pins?max=12345678&limit=20
    @GET("boards/{board_id}/pins")
    Observable<PinListBean> getBoardPinsMore(@Path("board_id") String boardId,
            @Query("max") int max,
            @Query("limit") int limit);

    // 获取某个画板的关注者
    // https://api.huaban.com/boards/1346566/followers?limit=20
    @GET("boards/{board_id}/followers")
    Observable<FollowerListBean> getBoardFollowers(@Path("board_id") String boardId,
            @Query("limit") int limit);

    // 获取某个画板的关注者
    // https://api.huaban.com/boards/1346566/followers?max=12345678&limit=20
    @GET("boards/{board_id}/followers")
    Observable<FollowerListBean> getBoardFollowersMore(@Path("board_id") String boardId,
            @Query("max") int maxId,
            @Query("limit") int limit);
}
