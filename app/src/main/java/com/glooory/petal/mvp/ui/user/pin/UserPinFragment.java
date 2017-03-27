package com.glooory.petal.mvp.ui.user.pin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.DialogUtils;
import com.glooory.petal.di.component.DaggerUserSectionComponent;
import com.glooory.petal.di.module.UserSectionModule;
import com.glooory.petal.mvp.presenter.UserSectionPresenter;
import com.glooory.petal.mvp.ui.home.HomePinsAdapter;
import com.glooory.petal.mvp.ui.pindetail.EditPinDialogFragment;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.glooory.petal.mvp.ui.user.board.CreateBoardDialogFragment;
import com.glooory.petal.mvp.ui.user.board.EditBoardDiglogFragment;

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalFragment;

/**
 * Created by Glooory on 17/3/24.
 */

public class UserPinFragment extends BasePetalFragment<UserSectionPresenter>
        implements UserContract.SectionView {

    private static final String ARGS_PIN_COUNT = "pin_count";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private HomePinsAdapter mAdapter;
    private int mPinCount;
    private String mUserId;
    private View mNoMoreDataFooter;

    public static UserPinFragment newInstance(String userId, int pinCount) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_USER_ID, userId);
        args.putInt(ARGS_PIN_COUNT, pinCount);
        UserPinFragment fragment = new UserPinFragment();
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
        mPinCount = getArguments().getInt(ARGS_PIN_COUNT);
        mAdapter = new HomePinsAdapter();
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
                    case R.id.ll_pin_img:
                        mPresenter.launchPinDetailActivity(getActivity(), view, position);
                        break;
                    case R.id.ll_pin_via_info:
                        mPresenter.launchUserActivityFromPin(getActivity(), view, position);
                        break;
                }
            }
        });
        if (mPresenter.isMe(mUserId)) {
            mRecyclerView.addOnItemTouchListener(new OnItemChildLongClickListener() {
                @Override
                public void onSimpleItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                    mPresenter.onPinLongClick(position);
                }
            });
        }
        if (mPinCount <= 0) {
            mAdapter.addFooterView(mNoMoreDataFooter);
        } else {
            mPresenter.getUserPins(mUserId);
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
        if (mAdapter.getData().size() >= mPinCount) {
            return;
        }

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.getUserPinsMore();
            }
        });
    }

    @Override
    public void showNoMoreDataFooter(boolean showAnyway) {
        if (showAnyway || mAdapter.getData().size() >= mPinCount) {
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
        ((UserActivity) getActivity()).onRefresh();
    }

    @Override
    public void showEditPinDialog(EditPinDialogFragment editPinDialogFragment) {
        editPinDialogFragment.show(getActivity().getSupportFragmentManager(), null);
    }

    @Override
    public void showDeletePinConfirmDialog(final String pinId, final int position) {
        DialogUtils.show(getActivity(), R.string.msg_delete_waring, R.string.msg_cancel,
                R.string.msg_confirm, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deletePin(pinId, position);
                    }
                });
    }

    @Override
    public void showDeletePinDataChange() {
        mPinCount--;
        ((UserActivity) getActivity()).setPinCountChanged(mPinCount);
    }

    @Override
    public void showFollowingDataChange(boolean isFollowed) {

    }

    @Override
    public void clearRecyclerViewPool() {
        mRecyclerView.getRecycledViewPool().clear();
    }

    public void onRefresh() {
        mPresenter.getUserPins(mUserId);
    }

    public void setPinCount(int pinCount) {
        mPinCount = pinCount;
    }
}
