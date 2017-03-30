package com.glooory.petal.mvp.ui.board.follower;

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
import com.glooory.petal.di.component.DaggerBoardSectionComponent;
import com.glooory.petal.di.module.BoardSectionModule;
import com.glooory.petal.mvp.presenter.BoardSectionPresenter;
import com.glooory.petal.mvp.ui.board.BoardActivity;
import com.glooory.petal.mvp.ui.board.BoardContract;
import com.glooory.petal.mvp.ui.login.LoginActivity;
import com.glooory.petal.mvp.ui.pindetail.EditPinDialogFragment;
import com.glooory.petal.mvp.ui.user.following.UserAdapter;

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalFragment;

/**
 * Created by Glooory on 17/3/29.
 */

public class BoardFollowerFragment extends BasePetalFragment<BoardSectionPresenter>
        implements BoardContract.SectionView {

    private static final String ARGS_FOLLOWER_COUNT = "follower_count";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private UserAdapter mAdapter;
    private int mFollowerCount;
    private String mBoardId;
    private View mNoMoreDataFooter;

    public static BoardFollowerFragment newInstance(String boardId, int followerCount) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_BOARD_ID, boardId);
        args.putInt(ARGS_FOLLOWER_COUNT, followerCount);
        BoardFollowerFragment followerFragment = new BoardFollowerFragment();
        followerFragment.setArguments(args);
        return followerFragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerBoardSectionComponent.builder()
                .appComponent(appComponent)
                .boardSectionModule(new BoardSectionModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBoardId = getArguments().getString(Constants.EXTRA_BOARD_ID);
        mFollowerCount = getArguments().getInt(ARGS_FOLLOWER_COUNT);
        mAdapter = new UserAdapter();
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
        if (mFollowerCount <= 0) {
            mAdapter.addFooterView(mNoMoreDataFooter);
        } else {
            mPresenter.getBoardFollowers(mBoardId);
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
        ((BoardActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((BoardActivity) getActivity()).hideLoading();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLoadingMore() {
        if (mAdapter.getData().size() >= mFollowerCount) {
            return;
        }

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.getBoardFollowersMore();
            }
        });
    }

    @Override
    public void showNoMoreDataFooter(boolean showAnyway) {
        if (showAnyway || mAdapter.getData().size() >= mFollowerCount) {
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
    public void showDeletePinConfirmDialog(String pinId, int position) {

    }

    @Override
    public void showEditPinDialog(EditPinDialogFragment editPinDialogFragment) {

    }

    @Override
    public void showPinDeleted() {

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
        mPresenter.getBoardFollowers(mBoardId);
    }
}
