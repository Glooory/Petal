package com.glooory.petal.mvp.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.mvp.ui.login.LoginActivity;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppComponent;
import common.PEActivity;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/2/18.
 */

public class HomeActivity extends PEActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBar;

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

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
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
                        // TODO: 17/3/2 Launch CollectActivity
                    }
                });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = ButterKnife.findById(this, R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        initNavigationView(navigationView);
        Menu menu = navigationView.getMenu();
        menu.getItem(isLogin() ? 1 : 0).setChecked(true);
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
        if (isLogin()) {
            createHomeFragment(HomeFragment.PIN_TYPE_FOLLOWING);
        } else {
            createHomeFragment(HomeFragment.PIN_TYPE_LATEST);
        }
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
                // TODO: 17/3/2 Launch SearchActivity
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_latest) {
            createHomeFragment(HomeFragment.PIN_TYPE_LATEST);
            getSupportActionBar().setTitle(R.string.nav_title_latest);
        } else if (itemId == R.id.nav_following) {
            if (isLogin()) {
                createHomeFragment(HomeFragment.PIN_TYPE_FOLLOWING);
                getSupportActionBar().setTitle(R.string.nav_title_following);
            } else {
                SnackbarUtil.showLong(HomeActivity.this, R.string.msg_login_hint,
                        R.string.msg_go_login, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LoginActivity.launch(HomeActivity.this, false);
                            }
                        });
                mDrawerLayout.closeDrawer(Gravity.START);
                return false;
            }
        } else if (itemId == R.id.nav_popular) {
            createHomeFragment(HomeFragment.PIN_TYPE_POPULAR);
            getSupportActionBar().setTitle(R.string.nav_title_popular);
        } else if (itemId == R.id.nav_about) {
            // TODO: 17/3/4 About info
        } else if (itemId == R.id.nav_settings) {
            // TODO: 17/3/4 Launch SettingsActivity
        } else if (itemId == R.id.nav_logout) {
            // TODO: 17/3/4 logout
        }
        mDrawerLayout.closeDrawer(Gravity.START);
        return true;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_drawer_avatar:
                LoginActivity.launch(this, false);
                break;
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
}
