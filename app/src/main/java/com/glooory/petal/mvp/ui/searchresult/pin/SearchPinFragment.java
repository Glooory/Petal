package com.glooory.petal.mvp.ui.searchresult.pin;

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
import com.glooory.petal.app.widget.CustomStaggeredGridLayoutManager;
import com.glooory.petal.di.component.DaggerSearchResultComponent;
import com.glooory.petal.di.module.SearchResultModule;
import com.glooory.petal.mvp.presenter.SearchResultPresenter;
import com.glooory.petal.mvp.ui.home.HomePinAdapter;
import com.glooory.petal.mvp.ui.searchresult.SearchResultActivity;
import com.glooory.petal.mvp.ui.searchresult.SearchResultContract;

import javax.inject.Inject;

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalFragment;

/**
 * Created by Glooory on 17/3/31.
 */

public class SearchPinFragment extends BasePetalFragment<SearchResultPresenter>
        implements SearchResultContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    HomePinAdapter mAdapter;
    private View mNoMoreDataFooter;
    private String mSearchKeyword;

    public static SearchPinFragment newInstance(String keyword) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_SEARCH_KEYWORD, keyword);
        SearchPinFragment fragment = new SearchPinFragment();
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
        mPresenter.getSearchedPins(mSearchKeyword);
    }

    @Override
    protected void setupViews() {
        mRecyclerView.setLayoutManager(new CustomStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_pin_img:
                        mPresenter.launchPinDetailActivity(getActivity(), view, position);
                        break;
                    case R.id.ll_pin_via_info:
                        mPresenter.launchUserActivityFromPin(getActivity(), view, position);
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
        ((SearchResultActivity) getActivity()).setTabTitles(titles);
    }

    @Override
    public void showLoadingMore() {
        if (mAdapter.getData().size() >= mPresenter.getPinCount()) {
            return;
        }

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.getSearchedPinsMore();
            }
        });
    }

    @Override
    public void showNoMoreDataFooter(boolean showAnyway) {
        if (showAnyway || mAdapter.getData().size() >= mPresenter.getPinCount()) {
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

    }

    public void onRefresh() {
        mAdapter.removeAllFooterView();
        mPresenter.getSearchedPins(mSearchKeyword);
    }
}
