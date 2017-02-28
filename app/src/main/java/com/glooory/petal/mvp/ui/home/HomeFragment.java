package com.glooory.petal.mvp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.glooory.petal.R;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.di.component.DaggerHomeComponent;
import com.glooory.petal.di.module.HomeModule;
import com.glooory.petal.mvp.presenter.HomePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import common.AppComponent;
import common.PEFragment;

/**
 * Created by Glooory on 17/2/25.
 */

public class HomeFragment extends PEFragment<HomePresenter> implements HomeContract.View {

    private static final String ARGS_HOME_PIN_TYPE = "pin_type";
    public static final int PIN_TYPE_FOLLOWING = 0;
    public static final int PIN_TYPE_LATEST = 1;
    public static final int PIN_TYPE_POPULAR = 2;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int mTypeIndex;
    HomePinsAdapter mAdapter;
    @Inject
    HomePresenter mPresenter;

    public static HomeFragment newInstance(int pinTypeIndex) {
        Bundle args = new Bundle();
        args.putInt(ARGS_HOME_PIN_TYPE, pinTypeIndex);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTypeIndex = getArguments().getInt(ARGS_HOME_PIN_TYPE);
    }

    @Override
    protected View initView(ViewGroup container) {
        mRootView = LayoutInflater.from(mActivity)
                .inflate(R.layout.view_swiperefreshlayout_recyclerview, container, false);
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(mActivity, R.color.blue_g_i),
                ContextCompat.getColor(mActivity, R.color.red_error),
                ContextCompat.getColor(mActivity, R.color.yellow_g_i),
                ContextCompat.getColor(mActivity, R.color.green_g_i)
        );
        mRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        initAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.requestPinsFirstTime(mTypeIndex);
        return mRootView;
    }

    private void initAdapter() {
        mAdapter.setLoadMoreView(new WindmillLoadMoreFooter());
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.requestMorePins(mTypeIndex);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setAdapter(HomePinsAdapter adapter) {
        this.mAdapter = adapter;
    }
}
