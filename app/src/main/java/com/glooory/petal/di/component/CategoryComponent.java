package com.glooory.petal.di.component;

import com.glooory.petal.di.module.CategoryModule;
import com.jess.arms.di.scope.FragmentScope;

import common.AppComponent;
import dagger.Component;

/**
 * Created by Glooory on 17/4/1.
 */
@FragmentScope
@Component(modules = CategoryModule.class, dependencies = AppComponent.class)
public interface CategoryComponent {


}
