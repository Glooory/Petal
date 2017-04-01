package com.glooory.petal.di.component;

import com.glooory.petal.di.module.SearchResultModule;
import com.glooory.petal.mvp.ui.searchresult.board.SearchBoardFragment;
import com.glooory.petal.mvp.ui.searchresult.pin.SearchPinFragment;
import com.jess.arms.di.scope.FragmentScope;

import common.AppComponent;
import dagger.Component;

/**
 * Created by Glooory on 17/3/31.
 */
@FragmentScope
@Component(modules = SearchResultModule.class, dependencies = AppComponent.class)
public interface SearchResultComponent {

    void inject(SearchPinFragment searchPinFragment);

    void inject(SearchBoardFragment searchBoardFragment);
}
