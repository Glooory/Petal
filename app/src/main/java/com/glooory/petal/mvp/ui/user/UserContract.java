package com.glooory.petal.mvp.ui.user;

import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Glooory on 17/3/21.
 */

public interface UserContract {

    interface View extends BaseView {

        void showViewPager();

        void showToolbarAction(int actionResId, int actionDrawableResId);

        void showProcessingbar();

        void hideProcessingbar();

        void showTabTitles(String[] titles);

        void showUserName(String userName);

        void showUserLocation(String location);

        void showUserAbout(String userAbout);

        void showUserAvatar(String avatarKey);

    }

    interface Model extends IModel {

        boolean isMe(String userId);

        Observable<UserBean> getUser(String userId);

        Observable<Void> followUser(String userId, boolean isFollowed);
    }

    interface SectionView extends BaseView {

        void showLoadingMore();

        void showNoMoreDataFooter();
    }

    interface SectionModel extends IModel {

        boolean isMe(String userId);

        Observable<List<BoardBean>> getBoards(String userId);

        Observable<List<BoardBean>> getBoardsMore(String userId);
    }

}
