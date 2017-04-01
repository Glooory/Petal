package com.glooory.petal.mvp.ui.category;

import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.type.PusersBean;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Glooory on 17/4/1.
 */

public interface CategoryContract {

    interface View extends BaseView {

        void showLoadingMore();

        void showNoMoreDataFooter();

        void showLoginNav();
    }

    interface Model extends IModel {

        Observable<List<PinBean>> getCategoryPins(String category);

        Observable<List<PinBean>> getCategoryPinsMore(String category);

        Observable<List<BoardBean>> getCategoryBoards(String category);

        Observable<List<BoardBean>> getCategoryBoardsMore(String category);

        Observable<FollowBoardResultBean> followBoard(String boardId, boolean isFollowed);

        Observable<List<PusersBean>> getCategoryUsers(String category);

        Observable<List<PusersBean>> getCategoryUsersMore(String category);

        Observable<Void> followUser(String userId, boolean isFollowed);
    }
}