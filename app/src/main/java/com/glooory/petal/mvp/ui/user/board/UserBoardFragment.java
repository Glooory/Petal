package com.glooory.petal.mvp.ui.user.board;

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
import com.glooory.petal.mvp.presenter.UserChildPresenter;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.glooory.petal.mvp.ui.user.UserContract;

import butterknife.BindView;
import common.AppComponent;
import common.PEFragment;

/**
 * Created by Glooory on 17/3/22.
 */

public class UserBoardFragment extends PEFragment<UserChildPresenter>
        implements UserContract.ChildView {

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

    }

    @Override
    protected void setupViews() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_user_board_cover:
                        // TODO: 17/3/22 Launch BoardActivity
                        break;
                    case R.id.ll_user_board_operate:
                        // TODO: 17/3/22 board operate
                        break;
                }
            }
        });
        mPresenter.getBoards(mUserId);
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
    public void showNoMoreDataFooter() {
        if (mAdapter.getItemCount() >= mBoardCount) {
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
}
