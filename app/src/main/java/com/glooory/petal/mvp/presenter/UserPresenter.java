package com.glooory.petal.mvp.presenter;

import android.content.res.Resources;
import android.text.TextUtils;

import com.glooory.petal.R;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.RxUtils;

import javax.inject.Inject;

import common.PEApplication;
import common.PEPresenter;

/**
 * Created by Glooory on 17/3/22.
 */
@ActivityScope
public class UserPresenter extends PEPresenter<UserContract.View, UserContract.Model> {

    private String mUserId;
    private int mBoardCount;
    private int mCollectCount;
    private int mLikeCount;
    private int mFollowingCount;
    private int mFollowerCount;

    @Inject
    public UserPresenter(UserContract.View rootView, UserContract.Model model) {
        super(rootView, model);
    }

    public void requestUserInfo(String userId) {
        mUserId = userId;
        mModel.getUser(mUserId)
                .compose(RxUtils.<UserBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        updateUserInfo(userBean);
                    }
                });
    }

    private void updateUserInfo(UserBean userBean) {
        mBoardCount = userBean.getBoardCount();
        mCollectCount = userBean.getPinCount();
        mLikeCount = userBean.getLikeCount();
        mFollowingCount = userBean.getFollowingCount();
        mFollowerCount = userBean.getFollowerCount();
        Resources resources = PEApplication.getContext().getResources();
        String tabTitle1 = String.format(resources.getString(R.string.format_board_count), mBoardCount);
        String tabTitle2 = String.format(resources.getString(R.string.format_collection_count), mCollectCount);
        String tabTitle3 = String.format(resources.getString(R.string.format_like_count), mLikeCount);
        String tabTitle4 = String.format(resources.getString(R.string.format_following_count), mFollowingCount);
        String tabTitle5 = String.format(resources.getString(R.string.format_follower_count), mFollowerCount);
        String[] titles = new String[]{tabTitle1, tabTitle2, tabTitle3, tabTitle4, tabTitle5};
        mRootView.showTabTitles(titles);

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
            mRootView.showUserAbout(PEApplication.getContext().getString(R.string.msg_empty_user_about));
        }

        mRootView.showUserAvatar(userBean.getAvatar().getKey());
    }
}
