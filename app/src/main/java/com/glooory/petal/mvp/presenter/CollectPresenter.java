package com.glooory.petal.mvp.presenter;

import com.glooory.petal.mvp.ui.collect.CollectContract;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import common.BasePetalPresenter;

/**
 * Created by Glooory on 17/4/3.
 */
@ActivityScope
public class CollectPresenter extends BasePetalPresenter<CollectContract.View, CollectContract.Model> {

    @Inject
    public CollectPresenter(CollectContract.View rootView, CollectContract.Model model) {
        super(rootView, model);
    }


}
