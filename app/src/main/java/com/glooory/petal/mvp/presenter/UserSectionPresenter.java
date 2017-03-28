package com.glooory.petal.mvp.presenter;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.rx.RxBus;
import com.glooory.petal.app.util.DrawableUtils;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardSingleBean;
import com.glooory.petal.mvp.model.entity.user.UserSectionCountBean;
import com.glooory.petal.mvp.ui.board.BoardActivity;
import com.glooory.petal.mvp.ui.home.HomePinsAdapter;
import com.glooory.petal.mvp.ui.pindetail.EditPinDialogFragment;
import com.glooory.petal.mvp.ui.pindetail.PinDetailActivity;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.glooory.petal.mvp.ui.user.board.CreateBoardDialogFragment;
import com.glooory.petal.mvp.ui.user.board.EditBoardDiglogFragment;
import com.glooory.petal.mvp.ui.user.board.UserBoardAdapter;
import com.glooory.petal.mvp.ui.user.following.UserAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.RxUtils;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import common.BasePetalAdapter;
import common.BasePetalPresenter;
import common.PetalApplication;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/3/21.
 */
@FragmentScope
public class UserSectionPresenter extends BasePetalPresenter<UserContract.SectionView, UserContract.SectionModel> {

    private BasePetalAdapter mAdapter;
    private String mUserId;
    private boolean mIsMe;

    @Inject
    public UserSectionPresenter(UserContract.SectionView rootView, UserContract.SectionModel model) {
        super(rootView, model);
    }

