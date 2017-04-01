package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.PinListBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardListBean;
import com.glooory.petal.mvp.ui.category.CategoryContract;
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
 * Created by Glooory on 17/4/1.
 */
@FragmentScope
public class CategoryModel extends BasePetalModel<ServiceManager, CacheManager>
        implements CategoryContract.Model {

    private int mMaxId;

    @Inject
    public CategoryModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<List<PinBean>> getCategoryPins(String category) {
        return mServiceManager.getSearchService()
                .getCategoryPins(category, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        if (pinListBean.getPins() != null && pinListBean.getPins().size() > 0) {
                            mMaxId = pinListBean.getPins().get(pinListBean.getPins().size() - 1).getPinId();
                        } else {
                            pinListBean.setPins(new ArrayList<PinBean>(0));
                        }
                        return pinListBean.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getCategoryPinsMore(String category) {
        return mServiceManager.getSearchService()
                .getCategoryPinsMore(category, mMaxId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        if (pinListBean.getPins() != null && pinListBean.getPins().size() > 0) {
                            mMaxId = pinListBean.getPins().get(pinListBean.getPins().size() - 1).getPinId();
                        } else {
                            pinListBean.setPins(new ArrayList<PinBean>(0));
                        }
                        return pinListBean.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<BoardBean>> getCategoryBoards(String category) {
        return mServiceManager.getSearchService()
                .getCategoryBoards(category, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<UserBoardListBean, List<BoardBean>>() {
                    @Override
                    public List<BoardBean> call(UserBoardListBean userBoardListBean) {
                        if (userBoardListBean.getBoards() != null
                                && userBoardListBean.getBoards().size() > 0) {
                            mMaxId = userBoardListBean.getBoards().get(
                                    userBoardListBean.getBoards().size() - 1).getBoardId();
                        } else {
                            userBoardListBean.setBoards(new ArrayList<BoardBean>(0));
                        }
                        return userBoardListBean.getBoards();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<BoardBean>> getCategoryBoardsMore(String category) {
        return mServiceManager.getSearchService()
                .getCategoryBoardsMore(category, mMaxId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<UserBoardListBean, List<BoardBean>>() {
                    @Override
                    public List<BoardBean> call(UserBoardListBean userBoardListBean) {
                        if (userBoardListBean.getBoards() != null
                                && userBoardListBean.getBoards().size() > 0) {
                            mMaxId = userBoardListBean.getBoards().get(
                                    userBoardListBean.getBoards().size() - 1).getBoardId();
                        } else {
                            userBoardListBean.setBoards(new ArrayList<BoardBean>(0));
                        }
                        return userBoardListBean.getBoards();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<UserBean>> getCategoryUsers(String category) {
        return null;
    }

    @Override
    public Observable<List<UserBean>> getCategoryUsersMore(String category) {
        return null;
    }
}
