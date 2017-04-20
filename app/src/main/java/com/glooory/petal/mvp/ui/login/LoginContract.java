package com.glooory.petal.mvp.ui.login;

import com.glooory.petal.app.adapter.NoFilterringAdapter;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import java.util.Set;

import rx.Observable;

/**
 * Created by Glooory on 17/3/13.
 */

public interface LoginContract {

    interface View extends BaseView {

        void setAdapter(NoFilterringAdapter adapter);

        void clearErrorInfo();

        void showAccountError(String error);

        void showPasswordError(String error, boolean isAccountIllegal);

        void showLoginFailed();

        void showLoginSuccess();
    }

    interface Model extends IModel {

        void saveSkipLoginTrue();

        Set<String> loadHistoryAccounts();

        Observable<UserBean> requestToken(String userAccount, String password);

        void saveUserInfo(UserBean userBean, String userAccount);
    }

}
