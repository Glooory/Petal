package com.glooory.petal.mvp.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.di.component.DaggerUserCompoment;
import com.glooory.petal.di.module.UserModule;
import com.glooory.petal.mvp.presenter.UserPresenter;
import com.orhanobut.logger.Logger;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import common.AppComponent;
import common.PEActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/3/21.
 */

public class UserActivity extends PEActivity<UserPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, UserContract.View{


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
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppbarLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindString(R.string.url_image_root)
    String mImgUrlRoot;
    @BindString(R.string.url_image_small_format)
    String mSmallImaUrlFormat;
    @BindArray(R.array.user_subitem_titles)
    String[] mTabTitles;

    private String mUserId;
    private String mUserName;
    private boolean mIsMe;

    public static void launch(Activity activity, String userId, String userName, SimpleDraweeView avatar) {
        Logger.d(userId);
        Intent intent = new Intent(activity, UserActivity.class);
        intent.putExtra(Constants.EXTRA_USER_ID, userId);
        intent.putExtra(Constants.EXTRA_USER_NAME, userName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            avatar.setTransitionName(activity.getResources().getString(R.string.image_transition_name));
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, avatar, activity.getResources().getString(R.string.image_transition_name)
            ).toBundle());
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
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(UserActivity.this, R.color.red_google_icon),
                ContextCompat.getColor(UserActivity.this, R.color.blue_google_icon),
                ContextCompat.getColor(UserActivity.this, R.color.yellow_google_icon),
                ContextCompat.getColor(UserActivity.this, R.color.green_google_icon)
        );
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        mUserId = getIntent().getStringExtra(Constants.EXTRA_USER_ID);
        mUserName = getIntent().getStringExtra(Constants.EXTRA_USER_NAME);
        mIsMe = (String.valueOf(SPUtils.get(Constants.PREF_USER_ID, 0)).equals(mUserId));
        Logger.d(mUserId);

        mPresenter.requestUserInfo(mUserId);
    }

    @Override
    public void onRefresh() {

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
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void showTabTitles(String[] titles) {
        mTabTitles = titles;
//        mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showUserName(String userName) {
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

    @Override
    public void showUserAvatar(String avatarKey) {
        mImgUserAvatar.setVisibility(View.VISIBLE);
        mPresenter.loadUserAvatar(avatarKey, mImgUserAvatar, new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                if (bitmap != null) {
                    final Drawable blurDrawable = new BitmapDrawable(getResources(),
                            FastBlurUtils.doBlur(bitmap, 15, false));
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
}
