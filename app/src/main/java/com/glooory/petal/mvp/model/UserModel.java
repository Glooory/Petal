package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
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
        String userIdTemp = String.valueOf(SPUtils.get(Constants.PREF_USER_ID, 0));
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
    public Observable<Void> followUser(String userId,boolean isFollowed) {
        String operate = isFollowed ? "unfollow" : "follow";
        return mServiceManager.getOperateService()
                .followUser(userId, operate)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
