package com.glooory.petal.mvp.ui.searchresult.board;

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
import com.glooory.petal.di.component.DaggerSearchResultComponent;
import com.glooory.petal.di.module.SearchResultModule;
import com.glooory.petal.mvp.presenter.SearchResultPresenter;
import com.glooory.petal.mvp.ui.category.board.CategoryBoardAdapter;
import com.glooory.petal.mvp.ui.login.LoginActivity;
import com.glooory.petal.mvp.ui.searchresult.SearchResultActivity;
import com.glooory.petal.mvp.ui.searchresult.SearchResultContract;

import javax.inject.Inject;

import common.AppComponent;
import common.BaseRecyclerFragment;

/**
 * Created by Glooory on 17/4/1.
 */

public class SearchBoardFragment extends BaseRecyclerFragment<SearchResultPresenter>
        implements SearchResultContract.View{

    @Inject
    CategoryBoardAdapter mAdapter;
    private String mSearchKeyword;

    public static SearchBoardFragment newInstance(String keyword) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_SEARCH_KEYWORD, keyword);
        SearchBoardFragment fragment = new SearchBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerSearchResultComponent.builder()
                .appComponent(appComponent)
                .searchResultModule(new SearchResultModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchKeyword = getArguments().getString(Constants.EXTRA_SEARCH_KEYWORD);
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
    protected void initData() {

    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        mPresenter.getSearchedBoards(mSearchKeyword);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.removeAllFooterView();
        mAdapter = null;
    }

    @Override
    public void showLoading() {
        ((SearchResultActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((SearchResultActivity) getActivity()).hideLoading();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showTabTitles(String[] titles) {

    }

    @Override
    public void showLoadingMore() {
        if (mAdapter.getData().size() >= mPresenter.getBoardCount()) {
            return;
        }

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.getSearchedBoardsMore();
            }
        });
    }

    @Override
    public void showNoMoreDataFooter(boolean showAnyway) {
        if (showAnyway || mAdapter.getData().size() >= mPresenter.getBoardCount()) {
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
        mPresenter.getSearchedBoards(mSearchKeyword);
    }
}
