package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.CollectModel;
import com.glooory.petal.mvp.ui.collect.CollectContract;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Glooory on 17/4/3.
 */
@Module
public class CollectModule {

    private CollectContract.View mView;

    public CollectModule(CollectContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    CollectContract.View provideCollectContractView() {
        return mView;
    }

    @ActivityScope
    @Provides
    CollectContract.Model provideCollectContractModel(CollectModel collectModel) {
        return collectModel;
    }
}
