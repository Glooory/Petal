package com.glooory.petal.mvp.ui.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.rx.RxBus;
import com.glooory.petal.app.util.DialogUtils;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.mvp.model.entity.BasicUserInfoBean;
import com.glooory.petal.mvp.ui.collect.CollectActivity;
import com.glooory.petal.mvp.ui.login.LoginActivity;
import com.glooory.petal.mvp.ui.search.SearchActivity;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.jakewharton.rxbinding.view.RxView;
import com.jess.arms.widget.imageloader.fresco.FrescoImageConfig;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppComponent;
import common.BasePetalActivity;
import common.PetalApplication;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Glooory on 17/2/18.
 */

public class HomeActivity extends BasePetalActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    private static final String EXTRA_NOTICE = "notice";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBar;
    @BindView(R.id.navigation)
    NavigationView mNavigationView;

    //侧滑菜单栏头像
    private SimpleDraweeView mAvatarImg;
    //策划菜单栏用户名
    private TextView mTvUserName;
    //侧滑菜单栏采集数
    private TextView mTvCollectionCount;
    //侧滑菜单栏画板数
    private TextView mTvBoardCount;
    //侧滑菜单栏关注数
    private TextView mTvFollowingCount;
    //侧滑菜单栏粉丝数
    private TextView mTvFollowerCount;
    private CompositeSubscription mCompositeSubscription;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity, String notice) {
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.putExtra(EXTRA_NOTICE, notice);
        activity.startActivity(intent);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        if (isLogin()) {
            getSupportActionBar().setTitle(R.string.nav_title_following);
        } else {
            getSupportActionBar().setTitle(R.string.nav_title_latest);
        }

        RxView.clicks(mFab)
                .throttleFirst(Constants.THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        CollectActivity.launch(HomeActivity.this);
                    }
                });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        initNavigationView(mNavigationView);
    }

    private void initNavigationView(NavigationView navigationView) {
        View navHeaderView = navigationView.inflateHeaderView(R.layout.view_header_nav_home);
        LinearLayout avatarLl = ButterKnife.findById(navHeaderView, R.id.ll_drawer_avatar);
        avatarLl.setOnClickListener(this);
        mAvatarImg = ButterKnife.findById(navHeaderView, R.id.img_drawer_avatar);
        mAvatarImg.setImageResource(R.drawable.ic_avatar_24dp);
        mTvUserName = ButterKnife.findById(navHeaderView, R.id.tv_drawer_username);
        mTvCollectionCount = ButterKnife.findById(navHeaderView, R.id.tv_drawer_collection);
        mTvBoardCount = ButterKnife.findById(navHeaderView, R.id.tv_drawer_board);
        mTvFollowingCount = ButterKnife.findById(navHeaderView, R.id.tv_drawer_following);
        mTvFollowerCount = ButterKnife.findById(navHeaderView, R.id.tv_drawer_follower);
    }

    @Override
    protected void initData() {
        String noticeMsg = getIntent().getStringExtra(EXTRA_NOTICE);
        if (!TextUtils.isEmpty(noticeMsg)) {
            SnackbarUtil.showLong(HomeActivity.this, noticeMsg);
        }

        if (isLogin()) {
            createHomeFragment(HomeFragment.PIN_TYPE_FOLLOWING);
        } else {
            createHomeFragment(HomeFragment.PIN_TYPE_LATEST);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerUserInfoEvent();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
    }

    private void registerUserInfoEvent() {
        Subscription s = RxBus.getInstance()
                .toObservable(BasicUserInfoBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BasicUserInfoBean>() {
                    @Override
                    public void onNext(BasicUserInfoBean basicUserInfoBean) {
                        updateBasicUserInfo(basicUserInfoBean);
                    }
                });
        addSubscription(s);
    }

    private void updateBasicUserInfo(BasicUserInfoBean basicUserInfoBean) {
        String avatarKey = basicUserInfoBean.getAvatarKey();
        if (!TextUtils.isEmpty(avatarKey)) {
            String avatarUrl = String.format(getString(R.string.url_image_small_format), avatarKey);
            ((PetalApplication) getApplication())
                    .getAppComponent()
                    .imageLoader()
                    .loadImage(this,
                            FrescoImageConfig.builder()
                                    .setUrl(avatarUrl)
                                    .setSimpleDraweeView(mAvatarImg)
                                    .isBorder(true)
                                    .isCircle(true).build());
        }
        mTvUserName.setText(basicUserInfoBean.getUserName());
        mTvCollectionCount.setText(basicUserInfoBean.getPinCount());
        mTvBoardCount.setText(basicUserInfoBean.getBoardCount());
        mTvFollowingCount.setText(basicUserInfoBean.getFollowingCount());
        mTvFollowerCount.setText(basicUserInfoBean.getFollowerCount());

        SPUtils.putByCommit(Constants.PREF_BOARD_COUNT, Integer.parseInt(basicUserInfoBean.getBoardCount()));
    }

    @Override
    protected void onDestroy() {
        unRegisterUserInfoEvent();
        Fresco.getImagePipeline().clearMemoryCaches();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_search:
                SearchActivity.launch(HomeActivity.this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_latest) {
            createHomeFragment(HomeFragment.PIN_TYPE_LATEST);
        } else if (itemId == R.id.nav_following) {
            if (isLogin()) {
                createHomeFragment(HomeFragment.PIN_TYPE_FOLLOWING);
            } else {
                SnackbarUtil.showLong(HomeActivity.this, R.string.msg_login_hint,
                        R.string.msg_go_login, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LoginActivity.launch(HomeActivity.this, false);
                            }
                        });
                closeDrawer();
                return false;
            }
        } else if (itemId == R.id.nav_popular) {
            createHomeFragment(HomeFragment.PIN_TYPE_POPULAR);
        } else if (itemId == R.id.nav_discover) {
            SearchActivity.launch(HomeActivity.this);
        } else {
            if (itemId == R.id.nav_about) {
                // TODO: 17/3/4 About info
            } else if (itemId == R.id.nav_settings) {
                // TODO: 17/3/4 Launch SettingsActivity
            } else if (itemId == R.id.nav_logout) {
                handleLogout();
            }
        }
        closeDrawer();
        return true;
    }

    private void handleLogout() {
        if (!isLogin()) {
            return;
        }
        DialogUtils.show(this, R.string.msg_logout_warning, R.string.msg_cancel, R.string.msg_confirm,
                null,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtils.clear();
                        createHomeFragment(HomeFragment.PIN_TYPE_LATEST);
                        clearUserInfo();
                        SnackbarUtil.showLong(HomeActivity.this, R.string.msg_logout_success);
                    }
                });
    }

    private void clearUserInfo() {
        mAvatarImg.setController(null);
        mAvatarImg.setImageResource(R.drawable.ic_avatar_24dp);
        mTvUserName.setText(R.string.msg_nav_login_hint);
        mTvCollectionCount.setText(R.string.msg_count_default);
        mTvBoardCount.setText(R.string.msg_count_default);
        mTvFollowingCount.setText(R.string.msg_count_default);
        mTvFollowerCount.setText(R.string.msg_count_default);
    }

    /**
     *  根据图集类型显示相应的 Fragment
     * @param pinType
     */
    private void createHomeFragment(int pinType) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance(pinType))
                .commit();
        int toolbarTitleResId;
        int menuCheckedIndex;
        switch (pinType) {
            case HomeFragment.PIN_TYPE_LATEST:
                toolbarTitleResId = R.string.nav_title_latest;
                menuCheckedIndex = 0;
                break;
            case HomeFragment.PIN_TYPE_FOLLOWING:
                toolbarTitleResId = R.string.nav_title_following;
                menuCheckedIndex = 1;
                break;
            case HomeFragment.PIN_TYPE_POPULAR:
                toolbarTitleResId = R.string.nav_title_popular;
                menuCheckedIndex = 2;
                break;
            default:
                toolbarTitleResId = R.string.nav_title_latest;
                menuCheckedIndex = 0;
                break;
        }
        getSupportActionBar().setTitle(toolbarTitleResId);
        mNavigationView.getMenu().getItem(menuCheckedIndex).setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_drawer_avatar:
                closeDrawer();
                if (isLogin()) {
                    String userId = String.valueOf(SPUtils.get(Constants.PREF_USER_ID, 0));
                    String userName = (String) SPUtils.get(Constants.PREF_USER_NAME, "");
                    String avatarKey = (String) SPUtils.get(Constants.PREF_USER_AVATAR_KEY, "");
                    UserActivity.launch(HomeActivity.this, userId, userName, mAvatarImg, avatarKey);
                } else {
                    LoginActivity.launch(this, false);
                }
                break;
        }
    }

    private void closeDrawer() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                mDrawerLayout.closeDrawer(Gravity.START);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    private void unRegisterUserInfoEvent() {
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
