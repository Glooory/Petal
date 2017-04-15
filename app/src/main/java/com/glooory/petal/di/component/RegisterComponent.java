package com.glooory.petal.di.component;

import com.glooory.petal.di.module.RegisterModule;
import com.glooory.petal.mvp.ui.register.RegisterFragment;
import com.jess.arms.di.scope.FragmentScope;

import common.AppComponent;
import dagger.Component;

/**
 * Created by Glooory on 17/4/14.
 */

@FragmentScope
@Component(modules = RegisterModule.class, dependencies = AppComponent.class)
public interface RegisterComponent {

    void inject(RegisterFragment registerFragment);
}
