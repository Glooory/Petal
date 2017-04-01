package com.glooory.petal.mvp.ui.searchresult;

import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchBoardListBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchPinListBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchUserListBean;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Glooory on 17/3/31.
 */

public interface SearchResultContract {

    interface View extends BaseView {

        void showTabTitles(String[] titles);

        void showLoadingMore();

        void showNoMoreDataFooter(boolean showAnyway);

        void showLoginNav();
    }

    interface Model extends IModel {

        Observable<SearchPinListBean> getSearchedPins(String keyword);

        Observable<List<PinBean>> getSearchedPinsMore(String keyword);

        Observable<SearchBoardListBean> getSearchedBoards(String keyword);

        Observable<List<BoardBean>> getSearchedBoardsMore(String keyword);

        Observable<FollowBoardResultBean> followBoard(String boardId, boolean isFollowed);

        Observable<SearchUserListBean> getSearchedUsers(String keyword);

        Observable<List<UserBean>> getSearchedUsersMore(String keyword);

        Observable<Void> followUser(String userId, boolean isFollowed);
    }
}
