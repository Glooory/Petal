package com.glooory.petal.di.component;

import com.glooory.petal.di.module.HomeModule;
import com.glooory.petal.mvp.ui.home.HomeFragment;
import com.jess.arms.di.scope.FragmentScope;

import common.AppComponent;
import dagger.Component;

/**
 * Created by Glooory on 17/2/25.
 */
@FragmentScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {

    void inject(HomeFragment homeFragment);
}
