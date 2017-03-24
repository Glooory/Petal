package com.glooory.petal.mvp.model.api.service;

import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardSingleBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    //新建画板
    //https://api.huaban.com/boards  body=category=game&description=war craft&title=midnight
    @FormUrlEncoded
    @POST("boards/")
    Observable<UserBoardSingleBean> createBoard(@Field("title") String title,
            @Field("description") String description,
            @Field("category") String category);

    //修改某个画板的信息
    //https://api.huaban.com/boards/29646779 category=photography&description=birds&title=birds
    @FormUrlEncoded
    @POST("boards/{board_id}")
    Observable<UserBoardSingleBean> editBoard(@Path("board_id") String boardId,
            @Field("title") String title,
            @Field("description") String des,
            @Field("category") String category);

    //删除某个画板
    //https://api.huaban.com/boards/29653031 POST BODY= _method=DELETE
    @FormUrlEncoded
    @POST("boards/{board_id}")
    Observable<UserBoardSingleBean> deleteBoard(@Path("board_id") String boardId,
            @Field("_method") String operate);

}
