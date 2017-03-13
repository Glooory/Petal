package com.glooory.petal.di.component;

import com.glooory.petal.di.module.LoginModule;
import com.glooory.petal.mvp.ui.login.LoginActivity;
import com.jess.arms.di.scope.ActivityScope;

import common.AppComponent;
import dagger.Component;

/**
 * Created by Glooory on 17/3/13.
 */
@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);
}
