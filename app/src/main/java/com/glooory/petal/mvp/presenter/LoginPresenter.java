package com.glooory.petal.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.adapter.NoFilterringAdapter;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.util.EncrypAES;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.ui.login.LoginContract;
import com.jess.arms.di.scope.ActivityScope;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import common.PEApplication;
import common.PEPresenter;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/13.
 */
@ActivityScope
public class LoginPresenter extends PEPresenter<LoginContract.View, LoginContract.Model> {

    @Inject
    public LoginPresenter(LoginContract.View view, LoginContract.Model model) {
        super(view, model);
    }

    public void loadHistoryAccount(Context context) {
        Set<String> accountsSet = mModel.loadHistoryAccounts();
        Logger.d("HistoryAccounts Counts:" + accountsSet.size());
        ArrayList<String> accoutsList = new ArrayList<>(accountsSet);
        NoFilterringAdapter adapter = new NoFilterringAdapter(context,
                android.R.layout.simple_spinner_dropdown_item, accoutsList);
        mRootView.setAdapter(adapter);
    }

    public void login(String userAccount, String password) {
        mRootView.clearErrorInfo();
        boolean isIllegalParams = false;
        boolean isAccountIllegal = false;

        if (TextUtils.isEmpty(userAccount)) {
            mRootView.showAccountError(
                    PEApplication.getContext().getString(R.string.msg_account_illegal));
            isIllegalParams = true;
            isAccountIllegal = true;
        }

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mRootView.showPasswordError(
                    PEApplication.getContext().getString(R.string.msg_password_illegal), isAccountIllegal);
            isIllegalParams = true;
        }

        if (!isIllegalParams) {
            startLogin(userAccount, password);
        }
    }

    private void startLogin(final String userAccount, final String password) {
        Subscription s = mModel.requestToken(userAccount, password)
                .retryWhen(new RetryWithDelay(2, 2))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();
                    }
                })
                .subscribe(new BaseSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        saveUserInfo(userBean, userAccount, password);
                        mRootView.showLoginSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.showLoginFailed();
                    }
                });
        addSubscrebe(s);
    }

    private void saveUserInfo(UserBean userBean, String userAccount, String password) {
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

    public void saveSkipLoginTrue() {
        mModel.saveSkipLoginTrue();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }
}
