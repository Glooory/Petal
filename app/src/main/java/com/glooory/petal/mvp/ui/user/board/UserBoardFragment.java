package com.glooory.petal.mvp.ui.user.board;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.DialogUtils;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.app.widget.CustomStaggeredGridLayoutManager;
import com.glooory.petal.di.component.DaggerUserSectionComponent;
import com.glooory.petal.di.module.UserSectionModule;
import com.glooory.petal.mvp.presenter.UserSectionPresenter;
import com.glooory.petal.mvp.ui.login.LoginActivity;
import com.glooory.petal.mvp.ui.pindetail.EditPinDialogFragment;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.glooory.petal.mvp.ui.user.UserContract;

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalFragment;

/**
 * Created by Glooory on 17/3/22.
 */

public class UserBoardFragment extends BasePetalFragment<UserSectionPresenter>
        implements UserContract.SectionView {

    private static final String BUNDLE_BOARD_COUNT = "board_count";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private boolean mIsMe;
    private UserBoardAdapter mAdapter;
    private int mBoardCount;
    private String mUserId;
    private String mUserName;
    private View mNoMoreDataFooter;

    public static UserBoardFragment newInstance(String userId, String userName, int boardCount) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_USER_ID, userId);
        args.putString(Constants.EXTRA_USER_NAME, userName);
        args.putInt(BUNDLE_BOARD_COUNT, boardCount);
        UserBoardFragment fragment = new UserBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserName = getArguments().getString(Constants.EXTRA_USER_NAME);
        mUserId = getArguments().getString(Constants.EXTRA_USER_ID);
        mBoardCount = getArguments().getInt(BUNDLE_BOARD_COUNT);
        mIsMe = mPresenter.isMe(mUserId);
        mAdapter = new UserBoardAdapter(mIsMe);
        mPresenter.setAdapter(mAdapter);
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
    protected void setupViews() {
        mRecyclerView.setLayoutManager(new CustomStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_user_board_cover:
                        mPresenter.launchBoardActivity(getActivity(), mUserName, position);
                        break;
                    case R.id.ll_user_board_operate:
                        mPresenter.onBoardOperateBtnClick(position);
                        break;
                }
            }
        });
        if (mBoardCount <= 0) {
            mAdapter.addFooterView(mNoMoreDataFooter);
        } else {
            mPresenter.getUserBoards(mUserId);
        }
        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if (mIsMe) {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.onFABClicked();
                }
            });
        }
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
    public void onResume() {
        super.onResume();

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
        ((UserActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((UserActivity) getActivity()).hideLoading();
    }

    @Override
    public void showMessage(String message) {
        SnackbarUtil.showLong(getActivity(), message);
    }

    @Override
    public void showLoadingMore() {
        if (mAdapter.getData().size() >= mBoardCount) {
            return;
        }

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.getUserBoardsMore();
            }
        });
    }

    @Override
    public void showNoMoreDataFooter(boolean showAnyway) {
        if (showAnyway || mAdapter.getData().size() >= mBoardCount) {
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
        editBoardDiglogFragment.show(getActivity().getSupportFragmentManager(), null);
    }

    @Override
    public void showDeleteBoardConfirmDialog(final String boardId, final int position) {
        DialogUtils.show(getActivity(), R.string.msg_delete_waring, R.string.msg_cancel,
                R.string.msg_confirm, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteBoard(boardId, position);
                    }
                });
    }

    @Override
    public void showCreateBoardDialog(CreateBoardDialogFragment fragment) {
        fragment.show(getActivity().getSupportFragmentManager(), null);
    }

    @Override
    public void showLatestUserInfo() {
        ((UserActivity) getActivity()).onRefresh();
    }

    @Override
    public void showEditPinDialog(EditPinDialogFragment editPinDialogFragment) {

    }

    @Override
    public void showDeletePinConfirmDialog(String pinId, int position) {

    }

    public void onRefresh() {
        mPresenter.getUserBoards(mUserId);
    }

    public void setBoardCount(int boardCount) {
        mBoardCount = boardCount;
    }
}
