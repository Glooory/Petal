package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.LoginModel;
import com.glooory.petal.mvp.ui.login.LoginContract;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Glooory on 17/3/13.
 */
@Module
public class LoginModule {

    private LoginContract.View mView;

    public LoginModule(LoginContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    LoginContract.View provideLoginContractView() {
        return mView;
    }

    @ActivityScope
    @Provides
    LoginContract.Model provideLoginContractModel(LoginModel loginModel) {
        return loginModel;
    }

}
