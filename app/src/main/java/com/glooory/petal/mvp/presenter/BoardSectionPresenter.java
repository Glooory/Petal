package com.glooory.petal.mvp.presenter;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.util.DrawableUtils;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.ui.board.BoardContract;
import com.glooory.petal.mvp.ui.board.pin.BoardPinAdapter;
import com.glooory.petal.mvp.ui.pindetail.EditPinDialogFragment;
import com.glooory.petal.mvp.ui.pindetail.PinDetailActivity;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.glooory.petal.mvp.ui.user.following.UserAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import common.BasePetalAdapter;
import common.BasePetalPresenter;
import rx.functions.Action0;

/**
 * Created by Glooory on 17/3/28.
 */
@FragmentScope
public class BoardSectionPresenter extends BasePetalPresenter<BoardContract.SectionView, BoardContract.SectionModel> {

    private BasePetalAdapter mAdapter;
    private boolean mIsMine;
    private String mBoardId;

    @Inject
    public BoardSectionPresenter(BoardContract.SectionView rootView, BoardContract.SectionModel model) {
        super(rootView, model);
    }

    public void setAdapter(BasePetalAdapter adapter) {
        mAdapter = adapter;
        initAdapter();
    }

    private void initAdapter() {
        mAdapter.setLoadMoreView(new WindmillLoadMoreFooter());
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRootView.showLoadingMore();
            }
        });
    }

    public boolean isMine(String userId) {
        mIsMine = mModel.isMine(userId);
        return mIsMine;
    }

    public void getBoardPins(String boardId) {
        mBoardId = boardId;
        mModel.getBoardPins(mBoardId)
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();
                    }
                })
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinBeen) {
                        mAdapter.setNewData(pinBeen);
                        mRootView.showNoMoreDataFooter(false);
                    }
                });
    }

    public void getBoardPinsMore() {
        mModel.getBoardPinsMore(mBoardId)
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinBeen) {
                        mAdapter.addData(pinBeen);
                        mRootView.showNoMoreDataFooter(false);
                        mAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    public void launchPinDetailActivity(Activity activity, View view, int position) {
        PinBean pinBean = ((BoardPinAdapter) mAdapter).getItem(position);
        float aspectRatio = pinBean.getFile().getWidth() /
                (float) pinBean.getFile().getHeight();
        if (aspectRatio < 0.3) {
            aspectRatio = 0.3F;
        }
        PinDetailActivity.launch(activity,
                pinBean.getPinId(),
                aspectRatio,
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin),
                DrawableUtils.getBasicColorStr(((BoardPinAdapter) mAdapter).getItem(position)));
    }

    /**
     * Launch UserActivity, 仅当用户点击的是采集底部的用户头像时
     * @param activity
     * @param view
     * @param position
     */
    public void launchUserActivityFromPin(Activity activity, View view, int position) {
        PinBean pinBean = ((BoardPinAdapter) mAdapter).getItem(position);
        String userId = String.valueOf(pinBean.getUserId());
        UserActivity.launch(activity,
                userId,
                pinBean.getUser().getUsername(),
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin_avatar));
    }

    /**
     * 采集（图片）的长按响应事件
     * @param position
     */
    public void onPinLongClick(final int position) {
        final PinBean pinBean = ((BoardPinAdapter) mAdapter).getItem(position);
        final String pinId = String.valueOf(pinBean.getPinId());
        EditPinDialogFragment editPinDialogFragment = EditPinDialogFragment.newInstance(
                pinId,
                String.valueOf(pinBean.getBoardId()),
                pinBean.getRawText());
        editPinDialogFragment.setPinEditListener(new EditPinDialogFragment.OnPinEditListener() {
            @Override
            public void onPinDelete() {
                mRootView.showDeletePinConfirmDialog(pinId , position);
            }

            @Override
            public void onPinEdit(String boardId, String boardName, String des) {
                editPin(pinId, boardId, des, position);
            }
        });
        mRootView.showEditPinDialog(editPinDialogFragment);
    }

    public void editPin(final String pinId, String boardId, String des, final int position) {
        mModel.editPin(pinId, boardId, des)
                .compose(RxUtils.<PinBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<PinBean>() {
                    @Override
                    public void onNext(PinBean pinBean) {
                        ((BoardPinAdapter) mAdapter).getItem(position)
                                .setRawText(pinBean.getRawText());
                        mAdapter.notifyItemChanged(position);
                    }
                });
    }

    public void deletePin(String pinId, final int position) {
        mModel.deletePin(pinId)
                .compose(RxUtils.<Void>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<Void>() {
                    @Override
                    public void onNext(Void aVoid) {
                        mAdapter.remove(position);
                        mAdapter.notifyDataSetChanged();
                        mRootView.showPinDeleted();
                        SnackbarUtil.showShort(R.string.msg_delete_success);
                    }
                });
    }

    public void getBoardFollowers(String boardId) {
        mBoardId = boardId;
        mModel.getBoardFollowers(boardId)
                .compose(RxUtils.<List<UserBean>>bindToLifecycle(mRootView))
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();
                    }
                })
                .subscribe(new BaseSubscriber<List<UserBean>>() {
                    @Override
                    public void onNext(List<UserBean> followerBeen) {
                        mAdapter.setNewData(followerBeen);
                        mRootView.showNoMoreDataFooter(false);
                        if (followerBeen.size() == 0) {
                            mAdapter.loadMoreEnd();
                        }
                    }
                });
    }

    public void getBoardFollowersMore() {
        mModel.getBoardFollowersMore(mBoardId)
                .compose(RxUtils.<List<UserBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<UserBean>>() {
                    @Override
                    public void onNext(List<UserBean> followerBeen) {
                        mAdapter.addData(followerBeen);
                        mRootView.showNoMoreDataFooter(false);
                        mAdapter.loadMoreComplete();
                        if (followerBeen.size() == 0) {
                            mAdapter.loadMoreEnd();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    /**
     * Launch UserActivity, 仅当用户点击的是 User item 时
     * @param activity
     * @param view
     * @param position
     */
    public void launchUserActivityFromUser(Activity activity, View view, int position) {
        UserBean userBean = ((UserAdapter) mAdapter).getItem(position);
        String userId = String.valueOf(userBean.getUserId());
        UserActivity.launch(activity, userId, userBean.getUsername(),
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_card_user_avatar));
    }

    /**
     * User item 底部的操作按钮点击事件
     * @param position
     */
    public void onUserCardOperateBtnClick(int position) {
        if (!mModel.isLogin()) {
            mRootView.showLoginNav();
            return;
        }
        actionFollowUser(position);
    }

    private void actionFollowUser(final int postion) {
        UserBean userBean = ((UserAdapter) mAdapter).getItem(postion);
        String userId = String.valueOf(userBean.getUserId());
        final boolean isFollowed = userBean.getFollowing() == 1;
        mModel.followUser(userId, isFollowed)
                .compose(RxUtils.<Void>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<Void>() {
                    @Override
                    public void onNext(Void aVoid) {
                        ((UserAdapter) mAdapter).getItem(postion)
                                .setFollowing(isFollowed ? 0 : 1);
                        mAdapter.notifyItemChanged(postion);
                    }
                });
    }
}