    public boolean isMe(String userId) {
        mIsMe = mModel.isMe(userId);
        return mIsMe;
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

    /**
     * 获取用户所有的画板信息
     * @param userId
     */
    public void getUserBoards(String userId) {
        mUserId = userId;
        mModel.getBoards(mUserId)
                .compose(RxUtils.<List<BoardBean>>bindToLifecycle(mRootView))
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
                .subscribe(new BaseSubscriber<List<BoardBean>>() {
                    @Override
                    public void onNext(List<BoardBean> boardBeanList) {
                        mAdapter.setNewData(boardBeanList);
                        mRootView.showNoMoreDataFooter(false);
                    }
                });
    }

    public void getUserBoardsMore() {
        mModel.getBoardsMore(mUserId)
                .compose(RxUtils.<List<BoardBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<BoardBean>>() {
                    @Override
                    public void onNext(List<BoardBean> boardBeanList) {
                        mAdapter.addData(boardBeanList);
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

    public void launchBoardActivity(Activity activity, String userName, View view, int position) {
        final BoardBean boardBean = ((UserBoardAdapter) mAdapter).getItem(position);
        BoardActivity.launch(activity, userName, boardBean,
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_user_board_cover));
    }

    /**
     * 画板 item 上的底部操作按钮的点击事件
     * @param position
     */
    public void onBoardOperateBtnClick(int position) {
        if (!mModel.isLogin()) {
            mRootView.showLoginNav();
            return;
        }
        if (mIsMe) {
            showEditBoardDialog(position);
        } else {
            actionFollowBoard(position);
        }
    }

    private void showEditBoardDialog(final int position) {
        final BoardBean boardBean = ((UserBoardAdapter) mAdapter).getItem(position);
        EditBoardDiglogFragment fragment = EditBoardDiglogFragment
                .newInstance(String.valueOf(boardBean.getBoardId()),
                        boardBean.getTitle(), boardBean.getDescription(), boardBean.getCategoryId());
        fragment.setBoardEditListener(new EditBoardDiglogFragment.OnBoardEditListener() {
            @Override
            public void onBoardEdit(String boardId, String boardName, String boardDes, String category) {
                commitBoardEditInfo(boardId, boardName, boardDes, category, position);
            }

            @Override
            public void onBoardDelete(String boardId) {
                mRootView.showDeleteBoardConfirmDialog(boardId, position);
            }
        });
        mRootView.showEditBoardDialog(fragment);
    }

    /**
     * 关注或者取消关注画板
     * @param position
     */
    private void actionFollowBoard(final int position) {
        final BoardBean boardBean = ((UserBoardAdapter) mAdapter).getItem(position);
        String boardId = String.valueOf(boardBean.getBoardId());
        final boolean isFollowed = boardBean.isFollowing();
        mModel.followBoard(boardId, isFollowed)
                .compose(RxUtils.<FollowBoardResultBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<FollowBoardResultBean>() {
                    @Override
                    public void onNext(FollowBoardResultBean followBoardResultBean) {
                        boolean isFollowedTemp = !boardBean.isFollowing();
                        boardBean.setFollowing(isFollowedTemp);
                        int followerCount = boardBean.getFollowCount();
                        boardBean.setFollowCount(isFollowed ? --followerCount : ++followerCount);
                        mRootView.clearRecyclerViewPool();
                        mAdapter.notifyItemChanged(position);
                    }
                });
    }

    /**
     * 提交用户对画板的修改
     * @param boardId
     * @param boardName
     * @param boardDes
     * @param category
     * @param position
     */
    private void commitBoardEditInfo(String boardId, String boardName, String boardDes,
            String category, final int position) {
        mModel.editBoard(boardId, boardName, boardDes, category)
                .compose(RxUtils.<UserBoardSingleBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<UserBoardSingleBean>() {
                    @Override
                    public void onNext(UserBoardSingleBean userBoardSingleBean) {
                        if (userBoardSingleBean.getBoard() != null) {
                            ((UserBoardAdapter) mAdapter).getItem(position).
                                    setTitle(userBoardSingleBean.getBoard().getTitle());
                            ((UserBoardAdapter) mAdapter).getItem(position)
                                    .setDescription(userBoardSingleBean.getBoard().getDescription());
                            ((UserBoardAdapter) mAdapter).getItem(position)
                                    .setCategoryId(userBoardSingleBean.getBoard().getCategoryId());
                            Logger.d(userBoardSingleBean.getBoard().getCategoryId());
                            Logger.d(((UserBoardAdapter) mAdapter).getItem(position).getCategoryId());
                            mRootView.clearRecyclerViewPool();
                            mAdapter.notifyItemChanged(position);
                            mRootView.showMessage(PetalApplication.getContext()
                                    .getString(R.string.msg_edit_success));
                        }
                    }
                });
    }

    public void deleteBoard(String boardId, final int position) {
        mModel.deleteBoard(boardId)
                .compose(RxUtils.<UserBoardSingleBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<UserBoardSingleBean>() {
                    @Override
                    public void onNext(UserBoardSingleBean userBoardSingleBean) {
                        Subscription s = Observable.just(1)
                                .delay(500, TimeUnit.MILLISECONDS)
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<Integer>() {
                                    @Override
                                    public void call(Integer integer) {
                                        mRootView.showMessage(PetalApplication.getContext()
                                                .getString(R.string.msg_delete_board_success));
                                        mRootView.showLatestUserInfo();
                                    }
                                });
                        addSubscrebe(s);
                    }
                });
    }

    public void onFABClicked() {
        if (!mModel.isLogin()) {
            mRootView.showLoginNav();
            return;
        }

        CreateBoardDialogFragment createBoardDialogFragment = CreateBoardDialogFragment.newInstance();
        createBoardDialogFragment.setOnCreateBoardListener(new CreateBoardDialogFragment.OnCreateBoardListener() {
            @Override
            public void onBoardCreate(String boardName, String boardDes, String boardType) {
                createBoard(boardName, boardDes, boardType);
            }
        });
        mRootView.showCreateBoardDialog(createBoardDialogFragment);
    }

    private void createBoard(String boardName, String boardDes, String boardType) {
        mModel.createBoard(boardName, boardDes, boardType)
                .compose(RxUtils.<UserBoardSingleBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<UserBoardSingleBean>() {
                    @Override
                    public void onNext(UserBoardSingleBean userBoardSingleBean) {
                        if (userBoardSingleBean.getBoard() != null) {
                            mRootView.showMessage(PetalApplication.getContext()
                                    .getString(R.string.msg_create_board_success));
                            mRootView.showLatestUserInfo();
                        }
                    }
                });
    }

    public void getUserPins(String userId) {
        mUserId = userId;
        mModel.getUserPins(mUserId)
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinBeen) {
                        mAdapter.setNewData(pinBeen);
                        mRootView.showNoMoreDataFooter(false);
                    }
                });
    }

