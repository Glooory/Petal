package com.glooory.petal.mvp.ui.user.following;

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
import com.glooory.petal.di.component.DaggerUserSectionComponent;
import com.glooory.petal.di.module.UserSectionModule;
import com.glooory.petal.mvp.presenter.UserSectionPresenter;
import com.glooory.petal.mvp.ui.login.LoginActivity;
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

public class UserFollowingFragment extends BaseRecyclerFragment<UserSectionPresenter>
        implements UserContract.SectionView {

    private static final String ARGS_FOLLOWING_COUNT = "following_count";

    @Inject
    UserAdapter mAdapter;
    private String mUserId;
    private int mFollowingCount;

    public static UserFollowingFragment newInstance(String userId, int followingCount) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_USER_ID, userId);
        args.putInt(ARGS_FOLLOWING_COUNT, followingCount);
        UserFollowingFragment followingFragment = new UserFollowingFragment();
        followingFragment.setArguments(args);
        return followingFragment;
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
        mFollowingCount = getArguments().getInt(ARGS_FOLLOWING_COUNT);
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
    protected void lazyFetchData() {
        super.lazyFetchData();
        if (mFollowingCount <= 0) {
            mAdapter.addFooterView(mNoMoreDataFooter);
        } else {
            mPresenter.getUserFollowing(mUserId);
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
        if (mAdapter.getData().size() >= mFollowingCount) {
            return;
        }

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.getUserFollowingMore();
            }
        });
    }

    @Override
    public void showNoMoreDataFooter(boolean showAnyway) {
        if (showAnyway || mAdapter.getData().size() >= mFollowingCount || mFollowingCount == 0) {
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
        mPresenter.getUserFollowing(mUserId);
    }

    public void setFollowingCount(int followingCount) {
        mFollowingCount = followingCount;
    }
}
