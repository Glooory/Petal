package com.glooory.petal.mvp.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.FastBlurUtils;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.di.component.DaggerUserCompoment;
import com.glooory.petal.di.module.UserModule;
import com.glooory.petal.mvp.presenter.UserPresenter;
import com.glooory.petal.mvp.ui.login.LoginActivity;
import com.glooory.petal.mvp.ui.user.board.UserBoardFragment;
import com.glooory.petal.mvp.ui.user.follower.UserFollowerFragment;
import com.glooory.petal.mvp.ui.user.following.UserFollowingFragment;
import com.glooory.petal.mvp.ui.user.like.UserLikedFragment;
import com.glooory.petal.mvp.ui.user.pin.UserPinFragment;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppComponent;
import common.BasePetalActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/3/21.
 */

public class UserActivity extends BasePetalActivity<UserPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, UserContract.View,
        AppBarLayout.OnOffsetChangedListener {

    private static final String EXTRA_AVATAR_KEY = "avatar_key";

    @BindView(R.id.simple_drawee_user_avatar)
    SimpleDraweeView mImgUserAvatar;
    @BindView(R.id.text_view_user_name)
    TextView mTextViewUserName;
    @BindView(R.id.text_view_user_lacation_job)
    TextView mTextViewUserLacation;
    @BindView(R.id.text_view_user_des)
    TextView mTextViewUserDes;
    @BindView(R.id.text_view_user_follow_edit)
    TextView mTextViewFollowEdit;
    @BindView(R.id.progressbar_user_following)
    ProgressBar mProgressbarFollowing;
    @BindView(R.id.ll_user_info)
    LinearLayout mLlUserInfo;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppbarLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindArray(R.array.user_subitem_titles)
    String[] mTabTitles;
    @BindColor(R.color.colorPrimary)
    int mColorTabIndicator;

    private String mUserId;
    private String mUserName;
    private boolean mIsMe;
    private UserSectionPagerAdapter mPagerAdapter;

    private UserBoardFragment mBoardFragment;
    private UserPinFragment mPinFragment;
    private UserLikedFragment mLikedFragment;
    private UserFollowingFragment mFollowingFragment;
    private UserFollowerFragment mFollowerFragment;

    public static void launch(Activity activity, String userId, String userName,
            SimpleDraweeView avatar, String avatarKey) {
        Intent intent = new Intent(activity, UserActivity.class);
        intent.putExtra(Constants.EXTRA_USER_ID, userId);
        intent.putExtra(Constants.EXTRA_USER_NAME, userName);
        intent.putExtra(EXTRA_AVATAR_KEY, avatarKey);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            avatar.setTransitionName(userId);
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, avatar, userId).toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserCompoment.builder()
                .appComponent(appComponent)
                .userModule(new UserModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView() {
        mUserId = getIntent().getStringExtra(Constants.EXTRA_USER_ID);
        mUserName = getIntent().getStringExtra(Constants.EXTRA_USER_NAME);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImgUserAvatar.setTransitionName(mUserId);
        }
        mIsMe = mPresenter.isMe(mUserId);
        String avatarKey = getIntent().getStringExtra(EXTRA_AVATAR_KEY);
        showUserAvatar(avatarKey);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(UserActivity.this, R.color.red_google_icon),
                ContextCompat.getColor(UserActivity.this, R.color.blue_google_icon),
                ContextCompat.getColor(UserActivity.this, R.color.yellow_google_icon),
                ContextCompat.getColor(UserActivity.this, R.color.green_google_icon)
        );
        mSwipeRefreshLayout.setOnRefreshListener(this);

        final FloatingActionButton floatingActionButton = ButterKnife.findById(this, R.id.fab);
        mTablayout.setSelectedTabIndicatorColor(mColorTabIndicator);
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
                if (tab.getPosition() == 0 && mIsMe) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                } else {
                    floatingActionButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mAppbarLayout.addOnOffsetChangedListener(this);

        RxView.clicks(mTextViewFollowEdit)
                .throttleFirst(Constants.THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .delay(200, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.toolbarActinBtnClicked();
                    }
                });
    }

    private void initViewPager() {
        if (mPagerAdapter == null) {
            mPagerAdapter = new UserSectionPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.setCurrentItem(0, true);
            mTablayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    protected void initData() {
        mPresenter.requestUserInfo(mUserId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter.isLogin()) {
            mPresenter.registerUserSectionCountEvent();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter.isLogin()) {
            mPresenter.unregisterUserSectionCountEvent();
        }
    }

    @Override
    protected void onDestroy() {
        mPagerAdapter = null;
        mBoardFragment = null;
        mPinFragment = null;
        mLikedFragment = null;
        mFollowingFragment = null;
        mFollowerFragment = null;
        mImgUserAvatar.setController(null);
        mAppbarLayout.setBackground(null);
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        mPresenter.requestUserInfo(mUserId);
        switch (mViewPager.getCurrentItem()) {
            case 0:
                if (mBoardFragment != null) {
                    mBoardFragment.onRefresh();
                }
                break;
            case 1:
                if (mPinFragment != null) {
                    mPinFragment.onRefresh();
                }
                break;
            case 2:
                if (mLikedFragment != null) {
                    mLikedFragment.onRefresh();
                }
                break;
            case 3:
                if (mFollowingFragment != null) {
                    mFollowingFragment.onRefresh();
                }
                break;
            case 4:
                if (mFollowerFragment != null) {
                    mFollowerFragment.onRefresh();
                }
                break;
        }
    }

    public void showLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });
    }

    public void hideLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLoginHint() {
        SnackbarUtil.showLong(this, R.string.msg_login_hint, R.string.msg_go_login,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginActivity.launch(UserActivity.this, false);
                    }
                });
    }

    @Override
    public void showViewPager() {
        initViewPager();
    }

    @Override
    public void showToolbarAction(int actionResId, int actionDrawableResId) {
        mTextViewFollowEdit.setVisibility(View.VISIBLE);
        mProgressbarFollowing.setVisibility(View.GONE);
        mTextViewFollowEdit.setText(actionResId);
        mTextViewFollowEdit.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(this, actionDrawableResId), null, null, null);
    }

    @Override
    public void showProcessingbar() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mTextViewFollowEdit.setVisibility(View.INVISIBLE);
                        mProgressbarFollowing.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void hideProcessingbar() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mTextViewFollowEdit.setVisibility(View.VISIBLE);
                        mProgressbarFollowing.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void showTabTitles(String[] titles) {
        mTabTitles = titles;
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showUserData(int boardCount, int pinCount, int likeCount, int followingCount,
            int followerCount) {
        if (mBoardFragment != null) {
            mBoardFragment.setBoardCount(boardCount);
        }
        if (mPinFragment != null) {
            mPinFragment.setPinCount(pinCount);
        }
        if (mLikedFragment != null) {
            mLikedFragment.setLikedCount(likeCount);
        }
        if (mFollowingFragment != null) {
            mFollowingFragment.setFollowingCount(followingCount);
        }
        if (mFollowerFragment != null) {
            mFollowerFragment.setFollowerCount(followerCount);
        }
    }

    @Override
    public void showUserName(String userName) {
        mUserName = userName;
        mTextViewUserName.setText(userName);
    }

    @Override
    public void showUserLocation(String location) {
        mTextViewUserLacation.setText(location);
    }

    @Override
    public void showUserAbout(String userAbout) {
        mTextViewUserDes.setText(userAbout);
    }

    public void showUserAvatar(String avatarKey) {
        mPresenter.loadUserAvatar(avatarKey, mImgUserAvatar, new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                if (bitmap != null) {
                    final Drawable blurDrawable = new BitmapDrawable(getResources(),
                            FastBlurUtils.doBlur(bitmap, 10, false));
                    Observable.just(1)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Integer>() {
                                @Override
                                public void call(Integer integer) {
                                    mAppbarLayout.setBackground(blurDrawable);
                                }
                            });

                }
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float off = -verticalOffset;
        float alpha = 1.0f - off / (appBarLayout.getTotalScrollRange() / 2.0f);
        mLlUserInfo.setAlpha(alpha);
        if (alpha < 0.3) {
            mCollapsingToolbar.setTitle(mUserName);
        } else {
            mCollapsingToolbar.setTitle("");
        }
        if (off >= appBarLayout.getTotalScrollRange()) {
            mCollapsingToolbar.setAlpha(0);
        } else {
            mCollapsingToolbar.setAlpha(1);
        }
    }

    class UserSectionPagerAdapter extends FragmentStatePagerAdapter {

        public UserSectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return UserBoardFragment.newInstance(mUserId, mUserName, mPresenter.getBoardCount());
                case 1:
                    return UserPinFragment.newInstance(mUserId, mPresenter.getCollectCount());
                case 2:
                    return UserLikedFragment.newInstance(mUserId, mPresenter.getLikeCount());
                case 3:
                    return UserFollowingFragment.newInstance(mUserId, mPresenter.getFollowingCount());
                case 4:
                    return UserFollowerFragment.newInstance(mUserId, mPresenter.getFollowerCount());
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            switch (position) {
                case 0:
                    mBoardFragment = (UserBoardFragment) createdFragment;
                    break;
                case 1:
                    mPinFragment = (UserPinFragment) createdFragment;
                    break;
                case 2:
                    mLikedFragment = (UserLikedFragment) createdFragment;
                    break;
                case 3:
                    mFollowingFragment = (UserFollowingFragment) createdFragment;
                    break;
                case 4:
                    mFollowerFragment = (UserFollowerFragment) createdFragment;
                    break;
            }
            return createdFragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }
}