    public void getUserPinsMore() {
        mModel.getUserPinsMore(mUserId)
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
        PinBean pinBean = ((HomePinsAdapter) mAdapter).getItem(position);
        float aspectRatio = pinBean.getFile().getWidth() /
                (float) pinBean.getFile().getHeight();
        if (aspectRatio < 0.3) {
            aspectRatio = 0.3F;
        }
        PinDetailActivity.launch(activity,
                pinBean.getPinId(),
                aspectRatio,
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin),
                DrawableUtils.getBasicColorStr(((HomePinsAdapter) mAdapter).getItem(position)));
    }

    /**
     * Launch UserActivity, 仅当用户点击的是采集底部的用户头像时
     * @param activity
     * @param view
     * @param position
     */
    public void launchUserActivityFromPin(Activity activity, View view, int position) {
        PinBean pinBean = ((HomePinsAdapter) mAdapter).getItem(position);
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
        final PinBean pinBean = ((HomePinsAdapter) mAdapter).getItem(position);
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
                editPin(pinId, boardId, boardName, des, position);
            }
        });
        mRootView.showEditPinDialog(editPinDialogFragment);
    }

    public void editPin(final String pinId, String boardId, final String boardName,
            String des, final int position) {
        mModel.editPin(pinId, boardId, des)
                .compose(RxUtils.<PinBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<PinBean>() {
                    @Override
                    public void onNext(PinBean pinBean) {
                        ((HomePinsAdapter) mAdapter).getItem(position)
                                .setRawText(pinBean.getRawText());
                        ((HomePinsAdapter) mAdapter).getItem(position).getBoard()
                                .setTitle(boardName);
                        mRootView.clearRecyclerViewPool();
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
                        mRootView.clearRecyclerViewPool();
                        mAdapter.notifyDataSetChanged();
                        RxBus.getInstance().post(
                                new UserSectionCountBean(UserSectionCountBean.PIN_COUNT, false));
                        SnackbarUtil.showShort(R.string.msg_delete_success);
                    }
                });
    }

    public void getUserLikedPins(String userId) {
        mUserId = userId;
        mModel.getUserLikedPins(userId)
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinBeen) {
                        mAdapter.setNewData(pinBeen);
                        mRootView.showNoMoreDataFooter(false);
                    }
                });
    }

    public void getUserLikedPinsMore() {
        mModel.getUserLikedPinsMore(mUserId)
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

    public void getUserFollowing(String userId) {
        mUserId = userId;
        mModel.getUserFollowing(userId)
                .compose(RxUtils.<List<UserBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<UserBean>>() {
                    @Override
                    public void onNext(List<UserBean> userBeen) {
                        mAdapter.setNewData(userBeen);
                        mRootView.showNoMoreDataFooter(false);
                    }
                });
    }

    public void getUserFollowingMore() {
        mModel.getUserFollowingMore(mUserId)
                .compose(RxUtils.<List<UserBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<UserBean>>() {
                    @Override
                    public void onNext(List<UserBean> userBeen) {
                        mAdapter.addData(userBeen);
                        mRootView.showNoMoreDataFooter(false);
                        mAdapter.loadMoreComplete();
                        if (userBeen.size() == 0) {
                            mAdapter.loadMoreEnd();
                            mRootView.showNoMoreDataFooter(true);
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
                        mRootView.clearRecyclerViewPool();
                        mAdapter.notifyItemChanged(postion);
                        if (isMe(mUserId)) {
                            RxBus.getInstance().post(
                                    new UserSectionCountBean(UserSectionCountBean.FOLLOWING_COUNT, !isFollowed)
                            );
                        }
                    }
                });
    }

    public void getUserFollowers(String userId) {
        mUserId = userId;
        mModel.getUserFollowers(userId)
                .compose(RxUtils.<List<UserBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<UserBean>>() {
                    @Override
                    public void onNext(List<UserBean> userBeen) {
                        mAdapter.setNewData(userBeen);
                        mRootView.showNoMoreDataFooter(false);
                    }
                });
    }

    public void getUserFollowersMore() {
        mModel.getUserFollowersMore(mUserId)
                .compose(RxUtils.<List<UserBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<UserBean>>() {
                    @Override
                    public void onNext(List<UserBean> userBeen) {
                        mAdapter.addData(userBeen);
                        mRootView.showNoMoreDataFooter(false);
                        mAdapter.loadMoreComplete();
                        if (userBeen.size() == 0) {
                            mAdapter.loadMoreEnd();
                            mRootView.showNoMoreDataFooter(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.loadMoreFail();
                    }
                });
    }
}
