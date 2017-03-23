package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.UserSectionModel;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Glooory on 17/3/23.
 */
@Module
public class UserSectionModule {

    private UserContract.SectionView mView;

    @Inject
    public UserSectionModule(UserContract.SectionView view) {
        mView = view;
    }

    @FragmentScope
    @Provides
    UserContract.SectionView provideUserContractSectionView() {
        return mView;
    }

    @FragmentScope
    @Provides
    UserContract.SectionModel provideUserContractSectionModel(UserSectionModel userSectionModel) {
        return userSectionModel;
    }
}
