package com.glooory.petal.mvp.presenter;

import android.content.res.Resources;
import android.text.TextUtils;

import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.rx.RxBus;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.user.UserSectionCountBean;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.RxUtils;

import javax.inject.Inject;

import common.BasePetalPresenter;
import common.PetalApplication;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/22.
 */
@ActivityScope
public class UserPresenter extends BasePetalPresenter<UserContract.View, UserContract.Model> {

    private String mUserId;
    private boolean mIsMe;
    private boolean mIsFollowed;
    private int mBoardCount;
    private int mCollectCount;
    private int mLikeCount;
    private int mFollowingCount;
    private int mFollowerCount;

    @Inject
    public UserPresenter(UserContract.View rootView, UserContract.Model model) {
        super(rootView, model);
    }

    public boolean isMe(String userId) {
        mIsMe = mModel.isMe(userId);
        return mIsMe;
    }

    public boolean isLogin() {
        return mModel.isLogin();
    }

    public void requestUserInfo(String userId) {
        mUserId = userId;
        mModel.getUser(mUserId)
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
                .compose(RxUtils.<UserBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        updateUserInfo(userBean);
                    }
                });
    }

    private void updateUserInfo(UserBean userBean) {
        mIsFollowed = userBean.getFollowing() == 1;
        mBoardCount = userBean.getBoardCount();
        mCollectCount = userBean.getPinCount();
        mLikeCount = userBean.getLikeCount();
        mFollowingCount = userBean.getFollowingCount();
        mFollowerCount = userBean.getFollowerCount();

        mRootView.showViewPager();
        setupToolbarAction();
        setupTabTitles();

        if (!TextUtils.isEmpty(userBean.getUsername())) {
            mRootView.showUserName(userBean.getUsername());
        }

        if (userBean.getProfile() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            if (!TextUtils.isEmpty(userBean.getProfile().getLocation())) {
                stringBuilder.append(userBean.getProfile().getLocation())
                        .append("  ");
            }
            if (!TextUtils.isEmpty(userBean.getProfile().getJob())) {
                stringBuilder.append(userBean.getProfile().getJob());
            }
            mRootView.showUserLocation(stringBuilder.toString());
        }
        if (userBean.getProfile() != null && !TextUtils.isEmpty(userBean.getProfile().getAbout())) {
            mRootView.showUserAbout(userBean.getProfile().getAbout());
        } else {
            mRootView.showUserAbout(PetalApplication.getContext().getString(R.string.msg_empty_user_about));
        }

        SPUtils.putByCommit(Constants.PREF_BOARD_COUNT, mBoardCount);
    }

    private void setupTabTitles() {
        Resources resources = PetalApplication.getContext().getResources();
        String tabTitle1 = String.format(resources.getString(R.string.format_board_count), mBoardCount);
        String tabTitle2 = String.format(resources.getString(R.string.format_collection_count), String.valueOf(mCollectCount));
        String tabTitle3 = String.format(resources.getString(R.string.format_like_count), mLikeCount);
        String tabTitle4 = String.format(resources.getString(R.string.format_following_count), mFollowingCount);
        String tabTitle5 = String.format(resources.getString(R.string.format_follower_count), mFollowerCount);
        String[] titles = new String[]{tabTitle1, tabTitle2, tabTitle3, tabTitle4, tabTitle5};
        mRootView.showUserData(mBoardCount, mCollectCount, mLikeCount, mFollowingCount, mFollowerCount);
        mRootView.showTabTitles(titles);
    }

    private void setupToolbarAction() {
        if (mIsMe) {
            mRootView.showToolbarAction(R.string.edit, R.drawable.ic_edit_white_24dp);
        } else {
            if (mIsFollowed) {
                mRootView.showToolbarAction(R.string.followed, R.drawable.ic_check_white_24dp);
            } else {
                mRootView.showToolbarAction(R.string.nav_title_following, R.drawable.ic_add_white_24dp);
            }
        }
    }


    public void onToolbarActinBtnClicked() {
        if (!mModel.isLogin()) {
            mRootView.showLoginHint();
            return;
        }
        if (mIsMe) {
            // TODO: 17/3/23 Launch EditUserActivity
        } else {
            mRootView.showProcessingbar();
            actionFollowUser();
        }
    }

    private void actionFollowUser() {
        mModel.followUser(mUserId, mIsFollowed)
                .compose(RxUtils.<Void>bindToLifecycle(mRootView))
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideProcessingbar();
                    }
                })
                .subscribe(new BaseSubscriber<Void>() {
                    @Override
                    public void onNext(Void aVoid) {
                        updateUserInfoBackground();
                    }
                });
    }

    private void updateUserInfoBackground() {
        mModel.getUser(mUserId)
                .compose(RxUtils.<UserBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        updateUserInfo(userBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    /**
     * 注册用户的画板、采集、粉丝及关注等信息改变事件
     */
    public void registerUserSectionCountEvent() {
        Subscription s = RxBus.getInstance()
                .toObservable(UserSectionCountBean.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserSectionCountBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserSectionCountBean userSectionCountBean) {
                        updateUserSectionCounts(userSectionCountBean);
                    }
                });
        addSubscrebe(s);
    }

    private void updateUserSectionCounts(UserSectionCountBean userSectionCountBean) {
        switch (userSectionCountBean.getCountTypeIndex()) {
            case UserSectionCountBean.BOARD_COUNT:
                mBoardCount = userSectionCountBean.isIncreasing() ? ++mBoardCount : --mBoardCount;
                break;
            case UserSectionCountBean.PIN_COUNT:
                mCollectCount = userSectionCountBean.isIncreasing() ? ++mCollectCount : --mCollectCount;
                break;
            case UserSectionCountBean.FOLLOWING_COUNT:
                mFollowingCount = userSectionCountBean.isIncreasing() ? ++mFollowingCount : --mFollowingCount;
                break;
            case UserSectionCountBean.FOLLOWER_COUNT:
                mFollowerCount = userSectionCountBean.isIncreasing() ? ++mFollowerCount : --mFollowerCount;
                break;
        }
        setupTabTitles();
    }

    public void unregisterUserSectionCountEvent() {
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public int getBoardCount() {
        return mBoardCount;
    }

    public int getCollectCount() {
        return mCollectCount;
    }

    public int getLikeCount() {
        return mLikeCount;
    }

    public int getFollowingCount() {
        return mFollowingCount;
    }

    public int getFollowerCount() {
        return mFollowerCount;
    }
}
