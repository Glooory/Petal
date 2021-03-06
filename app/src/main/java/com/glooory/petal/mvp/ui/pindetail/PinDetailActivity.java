package com.glooory.petal.mvp.ui.pindetail;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.DialogUtils;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.app.util.UIUtils;
import com.glooory.petal.app.widget.CustomStaggeredGridLayoutManager;
import com.glooory.petal.di.component.DaggerPinDetailComponent;
import com.glooory.petal.di.module.PinDetailModule;
import com.glooory.petal.mvp.presenter.PinDetailPresenter;
import com.glooory.petal.mvp.ui.home.HomePinAdapter;
import com.jakewharton.rxbinding.view.RxView;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppComponent;
import common.BasePetalActivity;
import rx.Observable;
import rx.functions.Action1;

import static com.glooory.petal.app.Constants.EXTRA_ASPECT_RATIO;
import static com.glooory.petal.app.Constants.EXTRA_PIN_ID;

/**
 * Created by Glooory on 17/3/17.
 */

public class PinDetailActivity extends BasePetalActivity<PinDetailPresenter>
        implements PinDetailContract.View {

    @BindView(R.id.simple_drawee_view_pin)
    SimpleDraweeView mImagePin;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private HomePinAdapter mAdapter;
    private int mPinId;
    private String mUserName;
    private int mUserId;
    private LinearLayout mLlCollect;
    private ShineButton mSbtnCollect;
    private TextView mTvCollectCount;
    private LinearLayout mLlLike;
    private ShineButton mSbtnLike;
    private TextView mTvLikeCount;
    private TextView mTvCollectDes;
    private RelativeLayout mRlUserBar;
    private SimpleDraweeView mImgAvatar;
    private TextView mTvUserName;
    private TextView mTvCollectTime;
    private RelativeLayout mRlBoardBar;
    private SimpleDraweeView mImgBoardFirst;
    private SimpleDraweeView mImgBoardSecond;
    private SimpleDraweeView mImgBoardThird;
    private SimpleDraweeView mImgBoardFourth;
    private TextView mTvBoardName;
    private View mNoMoreDataFooter;
    private RxPermissions mRxPermissions;

    public static void launch(Activity activity, int pinId, float aspectRatio,
            SimpleDraweeView image, String imageKey) {

        Intent intent = new Intent(activity, PinDetailActivity.class);
        intent.putExtra(Constants.EXTRA_PIN_ID, pinId);
        intent.putExtra(EXTRA_ASPECT_RATIO, aspectRatio);
        intent.putExtra(Constants.EXTRA_IMAGE_KEY, imageKey);

        // 太长的图使用 Shared Element transition 动画时会 crash
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && aspectRatio > 0.2) {
            image.setTransitionName(String.valueOf(pinId));
            activity.startActivity(
                    intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            activity, image, String.valueOf(pinId))
                            .toBundle()
            );
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerPinDetailComponent.builder()
                .appComponent(appComponent)
                .pinDetailModule(new PinDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pin_detail;
    }

    @Override
    protected void initView() {
        mPinId = getIntent().getIntExtra(EXTRA_PIN_ID, 0);
        float aspectRatio = getIntent().getFloatExtra(EXTRA_ASPECT_RATIO, 0F);
        mImagePin.setAspectRatio(aspectRatio);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 这里在代码中动态设置 Shared Element Transition Name,
            // 如果在 xml 文件中写死 Shared Element Transtion Name 的话，
            // 打开多个本 Activity 时，返回时动画会是之前 Activity 对应的图片，而不是本 Activity 对应的图片
            mImagePin.setTransitionName(String.valueOf(mPinId));
        }
        String imageKey = getIntent().getStringExtra(Constants.EXTRA_IMAGE_KEY);
        mPresenter.loadImageWithLowUrl(imageKey, mImagePin);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mRecyclerView.setLayoutManager(
                new CustomStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        initRecyelerHeader();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_pin_img:
                        mPresenter.launchPinDetailActivity(PinDetailActivity.this, view, position);
                        break;
                    case R.id.ll_pin_via_info:
                        mPresenter.launchUserActivity(PinDetailActivity.this, view, position);
                        break;
                }
            }
        });
        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int off = -verticalOffset;
                if (off >= appBarLayout.getTotalScrollRange()) {
                    mToolbar.setAlpha(0);
                } else {
                    mToolbar.setAlpha(1);
                }
            }
        });
        mNoMoreDataFooter = LayoutInflater.from(this).inflate(R.layout.view_footer_no_more_data, null);
    }

    private void initRecyelerHeader() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_pin_detail, mRecyclerView, false);
        mLlCollect = ButterKnife.findById(headerView, R.id.ll_pin_detail_collect);
        mSbtnCollect = ButterKnife.findById(headerView, R.id.shine_button_pin_detail_collect);
        mTvCollectCount = ButterKnife.findById(headerView, R.id.tv_pin_detail_collect_count);
        mLlLike = ButterKnife.findById(headerView, R.id.ll_pin_detail_like);
        mSbtnLike = ButterKnife.findById(headerView, R.id.shine_button_pin_detail_like);
        mTvLikeCount = ButterKnife.findById(headerView, R.id.tv_pin_detail_like_count);
        mTvCollectDes = ButterKnife.findById(headerView, R.id.tv_pin_detail_des);
        mRlUserBar = ButterKnife.findById(headerView, R.id.rl_pin_detail_user_bar);
        mImgAvatar = ButterKnife.findById(headerView, R.id.simple_drawee_view_pin_detail_avatar);
        mTvUserName = ButterKnife.findById(headerView, R.id.tv_pin_detail_user_name);
        mTvCollectTime = ButterKnife.findById(headerView, R.id.tv_pin_detail_collect_time);
        mRlBoardBar = ButterKnife.findById(headerView, R.id.rl_pin_detail_board_bar);
        mImgBoardFirst = ButterKnife.findById(headerView, R.id.simple_drawee_view_pin_detail_board_pin_first);
        mImgBoardSecond = ButterKnife.findById(headerView, R.id.simple_drawee_view_pin_detail_board_pin_second);
        mImgBoardThird = ButterKnife.findById(headerView, R.id.simple_drawee_view_pin_detail_board_pin_third);
        mImgBoardFourth = ButterKnife.findById(headerView, R.id.simple_drawee_view_pin_detail_board_pin_fourth);
        mTvBoardName = ButterKnife.findById(headerView, R.id.tv_pin_detail_board_name);
        mAdapter.addHeaderView(headerView);

        RxView.clicks(mLlCollect)
                .throttleFirst(Constants.THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.actionCollect();
                    }
                });

        RxView.clicks(mLlLike)
                .throttleFirst(Constants.THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.actionLike();
                    }
                });
        RxView.clicks(mRlUserBar)
                .throttleFirst(Constants.THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.launchUserActivity(PinDetailActivity.this, mImgAvatar);
                    }
                });
        RxView.clicks(mRlBoardBar)
                .throttleFirst(Constants.THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.launchBoardActivity(PinDetailActivity.this);
                    }
                });
    }

    @Override
    protected void initData() {
        mPresenter.getPinDetailInfo(mPinId);
        mPresenter.requestForIsCollected();
        mPresenter.requestRecommendedPins();
        mRxPermissions = new RxPermissions(PinDetailActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pin_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_download_pin) {
            actionDownloadPin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void actionDownloadPin() {
        Observable.just(1)
                .compose(mRxPermissions.ensureEach(Manifest.permission.READ_EXTERNAL_STORAGE))
                .subscribe(new Action1<Permission>() {
                               @Override
                               public void call(Permission permission) {
                                   if (permission.granted) {
                                       mPresenter.downloadPin(PinDetailActivity.this);
                                   } else if (permission.shouldShowRequestPermissionRationale) {
                                       // Denied permission without asking never again
                                       showMessage(getString(R.string.msg_permission_storage_denied));
                                   } else {
                                       // Denied permission with asking again
                                       // Need to go to the settings
                                       SnackbarUtil.showLong(PinDetailActivity.this,
                                               R.string.msg_permission_storage_not_enabled,
                                               R.string.msg_go_to_settings,
                                               new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       UIUtils.startInstalledAppDetailsActivity(
                                                               PinDetailActivity.this);
                                                   }
                                               });
                                   }
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRxPermissions = null;
        mNoMoreDataFooter = null;
        mAdapter = null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        SnackbarUtil.showLong(PinDetailActivity.this, message);
    }

    @Override
    public void killMyself() {
        finishSelf();
    }

    @Override
    public void showCreateBoardPrompt() {
        SnackbarUtil.showLong(PinDetailActivity.this, R.string.msg_none_board,
                R.string.msg_create_board_tip,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.launchUserMyselfActivity(PinDetailActivity.this);
                    }
                });
    }

    @Override
    public void setAdapter(HomePinAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void showLoadingMore() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.requestMoreRecommededPins();
            }
        });
    }

    @Override
    public void showCollectCount(String collectCount) {
        mTvCollectCount.setText(collectCount);
    }

    @Override
    public void showLikeCount(String likeCount) {
        mTvLikeCount.setText(likeCount);
    }

    @Override
    public void showCollectDes(String des) {
        mTvCollectDes.setText(des);
    }

    @Override
    public void hideCollectDes() {
        mTvCollectDes.setVisibility(View.GONE);
    }

    @Override
    public void showUserName(String userName) {
        mTvUserName.setText(userName);
    }

    @Override
    public void showCollectTime(String collectTime) {
        mTvCollectTime.setText(collectTime);
    }

    @Override
    public void showBoardName(String boardName) {
        mTvBoardName.setText(boardName);
    }

    @Override
    public void showAvatarImage(String avatarKey) {
        mPresenter.loadSmallCircleImage(avatarKey, mImgAvatar);
    }

    @Override
    public void showBoardImgFirst(String imageUrlKey) {
        mImgBoardFirst.setController(null);
        mImgBoardSecond.setController(null);
        mImgBoardThird.setController(null);
        mImgBoardFourth.setController(null);
        mPresenter.loadSmallRadiusImage(imageUrlKey, mImgBoardFirst, 4);
    }

    @Override
    public void showBoardImgSecond(String imageUrlKey) {
        mPresenter.loadSmallRadiusImage(imageUrlKey, mImgBoardSecond, 4);
    }

    @Override
    public void showBoardImgThird(String imageUrlKey) {
        mPresenter.loadSmallRadiusImage(imageUrlKey, mImgBoardThird, 4);
    }

    @Override
    public void showBoardImgFourth(String imageUrlKey) {
        mPresenter.loadSmallRadiusImage(imageUrlKey, mImgBoardFourth, 4);
    }

    @Override
    public void showCollectSbtnChecked(boolean checked, boolean isPerformClick) {
        if (isPerformClick) {
            mSbtnCollect.performClick();
        }
        mSbtnCollect.setChecked(checked);
    }

    @Override
    public void showLikeSbtnChecked(boolean checked, boolean isPerformClick) {
        if (isPerformClick) {
            mSbtnLike.performClick();
        }
        mSbtnLike.setChecked(checked);
    }

    @Override
    public void hideLikeSbtn() {
        mSbtnLike.setVisibility(View.GONE);
    }

    @Override
    public void showNoMoreDataFooter(boolean show) {
        if (show) {
            if (mNoMoreDataFooter.getParent() != null) {
                ((ViewGroup) mNoMoreDataFooter.getParent()).removeView(mNoMoreDataFooter);
            }
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.loadMoreEnd();
                    mAdapter.addFooterView(mNoMoreDataFooter);
                }
            });
        }
    }

    @Override
    public void showCollectDialog(CollectDialogFragment collectDialogFragment) {
        collectDialogFragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void showEditDialog(EditPinDialogFragment editPinDialogFragment) {
        editPinDialogFragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void showDeleteConfirmDialog() {
        DialogUtils.show(PinDetailActivity.this, R.string.msg_delete_waring, R.string.msg_cancel,
                R.string.delete, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deletePin();
                    }
                });
    }
}
