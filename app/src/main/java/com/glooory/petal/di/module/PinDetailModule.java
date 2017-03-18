package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.PinDetailModel;
import com.glooory.petal.mvp.ui.pindetail.PinDetailContract;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Glooory 17/3/18.
 */
@Module
public class PinDetailModule {

    private PinDetailContract.View mView;

    public PinDetailModule(PinDetailContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    PinDetailContract.View providePinDetailContractView() {
        return mView;
    }

    @ActivityScope
    @Provides
    PinDetailContract.Model providePinDetailContractModel(PinDetailModel pinDetailModel) {
        return pinDetailModel;
    }
}
