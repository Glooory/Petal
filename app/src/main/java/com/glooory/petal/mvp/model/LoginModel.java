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

import java.util.Set;

import javax.inject.Inject;

import common.PEApplication;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/13.
 */
@ActivityScope
public class LoginModel extends BasePEModel<ServiceManager, CacheManager>
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
    public Observable<UserBean> requestToken(String userAccount, String password) {
        return mServiceManager.getUserService()
                .getToken(PEApplication.getContext().getString(R.string.url_request_token),
                        Constants.HTTP_ARGS_VALUE_PASSWORD,
                        userAccount,
                        password)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(2, 2))
                .filter(new Func1<TokenBean, Boolean>() {
                    @Override
                    public Boolean call(TokenBean tokenBean) {
                        return saveToken(tokenBean);
                    }
                })
                .flatMap(new Func1<TokenBean, Observable<UserBean>>() {
                    @Override
                    public Observable<UserBean> call(TokenBean tokenBean) {
                        return mServiceManager.getUserService().getUserMyself();
                    }
                });
    }

    private boolean saveToken(TokenBean tokenBean) {
        return SPUtils.builder()
                .addData(Constants.PREF_IS_LOGIN, Boolean.TRUE)
                .addData(Constants.PREF_LOGIN_TIME, System.currentTimeMillis())
                .addData(Constants.PREF_TOKEN_ACCESS, tokenBean.getAccessToken())
                .addData(Constants.PREF_TOKEN_TYPE, tokenBean.getTokenType())
                .addData(Constants.PREF_TOKEN_REFRESH, tokenBean.getRefreshToken())
                .addData(Constants.PREF_TOKEN_EXPIRES_IN, tokenBean.getExpiresIn())
                .build();
    }

    @Override
    public void saveSkipLoginTrue() {
        SPUtils.putByApply(Constants.PREF_IS_SKIP_LOGIN, true);
    }

    @Override
    public void onDestory() {

    }
}
