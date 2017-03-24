package com.glooory.petal.di.component;

import com.glooory.petal.di.module.UserSectionModule;
import com.glooory.petal.mvp.ui.user.board.UserBoardFragment;
import com.glooory.petal.mvp.ui.user.pin.UserPinFragment;
import com.jess.arms.di.scope.FragmentScope;

import common.AppComponent;
import dagger.Component;

/**
 * Created by Glooory on 17/3/23.
 */
@FragmentScope
@Component(modules = UserSectionModule.class, dependencies = AppComponent.class)
public interface UserSectionComponent {

    void inject(UserBoardFragment userBoardFragment);

    void inject(UserPinFragment userPinFragment);
}
