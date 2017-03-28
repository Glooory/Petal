package com.glooory.petal.di.component;

import com.glooory.petal.di.module.BoardModule;
import com.glooory.petal.mvp.ui.board.BoardActivity;
import com.jess.arms.di.scope.ActivityScope;

import common.AppComponent;
import dagger.Component;

/**
 * Created by Glooory on 17/3/28.
 */
@ActivityScope
@Component(modules = BoardModule.class, dependencies = AppComponent.class)
public interface BoardComponent {

    void inject(BoardActivity boardActivity);
}
