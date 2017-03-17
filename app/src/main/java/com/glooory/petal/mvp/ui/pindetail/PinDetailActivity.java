package com.glooory.petal.mvp.ui.pindetail;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;

import butterknife.BindView;
import common.AppComponent;
import common.PEActivity;

/**
 * Created by Glooory on 17/3/17.
 */

public class PinDetailActivity extends PEActivity {


    @BindView(R.id.simple_drawee_view_pin)
    SimpleDraweeView mImagePin;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pin_detail;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
