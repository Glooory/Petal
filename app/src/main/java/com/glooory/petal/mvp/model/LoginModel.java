package com.glooory.petal.mvp.model;

import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.login.TokenBean;
import com.glooory.petal.mvp.ui.login.LoginContract;
import com.jess.arms.di.scope.ActivityScope;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import common.BasePetalModel;
import common.PetalApplication;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Glooory on 17/3/13.
 */
@ActivityScope
public class LoginModel extends BasePetalModel<ServiceManager, CacheManager>
        implements LoginContract.Model {

    @Inject
    public LoginModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Set<String> loadHistoryAccounts() {
        return SPUtils.getHistoryAccounts();
    }

    @Override
    public Observable<UserBean> requestToken(final String userAccount, final String password) {
        return mServiceManager.getUserService()
                .getToken(PetalApplication.getContext().getString(R.string.url_request_token),
                        Constants.GRANT_TYPE_PASSWORD,
                        userAccount,
                        password)
                .flatMap(new Func1<TokenBean, Observable<UserBean>>() {
                    @Override
                    public Observable<UserBean> call(TokenBean tokenBean) {
                        saveToken(tokenBean);
                        return mServiceManager.getUserService().getUserMyself();
                    }
                })
                .map(new Func1<UserBean, UserBean>() {
                    @Override
                    public UserBean call(UserBean userBean) {
                        saveUserInfo(userBean, userAccount);
                        return userBean;
                    }
                });
    }

    private boolean saveToken(TokenBean tokenBean) {
        SPUtils.clear();
        return SPUtils.builder()
                .addData(Constants.PREF_IS_LOGIN, Boolean.TRUE)
                .addData(Constants.PREF_LOGIN_TIME, System.currentTimeMillis())
                .addData(Constants.PREF_ACCESS_TOKEN, tokenBean.getAccessToken())
                .addData(Constants.PREF_TOKEN_TYPE, tokenBean.getTokenType())
                .addData(Constants.PREF_REFRESH_TOKEN, tokenBean.getRefreshToken())
                .addData(Constants.PREF_TOKEN_EXPIRES_IN, tokenBean.getExpiresIn())
                .build();
    }

    @Override
    public void saveUserInfo(UserBean userBean, String userAccount) {
        if (userBean == null) {
            return;
        }

        SPUtils.builder()
                .addData(Constants.PREF_USER_ACCOUNT, userAccount)
                .addData(Constants.PREF_USER_NAME, userBean.getUsername())
                .addData(Constants.PREF_USER_ID, userBean.getUserId())
                .addData(Constants.PREF_USER_EMAIL, userBean.getEmail())
                .addData(Constants.PREF_USER_AVATAR_KEY,
                        userBean.getAvatar() == null ? "" : userBean.getAvatar().getKey())
                .build();

        Set<String> historyAccounts = SPUtils.getHistoryAccounts();
        Set<String> newAccouts = new HashSet<>(historyAccounts);
        newAccouts.add(userAccount);
        SPUtils.putHistoryAccounts(newAccouts);
    }

    @Override
    public void saveSkipLoginTrue() {
        SPUtils.putByApply(Constants.PREF_IS_SKIP_LOGIN, true);
    }

    @Override
    public void onDestory() {

    }
}
