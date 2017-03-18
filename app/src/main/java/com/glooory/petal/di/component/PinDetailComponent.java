package com.glooory.petal.di.component;

import com.glooory.petal.di.module.PinDetailModule;
import com.glooory.petal.mvp.ui.pindetail.PinDetailActivity;
import com.jess.arms.di.scope.ActivityScope;

import common.AppComponent;
import dagger.Component;

/**
 * Created by Glooory on 17/3/18.
 */
@ActivityScope
@Component(modules = PinDetailModule.class, dependencies = AppComponent.class)
public interface PinDetailComponent {

    void inject(PinDetailActivity pinDetailActivity);
}
