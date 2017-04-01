package com.glooory.petal.mvp.ui.category.board;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
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

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalFragment;

/**
 * Created by Glooory on 17/4/1.
 */

public class CategoryBoardFragment extends BasePetalFragment<CategoryPresenter>
        implements CategoryContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CategoryBoardAdapter mAdapter;
    private View mNoMoreDataFooter;
    private String mCategoryValue;

    public static CategoryBoardFragment newInstance(String categoryValue) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_CATEGORY_VALUE, categoryValue);
        CategoryBoardFragment fragment = new CategoryBoardFragment();
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
        mAdapter = new CategoryBoardAdapter();
        mPresenter.setAdapter(mAdapter);
        mPresenter.getCategoryBoards(mCategoryValue);
    }

    @Override
    protected void setupViews() {
        mRecyclerView.setLayoutManager(new CustomStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_user_board_cover:
                        mPresenter.launchBoardActivity(getActivity(), position);
                        break;
                    case R.id.ll_category_board_user:
                        mPresenter.launchUserActivityFromBoard(getActivity(), view, position);
                        break;
                    case R.id.ll_user_board_operate:
                        mPresenter.onBoardOperateBtnClick(position);
                        break;
                }
            }
        });
    }

    @Override
    protected View initView(ViewGroup container) {
        mRootView = LayoutInflater.from(getActivity())
                .inflate(R.layout.view_recycler_view, container, false);
        mNoMoreDataFooter = LayoutInflater.from(getActivity())
                .inflate(R.layout.view_footer_no_more_data, null);
        return mRootView;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.removeAllFooterView();
        mAdapter = null;
        mNoMoreDataFooter = null;
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
                mPresenter.getCategoryBoardsMore();
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
        mPresenter.getCategoryBoards(mCategoryValue);
    }
}
