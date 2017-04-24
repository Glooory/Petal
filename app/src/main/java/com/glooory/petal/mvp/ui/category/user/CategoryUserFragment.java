package com.glooory.petal.mvp.ui.category.user;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.app.widget.CustomStaggeredGridLayoutManager;
import com.glooory.petal.di.component.DaggerCategoryComponent;
import com.glooory.petal.di.module.CategoryModule;
import com.glooory.petal.mvp.presenter.CategoryPresenter;
import com.glooory.petal.mvp.ui.category.CategoryActivity;
import com.glooory.petal.mvp.ui.category.CategoryContract;
import com.glooory.petal.mvp.ui.login.LoginActivity;

import javax.inject.Inject;

import common.AppComponent;
import common.BaseRecyclerFragment;

/**
 * Created by Glooory on 17/4/1.
 */

public class CategoryUserFragment extends BaseRecyclerFragment<CategoryPresenter>
        implements CategoryContract.View {

    @Inject
    CategoryUserAdapter mAdapter;
    private String mCategoryValue;

    public static CategoryUserFragment newInstance(String categoryValue) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_CATEGORY_VALUE, categoryValue);
        CategoryUserFragment fragment = new CategoryUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerCategoryComponent.builder()
                .appComponent(appComponent)
                .categoryModule(new CategoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryValue = getArguments().getString(Constants.BUNDLE_CATEGORY_VALUE);
        mPresenter.setAdapter(mAdapter);
    }

    @Override
    protected void setupViews() {
        mRecyclerView.setLayoutManager(new CustomStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_card_user:
                        mPresenter.launchUserActivityFromUser(getActivity(), view, position);
                        break;
                    case R.id.ll_card_user_operate:
                        mPresenter.onUserCardOperateBtnClick(position);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        mPresenter.getCategoryUsers(mCategoryValue);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.removeAllFooterView();
        mAdapter = null;
    }

    @Override
    public void showLoading() {
        ((CategoryActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((CategoryActivity) getActivity()).hideLoading();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLoadingMore() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.getCategoryUsersMore();
            }
        });
    }

    @Override
    public void showNoMoreDataFooter() {
        if (mNoMoreDataFooter.getParent() != null) {
            ((ViewGroup) mNoMoreDataFooter.getParent()).removeView(mNoMoreDataFooter);
        }
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.loadMoreEnd();
                mAdapter.addFooterView(mNoMoreDataFooter);
            }
        });
    }

    @Override
    public void showLoginNav() {
        SnackbarUtil.showLong(getActivity(), R.string.msg_login_hint, R.string.msg_go_login,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginActivity.launch(getActivity(), false);
                    }
                });
    }

    public void onRefresh() {
        mAdapter.removeAllFooterView();
        mPresenter.getCategoryUsers(mCategoryValue);
    }
}
