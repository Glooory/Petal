package com.glooory.petal.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.glooory.petal.R;
import com.glooory.petal.app.adapter.NoFilterringAdapter;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.ui.login.LoginContract;
import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.Set;

import javax.inject.Inject;

import common.BasePetalPresenter;
import common.PetalApplication;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/13.
 */
@ActivityScope
public class LoginPresenter extends BasePetalPresenter<LoginContract.View, LoginContract.Model> {

    @Inject
    public LoginPresenter(LoginContract.View view, LoginContract.Model model) {
        super(view, model);
    }

    public void loadHistoryAccount(Context context) {
        Set<String> accountsSet = mModel.loadHistoryAccounts();
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
                    PetalApplication.getContext().getString(R.string.msg_cannot_be_empty));
            isIllegalParams = true;
            isAccountIllegal = true;
        }

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mRootView.showPasswordError(
                    PetalApplication.getContext().getString(R.string.msg_password_illegal), isAccountIllegal);
            isIllegalParams = true;
        }

        if (!isIllegalParams) {
            performLogin(userAccount, password);
        }
    }

    private void performLogin(final String userAccount, final String password) {
        Subscription s = mModel.requestToken(userAccount, password)
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
                        mRootView.showLoginSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mRootView.showLoginFailed();
                    }
                });
        addSubscrebe(s);
    }

    public void saveSkipLoginTrue() {
        mModel.saveSkipLoginTrue();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }
}
