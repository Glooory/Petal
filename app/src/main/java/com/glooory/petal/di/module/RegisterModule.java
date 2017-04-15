package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.RegisterModel;
import com.glooory.petal.mvp.ui.register.RegisterContract;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Glooory on 17/4/14.
 */

@Module
public class RegisterModule {

    private RegisterContract.View mView;

    public RegisterModule(RegisterContract.View view) {
        mView = view;
    }

    @FragmentScope
    @Provides
    RegisterContract.View provideRegisterContractView() {
        return mView;
    }

    @FragmentScope
    @Provides
    RegisterContract.Model provideRegisterContractModel(RegisterModel registerModel) {
        return registerModel;
    }
}
