package com.glooory.petal.di.component;

import com.glooory.petal.di.module.CategoryModule;
import com.glooory.petal.mvp.ui.category.board.CategoryBoardFragment;
import com.glooory.petal.mvp.ui.category.pin.CategoryPinFragment;
import com.glooory.petal.mvp.ui.category.user.CategoryUserFragment;
import com.jess.arms.di.scope.FragmentScope;

import common.AppComponent;
import dagger.Component;

/**
 * Created by Glooory on 17/4/1.
 */
@FragmentScope
@Component(modules = CategoryModule.class, dependencies = AppComponent.class)
public interface CategoryComponent {

    void inject(CategoryPinFragment categoryPinFragment);

    void inject(CategoryBoardFragment categoryBoardFragment);

    void inject(CategoryUserFragment categoryUserFragment);
}
