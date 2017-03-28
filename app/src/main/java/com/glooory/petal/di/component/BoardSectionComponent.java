package com.glooory.petal.di.component;

import com.glooory.petal.di.module.BoardSectionModule;
import com.glooory.petal.mvp.ui.board.pin.BoardPinFragment;
import com.jess.arms.di.scope.FragmentScope;

import common.AppComponent;
import dagger.Component;

/**
 * Created by Glooory on 17/3/28.
 */
@FragmentScope
@Component(modules = BoardSectionModule.class, dependencies = AppComponent.class)
public interface BoardSectionComponent {

    void inject(BoardPinFragment boardPinFragment);
}
