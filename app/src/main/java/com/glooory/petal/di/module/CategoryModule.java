package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.CategoryModel;
import com.glooory.petal.mvp.ui.category.CategoryContract;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Glooory on 17/4/1.
 */
@Module
public class CategoryModule {

    private CategoryContract.View mView;

    public CategoryModule(CategoryContract.View view) {
        mView = view;
    }

    @FragmentScope
    @Provides
    CategoryContract.View provideCategoryContractView() {
        return mView;
    }

    @FragmentScope
    @Provides
    CategoryContract.Model provideCategoryContractModel(CategoryModel categoryModel) {
        return categoryModel;
    }
}
