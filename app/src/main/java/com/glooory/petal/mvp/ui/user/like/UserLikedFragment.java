package com.glooory.petal.mvp.ui.user.like;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.widget.CustomStaggeredGridLayoutManager;
import com.glooory.petal.di.component.DaggerUserSectionComponent;
import com.glooory.petal.di.module.UserSectionModule;
import com.glooory.petal.mvp.presenter.UserSectionPresenter;
import com.glooory.petal.mvp.ui.home.HomePinAdapter;
import com.glooory.petal.mvp.ui.pindetail.EditPinDialogFragment;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.glooory.petal.mvp.ui.user.board.CreateBoardDialogFragment;
import com.glooory.petal.mvp.ui.user.board.EditBoardDiglogFragment;

import javax.inject.Inject;

import common.AppComponent;
import common.BaseRecyclerFragment;

/**
 * Created by Glooory on 17/3/25.
 */

public class UserLikedFragment extends BaseRecyclerFragment<UserSectionPresenter>
        implements UserContract.SectionView {

    private static final String ARGS_LIKED_COUNT = "liked_count";

    @Inject
    HomePinAdapter mAdapter;
    private String mUserId;
    private int mLikedCount;

    public static UserLikedFragment newInstance(String userId, int likedCount) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_USER_ID, userId);
        args.putInt(ARGS_LIKED_COUNT, likedCount);
        UserLikedFragment fragment = new UserLikedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerUserSectionComponent.builder()
                .appComponent(appComponent)
                .userSectionModule(new UserSectionModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = getArguments().getString(Constants.EXTRA_USER_ID);
        mLikedCount = getArguments().getInt(ARGS_LIKED_COUNT);
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
    protected void lazyFetchData() {
        super.lazyFetchData();
        if (mLikedCount <= 0) {
            mAdapter.addFooterView(mNoMoreDataFooter);
        } else {
            mPresenter.getUserLikedPins(mUserId);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAdapter != null) {
            mAdapter.removeAllFooterView();
        }
    }

    @Override
    public void onDestroy() {
        mAdapter = null;
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        ((UserActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((UserActivity) getActivity()).hideLoading();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLoadingMore() {
        if (mAdapter.getData().size() >= mLikedCount) {
            return;
        }

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.getUserLikedPinsMore();
            }
        });
    }

    @Override
    public void showNoMoreDataFooter(boolean showAnyway) {
        if (showAnyway || mAdapter.getData().size() >= mLikedCount || mLikedCount == 0) {
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

    @Override
    public void showEditBoardDialog(EditBoardDiglogFragment editBoardDiglogFragment) {

    }

    @Override
    public void showDeleteBoardConfirmDialog(String boardId, int position) {

    }

    @Override
    public void showCreateBoardDialog(CreateBoardDialogFragment fragment) {

    }

    @Override
    public void showLatestUserInfo() {

    }

    @Override
    public void showEditPinDialog(EditPinDialogFragment editPinDialogFragment) {

    }

    @Override
    public void showDeletePinConfirmDialog(String pinId, int position) {

    }

    public void onRefresh() {
        mAdapter.removeAllFooterView();
        mPresenter.getUserLikedPins(mUserId);
    }

    public void setLikedCount(int likedCount) {
        mLikedCount = likedCount;
    }
}
