package com.glooory.petal.di.component;

import com.glooory.petal.di.module.UserModule;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.jess.arms.di.scope.ActivityScope;

import common.AppComponent;
import dagger.Component;

/**
 * Created by Glooory on 17/3/22.
 */
@ActivityScope
@Component(modules = UserModule.class, dependencies = AppComponent.class)
public interface UserCompoment {

    void inject(UserActivity userActivity);
}
