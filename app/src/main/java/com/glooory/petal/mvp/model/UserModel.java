package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardListBean;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.jess.arms.di.scope.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/22.
 */
@ActivityScope
public class UserModel extends BasePEModel<ServiceManager, CacheManager> implements UserContract.Model {

    private int mMaxId;

    @Inject
    public UserModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    public boolean isMe(String userId) {
        String userIdTemp = (String) SPUtils.get(Constants.PREF_USER_ID, "");
        return userId.equals(userIdTemp);
    }

    @Override
    public Observable<UserBean> getUser(String userId) {
        return mServiceManager.getUserService()
                .getUser(userId)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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

}
