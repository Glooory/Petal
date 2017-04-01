package com.glooory.petal.mvp.presenter;

import com.glooory.petal.mvp.ui.category.CategoryContract;
import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import common.BasePetalAdapter;
import common.BasePetalPresenter;

/**
 * Created by Glooory on 17/4/1.
 */
@FragmentScope
public class CategoryPresenter extends BasePetalPresenter<CategoryContract.View, CategoryContract.Model> {

    private BasePetalAdapter mAdapter;
    private int mPinCount;
    private int mBoardCount;
    private int mUserCount;


    @Inject
    public CategoryPresenter(CategoryContract.View rootView, CategoryContract.Model model) {
        super(rootView, model);
    }


    public int getPinCount() {
        return mPinCount;
    }

    public int getBoardCount() {
        return mBoardCount;
    }

    public int getUserCount() {
        return mUserCount;
    }
}
