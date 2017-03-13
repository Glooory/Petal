package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.HomeModel;
import com.glooory.petal.mvp.ui.home.HomeContract;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Glooory on 17/2/25.
 */
@Module
public class HomeModule {

    private HomeContract.View mView;

    public HomeModule(HomeContract.View view) {
        mView = view;
    }

    @FragmentScope
    @Provides
    HomeContract.View provideHomeContractView() {
        return mView;
    }

    @FragmentScope
    @Provides
    HomeContract.Model provideHomeContractModel(HomeModel homeModel) {
        return homeModel;
    }
}
