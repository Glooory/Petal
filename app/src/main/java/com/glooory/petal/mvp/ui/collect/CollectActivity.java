package com.glooory.petal.mvp.ui.collect;

import android.app.Activity;
import android.content.Intent;

import com.glooory.petal.di.component.DaggerCollectComponent;
import com.glooory.petal.di.module.CollectModule;
import com.glooory.petal.mvp.presenter.CollectPresenter;

import common.AppComponent;
import common.BasePetalActivity;

/**
 * Created by Glooory on 17/4/3.
 */

public class CollectActivity extends BasePetalActivity<CollectPresenter>
        implements CollectContract.View {



    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, CollectActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCollectComponent.builder()
                .appComponent(appComponent)
                .collectModule(new CollectModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }
}
