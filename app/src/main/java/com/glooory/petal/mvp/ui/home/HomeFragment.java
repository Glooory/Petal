package com.glooory.petal.mvp.ui.home;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.glooory.petal.R;
import com.glooory.petal.di.component.DaggerHomeComponent;
import com.glooory.petal.di.module.HomeModule;
import com.glooory.petal.mvp.presenter.HomePresenter;

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalFragment;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/2/25.
 */

public class HomeFragment extends BasePetalFragment<HomePresenter> implements HomeContract.View,
        SwipeRefreshLayout.OnRefreshListener{

    private static final String ARGS_HOME_PIN_TYPE = "pin_type";
    public static final int PIN_TYPE_FOLLOWING = 0;
    public static final int PIN_TYPE_LATEST = 1;
    public static final int PIN_TYPE_POPULAR = 2;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int mTypeIndex;
    HomePinAdapter mAdapter;

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
        return mRootView;
    }

    @Override
    protected void setupViews() {
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(mActivity, R.color.blue_google_icon),
                ContextCompat.getColor(mActivity, R.color.red_google_icon),
                ContextCompat.getColor(mActivity, R.color.yellow_google_icon),
                ContextCompat.getColor(mActivity, R.color.green_google_icon)
        );
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.requestPinsFirstTime(mTypeIndex);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_pin_img:
                        mPresenter.launchPinDetailActivity(getActivity(), view, position);
                        break;
                    case R.id.ll_pin_via_info:
                        mPresenter.launchUserActivity(getActivity(), view, position);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getBasicUserInfo();
    }

    @Override
    public void showLoading() {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {

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
    public void setAdapter(HomePinAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void showLoadingMore() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.requestMorePins(mTypeIndex);
            }
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.requestPinsFirstTime(mTypeIndex);
    }
}
