package com.glooory.petal.di.component;

import com.glooory.petal.di.module.CollectModule;
import com.glooory.petal.mvp.ui.collect.CollectActivity;
import com.jess.arms.di.scope.ActivityScope;

import common.AppComponent;
import dagger.Component;

/**
 * Created by Glooory on 17/4/3.
 */
@ActivityScope
@Component(modules = CollectModule.class, dependencies = AppComponent.class)
public interface CollectComponent {

    void inject(CollectActivity collectActivity);
}
