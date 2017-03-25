package com.glooory.petal.mvp.presenter;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.util.DrawableUtils;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardSingleBean;
import com.glooory.petal.mvp.ui.home.HomePinsAdapter;
import com.glooory.petal.mvp.ui.pindetail.EditPinDialogFragment;
import com.glooory.petal.mvp.ui.pindetail.PinDetailActivity;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.glooory.petal.mvp.ui.user.board.CreateBoardDialogFragment;
import com.glooory.petal.mvp.ui.user.board.EditBoardDiglogFragment;
import com.glooory.petal.mvp.ui.user.board.UserBoardAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.RxUtils;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import common.PEAdapter;
import common.PEApplication;
import common.PEPresenter;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/3/21.
 */
@FragmentScope
public class UserSectionPresenter extends PEPresenter<UserContract.SectionView, UserContract.SectionModel> {

    private PEAdapter mAdapter;
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

    public void setAdapter(PEAdapter adapter) {
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

    public void getBoards(String userId) {
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
                        mRootView.showNoMoreDataFooter();
                    }
                });
    }

    public void getBoardsMore() {
        mModel.getBoardsMore(mUserId)
                .compose(RxUtils.<List<BoardBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<BoardBean>>() {
                    @Override
                    public void onNext(List<BoardBean> boardBeanList) {
                        mAdapter.addData(boardBeanList);
                        mRootView.showNoMoreDataFooter();
                        mAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    public void operateBtnClicked(int position) {
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

    private void actionFollowBoard(final int position) {
        final BoardBean boardBean = ((UserBoardAdapter) mAdapter).getItem(position);
        String boardId = String.valueOf(boardBean.getBoardId());
        boolean isFollowed = boardBean.isFollowing();
        mModel.followBoard(boardId, isFollowed)
                .compose(RxUtils.<FollowBoardResultBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<FollowBoardResultBean>() {
                    @Override
                    public void onNext(FollowBoardResultBean followBoardResultBean) {
                        boolean isFollowedTemp = !boardBean.isFollowing();
                        boardBean.setFollowing(isFollowedTemp);
                        mAdapter.notifyItemChanged(position);
                    }
                });
    }

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
                            mAdapter.notifyItemChanged(position);
                            mRootView.showMessage(PEApplication.getContext()
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
                                        mRootView.showMessage(PEApplication.getContext()
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
                            mRootView.showMessage(PEApplication.getContext()
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
                        mRootView.showNoMoreDataFooter();
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
                        mRootView.showNoMoreDataFooter();
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

    public void launchUserActivity(Activity activity, View view, int position) {
        PinBean pinBean = ((HomePinsAdapter) mAdapter).getItem(position);
        String userId = String.valueOf(pinBean.getUserId());
        UserActivity.launch(activity,
                userId,
                pinBean.getUser().getUsername(),
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin_avatar));
    }

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
                        Logger.d(pinBean.getRawText());
                        ((HomePinsAdapter) mAdapter).getItem(position)
                                .setRawText(pinBean.getRawText());
                        ((HomePinsAdapter) mAdapter).getItem(position).getBoard()
                                .setTitle(boardName);
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
                        mRootView.showDeletePinDataChange();
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
                        mRootView.showNoMoreDataFooter();
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
                        mRootView.showNoMoreDataFooter();
                        mAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.loadMoreFail();
                    }
                });
    }
}
