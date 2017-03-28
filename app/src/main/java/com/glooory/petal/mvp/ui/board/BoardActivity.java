package com.glooory.petal.mvp.ui.board;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.DialogUtils;
import com.glooory.petal.di.component.DaggerBoardComponent;
import com.glooory.petal.di.module.BoardModule;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.presenter.BoardPresenter;
import com.glooory.petal.mvp.ui.board.pin.BoardPinFragment;
import com.glooory.petal.mvp.ui.user.board.EditBoardDiglogFragment;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindView;
import common.AppComponent;
import common.BasePetalActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/3/27.
 */

public class BoardActivity extends BasePetalActivity<BoardPresenter>
        implements BoardContract.View, SwipeRefreshLayout.OnRefreshListener,
        AppBarLayout.OnOffsetChangedListener{

    private static final String ARGS_BOARD_BEAN = "board_bean";

    @BindView(R.id.simple_drawee_view_board_thumbnail_first)
    SimpleDraweeView mImgBoardThumbnailFirst;
    @BindView(R.id.simple_drawee_view_board_thumbnail_second)
    SimpleDraweeView mImgBoardThumbnailSecond;
    @BindView(R.id.simple_drawee_view_board_thumbnail_third)
    SimpleDraweeView mImgBoardThumbnailThird;
    @BindView(R.id.simple_drawee_view_board_thumbnail_fourth)
    SimpleDraweeView mImgBoardThumbnailFourth;
    @BindView(R.id.rl_board_info_header)
    RelativeLayout mRlBoardInfoHeader;
    @BindView(R.id.text_view_board_username)
    TextView mTvBoardUsername;
    @BindView(R.id.text_view_board_des)
    TextView mTvBoardDes;
    @BindView(R.id.text_view_board_follow_edit)
    TextView mTvBoardFollowEdit;
    @BindView(R.id.progressbar_board_following)
    ProgressBar mProgressbarFollowing;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppbarLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindColor(R.color.colorPrimary)
    int mColorTabIndicator;
    @BindArray(R.array.board_subitem_titles)
    String[] mTabTitles;

    private String mBoardId;
    private String mBoardName;
    private String mUserName;
    private BoardBean mBoardBean;
    private BoardSectionAdapter mViewPagerAdapter;
    private BoardPinFragment mPinFragment;


    public static void launch(Activity activity, String userName, BoardBean boardBean, SimpleDraweeView image) {
        Intent intent = new Intent(activity, BoardActivity.class);
        intent.putExtra(Constants.EXTRA_USER_NAME, userName);
        intent.putExtra(ARGS_BOARD_BEAN, boardBean);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            image.setTransitionName(activity.getString(R.string.image_transition_name));
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, image, activity.getString(R.string.image_transition_name)
            ).toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBoardComponent.builder()
                .appComponent(appComponent)
                .boardModule(new BoardModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_board;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAppbarLayout.addOnOffsetChangedListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(BoardActivity.this, R.color.red_google_icon),
                ContextCompat.getColor(BoardActivity.this, R.color.blue_google_icon),
                ContextCompat.getColor(BoardActivity.this, R.color.yellow_google_icon),
                ContextCompat.getColor(BoardActivity.this, R.color.green_google_icon)
        );
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mTablayout.setSelectedTabIndicatorColor(mColorTabIndicator);
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        RxView.clicks(mTvBoardFollowEdit)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.onBoardEditBtnClick();
                    }
                });
    }

    @Override
    protected void initData() {
        mUserName = getIntent().getStringExtra(Constants.EXTRA_USER_NAME);
        mBoardBean = getIntent().getParcelableExtra(ARGS_BOARD_BEAN);
        if (mBoardBean != null) {
            mBoardId = String.valueOf(mBoardBean.getBoardId());
            mPresenter.updateBoardInfo(mBoardBean, mUserName);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getBoardInfo(mBoardId);
        switch (mViewPager.getCurrentItem()) {
            case 0:
                if (mPinFragment != null) {
                    mPinFragment.onRefresh();
                }
                break;
        }
    }

    @Override
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

    @Override
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
    public void showBoardOperateBtn(String operate, Drawable actionDrawable) {
        mTvBoardFollowEdit.setCompoundDrawablesWithIntrinsicBounds(actionDrawable, null, null, null);
        mTvBoardFollowEdit.setText(operate);
    }

    @Override
    public void showBoardName(CharSequence boardName) {
        getSupportActionBar().setTitle(boardName);
    }

    @Override
    public void showBoardUserName(CharSequence userName) {
        mTvBoardUsername.setText(userName);
    }

    @Override
    public void showBoardDescription(String des) {
        mTvBoardDes.setVisibility(View.VISIBLE);
        mTvBoardDes.setText(des);
    }

    @Override
    public void hideBoardDescription() {
        mTvBoardDes.setVisibility(View.GONE);
    }

    @Override
    public void showBoardThumbnailFirst(String imgUrlKey) {
        mPresenter.loadSmallBoardCover(imgUrlKey, mImgBoardThumbnailFirst,
                new BaseBitmapDataSubscriber() {
                    @Override
                    protected void onNewResultImpl(Bitmap bitmap) {
                        mPresenter.processBlurBackgroundImg(bitmap);
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

                    }
                });
    }

    @Override
    public void showBoardThumbnailSecond(String imgUrlKey) {
        mPresenter.loadSmallRadiusImage(imgUrlKey, mImgBoardThumbnailSecond, 4);
    }

    @Override
    public void showBoardThumbnailThird(String imgUrlKey) {
        mPresenter.loadSmallRadiusImage(imgUrlKey, mImgBoardThumbnailThird, 4);
    }

    @Override
    public void showBoardThumbnailFourth(String imgUrlKey) {
        mPresenter.loadSmallRadiusImage(imgUrlKey, mImgBoardThumbnailFourth, 4);
    }

    @Override
    public void showAppBarBackground(final Drawable drawable) {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mAppbarLayout.setBackground(drawable);
                    }
                });
    }

    @Override
    public void showTabTitles(String[] titles) {
        mTabTitles = titles;
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showViewPager() {
        if (mViewPagerAdapter == null) {
            mViewPagerAdapter = new BoardSectionAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mViewPagerAdapter);
            mViewPager.setCurrentItem(0, true);
            mTablayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    public void showEditBoardDialog(EditBoardDiglogFragment editBoardDiglogFragment) {
        editBoardDiglogFragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void showDeleteBoardSuccess() {
        finishSelf();
    }

    @Override
    public void showDeleteBoardConfirmDialog(final String boardId) {
        DialogUtils.show(BoardActivity.this, R.string.msg_delete_waring, R.string.msg_cancel,
                R.string.msg_confirm, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteBoard(boardId);
                    }
                });
    }

    @Override
    public void showProcessingBar() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mTvBoardFollowEdit.setVisibility(View.INVISIBLE);
                        mProgressbarFollowing.setVisibility(View.VISIBLE);

                    }
                });
    }

    @Override
    public void hideProcessingBar() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mTvBoardFollowEdit.setVisibility(View.VISIBLE);
                        mProgressbarFollowing.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float c = -verticalOffset;
        float t = appBarLayout.getTotalScrollRange();
        float a = (float) (1.0 - c / (t / 2));
        mRlBoardInfoHeader.setAlpha(a);
        if (c >= appBarLayout.getTotalScrollRange()) {
            mToolbar.setAlpha(0.0f);
        } else {
            mToolbar.setAlpha(1.0f);
        }
    }

    class BoardSectionAdapter extends FragmentStatePagerAdapter {

        public BoardSectionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return BoardPinFragment
                            .newInstance(mBoardId, mPresenter.getPinCount(), mPresenter.isMine());
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            switch (position) {
                case 0:
                    mPinFragment = (BoardPinFragment) createdFragment;
                    break;
            }
            return createdFragment;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }

}
