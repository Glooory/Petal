package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardListBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardSingleBean;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.jess.arms.di.scope.FragmentScope;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/23.
 */
@FragmentScope
public class UserSectionModel extends BasePEModel<ServiceManager, CacheManager> implements UserContract.SectionModel {

    private int mMaxId;

    @Inject
    public UserSectionModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public boolean isMe(String userId) {
        String userIdTemp = String.valueOf(SPUtils.get(Constants.PREF_USER_ID, 0));
        return userId.equals(userIdTemp);
    }

    @Override
    public Observable<List<BoardBean>> getBoards(String userId) {
        return mServiceManager.getUserService()
                .getBoards(userId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<UserBoardListBean, List<BoardBean>>() {
                    @Override
                    public List<BoardBean> call(UserBoardListBean userBoardListBean) {
                        mMaxId = userBoardListBean.getBoards().
                                get(userBoardListBean.getBoards().size() - 1).getBoardId();
                        return userBoardListBean.getBoards();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<BoardBean>> getBoardsMore(final String userId) {
        return mServiceManager.getUserService()
                .getBoardsMore(userId, mMaxId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<UserBoardListBean, List<BoardBean>>() {
                    @Override
                    public List<BoardBean> call(UserBoardListBean userBoardListBean) {
                        mMaxId = userBoardListBean.getBoards()
                                .get(userBoardListBean.getBoards().size() - 1).getBoardId();
                        return userBoardListBean.getBoards();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<FollowBoardResultBean> followBoard(String boardId, boolean isFollowed) {
        String operate = isFollowed ? Constants.HTTP_ARGS_UNFOLLOW : Constants.HTTP_ARGS_FOLLOW;
        return mServiceManager.getBoardService()
                .followBoard(boardId, operate)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UserBoardSingleBean> editBoard(String boardId, String boardName,
            String des, String category) {
        return mServiceManager.getBoardService()
                .editBoard(boardId, boardName, des, category)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UserBoardSingleBean> createBoard(String boardName,
            String des, String category) {
        return mServiceManager.getBoardService()
                .createBoard(boardName, des, category)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UserBoardSingleBean> deleteBoard(String boardId) {
        return mServiceManager.getBoardService()
                .deleteBoard(boardId, Constants.HTTP_ARGS_DELETE)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
