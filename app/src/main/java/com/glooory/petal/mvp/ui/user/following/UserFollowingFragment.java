package com.glooory.petal.mvp.ui.user.following;

import android.content.Intent;
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
import com.glooory.petal.di.component.DaggerUserSectionComponent;
import com.glooory.petal.di.module.UserSectionModule;
import com.glooory.petal.mvp.presenter.UserSectionPresenter;
import com.glooory.petal.mvp.ui.login.LoginActivity;
import com.glooory.petal.mvp.ui.pindetail.EditPinDialogFragment;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.glooory.petal.mvp.ui.user.board.CreateBoardDialogFragment;
import com.glooory.petal.mvp.ui.user.board.EditBoardDiglogFragment;

import butterknife.BindView;
import common.AppComponent;
import common.PEFragment;

/**
 * Created by Glooory on 17/3/25.
 */

public class UserFollowingFragment extends PEFragment<UserSectionPresenter> implements UserContract.SectionView {

    private static final String ARGS_FOLLOWING_COUNT = "following_count";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private UserAdapter mAdapter;
    private String mUserId;
    private int mFollowingCount;
    private View mNoMoreDataFooter;

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
        mAdapter = new UserAdapter();
        mPresenter.setAdapter(mAdapter);
    }

    @Override
    protected void setupViews() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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
        if (mFollowingCount <= 0) {
            mAdapter.addFooterView(mNoMoreDataFooter);
        } else {
            mPresenter.getUserFollowings(mUserId);
        }
    }

    @Override
    protected View initView(ViewGroup container) {
        mRootView = LayoutInflater.from(mActivity)
                .inflate(R.layout.view_recycler_view, container, false);
        mNoMoreDataFooter = LayoutInflater.from(mActivity)
                .inflate(R.layout.view_footer_no_more_data, null);
        return mRootView;
    }

    @Override
    protected void initData() {

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
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void showLoadingMore() {
        if (mAdapter.getData().size() >= mFollowingCount) {
            return;
        }

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.getUserFollowingsMore();
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

    @Override
    public void showDeletePinDataChange() {

    }

    @Override
    public void showFollowingDataChange(boolean isFollowed) {
        if (isFollowed) {
            mFollowingCount--;
        } else {
            mFollowingCount++;
        }
        ((UserActivity) getActivity()).setFollowingCountChanged(mFollowingCount);
    }

    public void onRefresh() {
        mPresenter.getUserFollowings(mUserId);
    }

    public void setFollowingCount(int followingCount) {
        mFollowingCount = followingCount;
    }
}
