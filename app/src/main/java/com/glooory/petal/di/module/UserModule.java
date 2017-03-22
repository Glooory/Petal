package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.UserModel;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Glooory on 17/3/22.
 */
@Module
public class UserModule {

    private UserContract.View mView;

    public UserModule(UserContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    UserContract.View provideUserContractView() {
        return mView;
    }

    @ActivityScope
    @Provides
    UserContract.Model provideUserContractModel(UserModel userModel) {
        return userModel;
    }
}
