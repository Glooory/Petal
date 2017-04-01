package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchBoardListBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchPinListBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchUserListBean;
import com.glooory.petal.mvp.ui.searchresult.SearchResultContract;
import com.jess.arms.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import common.BasePetalModel;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/31.
 */
@FragmentScope
public class SearchResultModel extends BasePetalModel<ServiceManager, CacheManager>
        implements SearchResultContract.Model{

    private int mPage;

    @Inject
    public SearchResultModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<SearchPinListBean> getSearchedPins(String keyword) {
        mPage = 1;
        mPage++;
        return mServiceManager.getSearchService()
                .getSearchedPins(keyword, 1, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1));
    }

    @Override
    public Observable<List<PinBean>> getSearchedPinsMore(String keyword) {
        return mServiceManager.getSearchService()
                .getSearchedPins(keyword, mPage, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<SearchPinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(SearchPinListBean searchPinListBean) {
                        if (searchPinListBean.getPins() != null && searchPinListBean.getPins().size() > 0) {
                            mPage++;
                        } else {
                            searchPinListBean.setPins(new ArrayList<PinBean>(0));
                        }
                        return searchPinListBean.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<SearchBoardListBean> getSearchedBoards(String keyword) {
        mPage = 1;
        mPage++;
        return mServiceManager.getSearchService()
                .getSearchedBoards(keyword, 1, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1));
    }

    @Override
    public Observable<List<BoardBean>> getSearchedBoardsMore(String keyword) {
        return mServiceManager.getSearchService()
                .getSearchedBoards(keyword, mPage, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<SearchBoardListBean, List<BoardBean>>() {
                    @Override
                    public List<BoardBean> call(SearchBoardListBean searchBoardListBean) {
                        if (searchBoardListBean.getBoards() != null &&
                                searchBoardListBean.getBoards().size() > 0) {
                            mPage++;
                        } else {
                            searchBoardListBean.setBoards(new ArrayList<BoardBean>(0));
                        }
                        return searchBoardListBean.getBoards();
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
    public Observable<SearchUserListBean> getSearchedUsers(String keyword) {
        mPage = 1;
        mPage++;
        return mServiceManager.getSearchService()
                .getSearchedUsers(keyword, 1, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1));
    }

    @Override
    public Observable<List<UserBean>> getSearchedUsersMore(String keyword) {
        return mServiceManager.getSearchService()
                .getSearchedUsers(keyword, mPage, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<SearchUserListBean, List<UserBean>>() {
                    @Override
                    public List<UserBean> call(SearchUserListBean searchUserListBean) {
                        if (searchUserListBean.getUsers() != null &&
                                searchUserListBean.getUsers().size() > 0) {
                            mPage++;
                        } else {
                            searchUserListBean.setUsers(new ArrayList<UserBean>(0));
                        }
                        return searchUserListBean.getUsers();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Void> followUser(String userId, boolean isFollowed) {
        return mServiceManager.getUserService()
                .followUser(userId, isFollowed ? Constants.HTTP_ARGS_UNFOLLOW : Constants.HTTP_ARGS_FOLLOW)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
