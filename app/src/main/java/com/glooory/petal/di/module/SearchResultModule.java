package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.SearchResultModel;
import com.glooory.petal.mvp.ui.searchresult.SearchResultContract;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Glooory on 17/3/31.
 */
@Module
public class SearchResultModule {

    private SearchResultContract.View mView;

    public SearchResultModule(SearchResultContract.View view) {
        mView = view;
    }

    @FragmentScope
    @Provides
    SearchResultContract.View provideSearchResultContractView() {
        return mView;
    }

    @FragmentScope
    @Provides
    SearchResultContract.Model provideSearchResultContractModel(SearchResultModel searchResultModel) {
        return searchResultModel;
    }
}
