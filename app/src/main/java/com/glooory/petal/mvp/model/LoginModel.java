package com.glooory.petal.mvp.model;

import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.EncrypAES;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.LatestEditBoardsBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.login.TokenBean;
import com.glooory.petal.mvp.ui.login.LoginContract;
import com.jess.arms.di.scope.ActivityScope;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import common.BasePetalModel;
import common.PetalApplication;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
    public Observable<LatestEditBoardsBean> requestToken(final String userAccount, final String password) {
        return mServiceManager.getUserService()
                .getToken(PetalApplication.getContext().getString(R.string.url_request_token),
                        Constants.HTTP_ARGS_VALUE_PASSWORD,
                        userAccount,
                        password)
                .retryWhen(new RetryWithDelay(2, 2))
                .flatMap(new Func1<TokenBean, Observable<UserBean>>() {
                    @Override
                    public Observable<UserBean> call(TokenBean tokenBean) {
                        saveToken(tokenBean);
                        return mServiceManager.getUserService().getUserMyself();
                    }
                })
                .flatMap(new Func1<UserBean, Observable<LatestEditBoardsBean>>() {
                    @Override
                    public Observable<LatestEditBoardsBean> call(UserBean userBean) {
                        saveUserInfo(userBean, userAccount, password);
                        return mServiceManager.getUserService().requestLatestBoardInfo(Constants.HTTP_RECOMMEND_TAGS);
                    }
                });
    }

    @Override
    public Observable<LatestEditBoardsBean> requestBoardsInfo() {
        return mServiceManager.getUserService()
                .requestLatestBoardInfo(Constants.HTTP_RECOMMEND_TAGS)
                .retryWhen(new RetryWithDelay(1, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private boolean saveToken(TokenBean tokenBean) {
        SPUtils.clear();
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
    public void saveUserInfo(UserBean userBean, String userAccount, String password) {
        if (userBean == null) {
            return;
        }

        EncrypAES mEncrypAES = new EncrypAES();

        SPUtils.builder()
                .addData(Constants.PREF_USER_ACCOUNT, userAccount)
                .addData(Constants.PREF_USER_PASSWORD, mEncrypAES.EncryptorString(password))
                .addData(Constants.PREF_USER_NAME, userBean.getUsername())
                .addData(Constants.PREF_USER_ID, userBean.getUserId())
                .addData(Constants.PREF_USER_EMAIL, userBean.getEmail())
                .addData(Constants.PREF_USER_AVATAR_KEY, userBean.getAvatar().getKey())
                .build();

        Set<String> historyAccounts = SPUtils.getHistoryAccounts();
        Set<String> newAccouts = new HashSet<>(historyAccounts);
        newAccouts.add(userAccount);
        SPUtils.putHistoryAccounts(newAccouts);
    }

    @Override
    public void saveUserBoardInfo(List<BoardBean> boardList) {
        if (boardList == null) {
            return;
        }

        SPUtils.remove(Constants.PREF_BOARD_TITLES);
        SPUtils.remove(Constants.PREF_BOARD_IDS);
        StringBuilder boardTitle = new StringBuilder();
        StringBuilder boardId = new StringBuilder();

        for (int i = 0; i < boardList.size(); i++) {
            boardTitle.append(boardList.get(i).getTitle());
            boardId.append(boardList.get(i).getBoardId());
            if (i != boardList.size() - 1) {
                boardTitle.append(Constants.COMMA);
                boardId.append(Constants.COMMA);
            }
        }

        SPUtils.builder()
                .addData(Constants.PREF_BOARD_TITLES, boardTitle.toString())
                .addData(Constants.PREF_BOARD_IDS, boardId.toString())
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
