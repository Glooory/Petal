package com.glooory.petal.mvp.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.util.DrawableUtils;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.app.util.TimeUtils;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.pindetail.CollectionInfoBean;
import com.glooory.petal.mvp.model.entity.pindetail.PinDetailBean;
import com.glooory.petal.mvp.ui.home.HomePinsAdapter;
import com.glooory.petal.mvp.ui.pindetail.PinDetailActivity;
import com.glooory.petal.mvp.ui.pindetail.PinDetailContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.RxUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import common.PEApplication;
import common.PEPresenter;
import rx.Subscriber;

/**
 * Created by Glooory on 17/3/17.
 */
@ActivityScope
public class PinDetailPresenter extends PEPresenter<PinDetailContract.View, PinDetailContract.Model> {

    private HomePinsAdapter mAdapter;
    private boolean mIsMine;
    private String mCollectCountFormat;
    private String mLikeCountFormat;
    private String mStrEdit;
    private int mCollectCount;
    private int mLikeCount;
    private boolean mIsCollected;
    private boolean mIsLiked;
    private String mCollectBelong;
    private int mPage = 1;

    @Inject
    public PinDetailPresenter(PinDetailContract.View rootView, PinDetailContract.Model model,
            HomePinsAdapter adapter) {
        super(rootView, model);
        mAdapter = adapter;
        initAdaper();
        mRootView.setAdapter(mAdapter);
        mCollectCountFormat = PEApplication.getContext().getString(R.string.collection_count_format);
        mLikeCountFormat = PEApplication.getContext().getString(R.string.like_count_format);
        mStrEdit = PEApplication.getContext().getString(R.string.edit);
    }

    private void initAdaper() {
        mAdapter.setLoadMoreView(new WindmillLoadMoreFooter());
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRootView.showLoadingMore();
            }
        });
    }

    public void getPinDetailInfo(int pinId) {
        mModel.getPinDetailInfo(pinId)
                .compose(RxUtils.<PinDetailBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<PinDetailBean>() {
                    @Override
                    public void onNext(PinDetailBean pinDetailBean) {
                        setupPinDetailInfo(pinDetailBean);
                    }
                });
    }

    private void setupPinDetailInfo(PinDetailBean pinDetailBean) {
        mRootView.showCollectCount(
                String.format(mCollectCountFormat, pinDetailBean.getPin().getRepinCount()));
        mIsMine = pinDetailBean.getPin().getUser().getUsername()
                .equals(SPUtils.get(Constants.PREF_USER_NAME, ""));
        if (mIsMine) {
            mRootView.hideLikeSbtn();
            mRootView.showLikeCount(mStrEdit);
        } else {
            mIsLiked = pinDetailBean.getPin().isLiked();
            mRootView.showLikeSbtnChecked(mIsLiked);
            mRootView.showLikeCount(
                    String.format(mLikeCountFormat, pinDetailBean.getPin().getLikeCount())
            );
        }
        if (TextUtils.isEmpty(pinDetailBean.getPin().getRawText())) {
            mRootView.hideCollectDes();
        } else {
            mRootView.showCollectDes(pinDetailBean.getPin().getRawText());
        }
        mRootView.showUserName(pinDetailBean.getPin().getUser().getUsername());
        mRootView.showCollectTime(TimeUtils.getTimeDifference(
                pinDetailBean.getPin().getCreatedAt(), System.currentTimeMillis()));
        mRootView.showBoardName(pinDetailBean.getPin().getBoard().getTitle());
        mRootView.showPinImage(pinDetailBean.getPin().getFile().getKey());
        mRootView.showAvatarImage(pinDetailBean.getPin().getUser().getAvatar().getKey());
        List<PinBean> pinList = pinDetailBean.getPin().getBoard().getPins();
        if (pinList != null) {
            if (pinList.size() > 0) {
                mRootView.showBoardImgFirst(pinList.get(0).getFile().getKey());
            }
            if (pinList.size() > 1) {
                mRootView.showBoardImgSecond(pinList.get(1).getFile().getKey());
            }
            if (pinList.size() > 2) {
                mRootView.showBoardImgThird(pinList.get(2).getFile().getKey());
            }
            if (pinList.size() > 3) {
                mRootView.showBoardImgFourth(pinList.get(3).getFile().getKey());
            }
        }
    }

    public void isCollected() {
        if (!mModel.isLogin()) {
            updateCollectSbtnStatus(false);
            return;
        }
        mModel.isCollected()
                .compose(RxUtils.<CollectionInfoBean>bindToLifecycle(mRootView))
                .subscribe(new Subscriber<CollectionInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CollectionInfoBean collectionInfoBean) {
                        if (collectionInfoBean.getExistPin() != null) {
                            updateCollectSbtnStatus(true);
                            mCollectBelong = collectionInfoBean.getExistPin().getBoard().getTitle();
                        }
                    }
                });
    }

    private void updateCollectSbtnStatus(boolean checked) {
        mIsCollected = checked;
        mRootView.showCollectSbtnChecked(mIsCollected);
    }

    private void updateLikeSbtnStatus(boolean liked) {
        mIsLiked = liked;
        mRootView.showLikeSbtnChecked(mIsLiked);
    }

    public void requestRecommendedPins() {
        mPage = 1;
        mModel.getRecommendedPins(mPage)
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinBeen) {
                        mPage++;
                        mAdapter.setNewData(pinBeen);
                        mAdapter.loadMoreComplete();
                        mRootView.showNoMoreDataFooter(pinBeen.size() < 10);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAdapter.loadMoreFail();
                    }
                });
    }

    public void requestMoreRecommededPins() {
        mModel.getRecommendedPins(mPage)
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinBeen) {
                        mPage++;
                        mAdapter.addData(pinBeen);
                        mAdapter.loadMoreComplete();
                        mRootView.showNoMoreDataFooter(pinBeen.size() < 10);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAdapter.loadMoreFail();
                    }
                });
    }

    public void launchPinDetailActivity(Activity activity, View view, int position) {
        PinBean pinBean = mAdapter.getItem(position);
        float aspectRatio = pinBean.getFile().getWidth() /
                (float) pinBean.getFile().getHeight();
        Logger.d(aspectRatio);
        if (aspectRatio < 0.3) {
            aspectRatio = 0.3F;
        }
        PinDetailActivity.launch(activity,
                pinBean.getPinId(),
                aspectRatio,
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin),
                DrawableUtils.getBasicColorStr(mAdapter.getItem(position)));
    }
}
