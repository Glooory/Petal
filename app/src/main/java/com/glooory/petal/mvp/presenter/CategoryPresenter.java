package com.glooory.petal.mvp.presenter;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.type.PusersBean;
import com.glooory.petal.mvp.ui.board.BoardActivity;
import com.glooory.petal.mvp.ui.category.CategoryContract;
import com.glooory.petal.mvp.ui.category.board.CategoryBoardAdapter;
import com.glooory.petal.mvp.ui.category.user.CategoryUserAdapter;
import com.glooory.petal.mvp.ui.home.HomePinAdapter;
import com.glooory.petal.mvp.ui.pindetail.PinDetailActivity;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import common.BasePetalAdapter;
import common.BasePetalPresenter;
import rx.functions.Action0;

/**
 * Created by Glooory on 17/4/1.
 */
@FragmentScope
public class CategoryPresenter extends BasePetalPresenter<CategoryContract.View, CategoryContract.Model> {

    private BasePetalAdapter mAdapter;
    private String mCategory;

    @Inject
    public CategoryPresenter(CategoryContract.View rootView, CategoryContract.Model model) {
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

    public void getCategoryPins(String category) {
        mCategory = category;
        mModel.getCategoryPins(mCategory)
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
                        if (pinBeen.size() == 0) {
                            mRootView.showNoMoreDataFooter();
                            return;
                        }
                        mAdapter.setNewData(pinBeen);
                    }
                });
    }

    public void getCategoryPinsMore() {
        mModel.getCategoryPinsMore(mCategory)
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinBeen) {
                        if (pinBeen.size() == 0) {
                            mRootView.showNoMoreDataFooter();
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        mAdapter.addData(pinBeen);
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
        PinBean pinBean = ((HomePinAdapter) mAdapter).getItem(position);
        float aspectRatio = pinBean.getFile().getWidth() /
                (float) pinBean.getFile().getHeight();
        if (aspectRatio < 0.3) {
            aspectRatio = 0.3F;
        }
        PinDetailActivity.launch(activity,
                pinBean.getPinId(),
                aspectRatio,
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin),
                pinBean.getFile().getKey());
    }

    /**
     * Launch UserActivity, 仅当用户点击的是采集底部的用户头像时
     * @param activity
     * @param view
     * @param position
     */
    public void launchUserActivityFromPin(Activity activity, View view, int position) {
        PinBean pinBean = ((HomePinAdapter) mAdapter).getItem(position);
        String userId = String.valueOf(pinBean.getUserId());
        UserActivity.launch(activity,
                userId,
                pinBean.getUser().getUsername(),
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin_avatar));
    }

    public void getCategoryBoards(String category) {
        mCategory = category;
        mModel.getCategoryBoards(mCategory)
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();
                    }
                })
                .subscribe(new BaseSubscriber<List<BoardBean>>() {
                    @Override
                    public void onNext(List<BoardBean> boardBeanList) {
                        if (boardBeanList.size() == 0) {
                            mRootView.showNoMoreDataFooter();
                            return;
                        }
                        mAdapter.setNewData(boardBeanList);
                    }
                });
    }

    public void getCategoryBoardsMore() {
        mModel.getCategoryBoardsMore(mCategory)
                .subscribe(new BaseSubscriber<List<BoardBean>>() {
                    @Override
                    public void onNext(List<BoardBean> boardBeanList) {
                        if (boardBeanList.size() == 0) {
                            mAdapter.loadMoreEnd();
                            mRootView.showNoMoreDataFooter();
                            return;
                        }
                        mAdapter.addData(boardBeanList);
                        mAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    public void launchBoardActivity(Activity activity, int position) {
        final BoardBean boardBean = ((CategoryBoardAdapter) mAdapter).getItem(position);
        boardBean.setDeleting(1);
        BoardActivity.launch(activity, boardBean.getUser().getUsername(), boardBean);
    }

    public void launchUserActivityFromBoard(Activity activity, View view, int position) {
        BoardBean boardBean = ((CategoryBoardAdapter) mAdapter).getItem(position);
        String userId = String.valueOf(boardBean.getUserId());
        String userName = boardBean.getUser().getUsername();
        UserActivity.launch(activity, userId, userName,
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_category_board_user_avatar));
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
        actionFollowBoard(position);
    }

    /**
     * 关注或者取消关注画板
     * @param position
     */
    private void actionFollowBoard(final int position) {
        final BoardBean boardBean = ((CategoryBoardAdapter) mAdapter).getItem(position);
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
                        mAdapter.notifyItemChanged(position);
                    }
                });
    }

    public void getCategoryUsers(String category) {
        mCategory = category;
        mModel.getCategoryUsers(mCategory)
                .compose(RxUtils.<List<PusersBean>>bindToLifecycle(mRootView))
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
                .subscribe(new BaseSubscriber<List<PusersBean>>() {
                    @Override
                    public void onNext(List<PusersBean> pusersBeen) {
                        if (pusersBeen.size() == 0) {
                            mRootView.showNoMoreDataFooter();
                            return;
                        }
                        mAdapter.setNewData(pusersBeen);
                    }
                });
    }

    public void getCategoryUsersMore() {
        mModel.getCategoryUsersMore(mCategory)
                .compose(RxUtils.<List<PusersBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<PusersBean>>() {
                    @Override
                    public void onNext(List<PusersBean> pusersBeen) {
                        if (pusersBeen.size() == 0) {
                            mAdapter.loadMoreEnd();
                            mRootView.showNoMoreDataFooter();
                            return;
                        }
                        mAdapter.addData(pusersBeen);
                        mAdapter.loadMoreComplete();
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
        UserBean userBean = ((CategoryUserAdapter) mAdapter).getItem(position).getUser();
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
        UserBean userBean = ((CategoryUserAdapter) mAdapter).getItem(postion).getUser();
        String userId = String.valueOf(userBean.getUserId());
        final boolean isFollowed = userBean.getFollowing() == 1;
        mModel.followUser(userId, isFollowed)
                .compose(RxUtils.<Void>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<Void>() {
                    @Override
                    public void onNext(Void aVoid) {
                        ((CategoryUserAdapter) mAdapter).getItem(postion).getUser()
                                .setFollowing(isFollowed ? 0 : 1);
                        mAdapter.notifyItemChanged(postion);
                    }
                });
    }
}
