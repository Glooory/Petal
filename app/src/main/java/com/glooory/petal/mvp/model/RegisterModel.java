package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.app.util.StringUtils;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.register.CaptchaResult;
import com.glooory.petal.mvp.model.entity.register.RegisterResultBean;
import com.glooory.petal.mvp.ui.register.RegisterContract;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import common.BasePetalModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/4/14.
 */

public class RegisterModel extends BasePetalModel<ServiceManager, CacheManager>
        implements RegisterContract.Model {

    @Inject
    public RegisterModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public boolean isPhoneNumber(String number) {
        return StringUtils.isPhoneNumber(number);
    }

    @Override
    public Observable<CaptchaResult> register(String phoneNumber) {
        return mServiceManager.getUserService()
                .sendIndentifyCode("huaban_android_3.1.3", phoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RegisterResultBean> signup(String captcha, String tel, String userName, String password) {
        return mServiceManager.getUserService()
                .signup(captcha, Constants.GRANT_TYPE_PASSWORD, password, password, tel, userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveToken(RegisterResultBean registerResultBean) {
        SPUtils.clear();
        SPUtils.builder()
                .addData(Constants.PREF_IS_LOGIN, Boolean.TRUE)
                .addData(Constants.PREF_LOGIN_TIME, System.currentTimeMillis())
                .addData(Constants.PREF_ACCESS_TOKEN, registerResultBean.getAccessToken())
                .addData(Constants.PREF_TOKEN_TYPE, registerResultBean.getTokenType())
                .addData(Constants.PREF_REFRESH_TOKEN, registerResultBean.getRefreshToken())
                .addData(Constants.PREF_TOKEN_EXPIRES_IN, registerResultBean.getExpiresIn())
                .build();
    }

    @Override
    public Observable<UserBean> requestUserMeInfo(String userAccount) {
        return mServiceManager.getUserService()
                .getUserMyself()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
}
