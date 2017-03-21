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
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.app.util.TimeUtils;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.collect.CollectResultBean;
import com.glooory.petal.mvp.model.entity.pindetail.CollectionInfoBean;
import com.glooory.petal.mvp.model.entity.pindetail.LikeResultBean;
import com.glooory.petal.mvp.model.entity.pindetail.PinDetailBean;
import com.glooory.petal.mvp.ui.home.HomePinsAdapter;
import com.glooory.petal.mvp.ui.pindetail.CollectDialogFragment;
import com.glooory.petal.mvp.ui.pindetail.EditPinDialogFragment;
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
    private int mPinId;
    private String mCollectDes;
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
    private String mBoardId;

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

    /**
     * 获取采集的详细信息
     * @param pinId
     */
    public void getPinDetailInfo(int pinId) {
        mPinId = pinId;
        mModel.getPinDetailInfo(pinId)
                .compose(RxUtils.<PinDetailBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<PinDetailBean>() {
                    @Override
                    public void onNext(PinDetailBean pinDetailBean) {
                        setupPinDetailInfo(pinDetailBean);
                    }
                });
    }

    /**
     * 拿到采集的详细信息后更新 UI
     * @param pinDetailBean
     */
    private void setupPinDetailInfo(PinDetailBean pinDetailBean) {
        mCollectCount = pinDetailBean.getPin().getRepinCount();
        mLikeCount = pinDetailBean.getPin().getLikeCount();
        mBoardId = String.valueOf(pinDetailBean.getPin().getBoardId());
        mCollectDes = pinDetailBean.getPin().getRawText();
        mRootView.showCollectCount(
                String.format(mCollectCountFormat, mCollectCount));
        mIsMine = pinDetailBean.getPin().getUser().getUsername()
                .equals(SPUtils.get(Constants.PREF_USER_NAME, ""));
        if (mIsMine) {
            mRootView.hideLikeSbtn();
            mRootView.showLikeCount(mStrEdit);
        } else {
            mIsLiked = pinDetailBean.getPin().isLiked();
            mRootView.showLikeSbtnChecked(mIsLiked, false);
            mRootView.showLikeCount(
                    String.format(mLikeCountFormat, mLikeCount));
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

    /**
     * 联网判断是否已经采集过该图片
     */
    public void requestForIsCollected() {
        if (!mModel.isLogin()) {
            mRootView.showCollectSbtnChecked(false, false);
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
                            mIsCollected = true;
                            mRootView.showCollectSbtnChecked(mIsCollected, false);
                            mCollectBelong = collectionInfoBean.getExistPin().getBoard().getTitle();
                        }
                    }
                });
    }

    /**
     * 请求相关推荐
     */
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

    /**
     * 采集操作
     */
    public void actionCollect() {
        if (!mModel.isLogin()) {
            showLoginHintMsg();
            return;
        }
        if (mIsCollected) {
            String pinExistMsg = String.format(
                    PEApplication.getContext().getString(R.string.msg_collection_exist_format),
                    mCollectBelong);
            SnackbarUtil.showLong(pinExistMsg,
                    PEApplication.getContext().getString(R.string.msg_collect_still),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showCollectDialog();
                        }
                    });
        } else {
            showCollectDialog();
        }
    }

    private void showCollectDialog() {
        CollectDialogFragment collectDialogFragment = CollectDialogFragment
                .create(mPinId, mCollectDes, mIsCollected, mCollectBelong);
        collectDialogFragment.setCollectActionListener(new CollectDialogFragment.OnCollectActionListener() {
            @Override
            public void onCollectButtonClick(String collectDes, String boardId) {
                collectPin(boardId, collectDes);
            }
        });
        mRootView.showCollectDialog(collectDialogFragment);
    }

    private void showEditDialog() {
        EditPinDialogFragment editPinDialogFragment = EditPinDialogFragment
                .create(String.valueOf(mPinId), mBoardId, mCollectDes);
        editPinDialogFragment.setPinEditedListener(new EditPinDialogFragment.OnPinEditedListener() {
            @Override
            public void onDeleteButtonClicked() {
                mRootView.showDeleteConfirmDialog();
            }

            @Override
            public void onCommitButtonClicked(String boardId, String des) {
                actionEditPin(boardId, des);
            }
        });
        mRootView.showEditDialog(editPinDialogFragment);
    }

    /**
     * 编辑完成后提交修改
     * @param boardId
     * @param des
     */
    private void actionEditPin(String boardId, String des) {
        mModel.editPin(boardId, des)
                .compose(RxUtils.<PinBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<PinBean>() {
                    @Override
                    public void onNext(PinBean pinBean) {
                        getPinDetailInfo(mPinId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void collectPin(String boardId, String des) {
        mModel.collectPin(boardId, des)
                .compose(RxUtils.<CollectResultBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<CollectResultBean>() {
                    @Override
                    public void onNext(CollectResultBean collectResultBean) {
                        if (collectResultBean.getPin() != null) {
                            mCollectBelong = collectResultBean.getPin().getBoard().getTitle();
                            mIsCollected = true;
                            mCollectCount++;
                            mRootView.showCollectCount(String.format(mCollectCountFormat, mCollectCount));
                            mRootView.showCollectSbtnChecked(mIsCollected, true);
                            SnackbarUtil.showLong(String.format(
                                    PEApplication.getContext().getString(R.string.msg_collect_success_format),
                                    collectResultBean.getPin().getBoard().getTitle()
                            ));
                        }
                    }
                });
    }

    public void deletePin() {
        mModel.deletePin()
                .compose(RxUtils.<Void>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<Void>() {
                    @Override
                    public void onNext(Void aVoid) {
                        mRootView.killMyself();
                        SnackbarUtil.showShort(R.string.msg_delete_success);
                    }
                });
    }

    public void actionLike() {
        if (!mModel.isLogin()) {
            showLoginHintMsg();
            return;
        }
        if (mIsMine) {
            showEditDialog();
        } else {
            actionlikeOrUnlikePin();
        }
    }

    private void actionlikeOrUnlikePin() {
        Logger.d(mLikeCount);
        mModel.likePin(!mIsLiked)
                .compose(RxUtils.<LikeResultBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<LikeResultBean>() {
                    @Override
                    public void onNext(LikeResultBean likeResultBean) {
                        mLikeCount = mIsLiked ? --mLikeCount : ++mLikeCount;
                        mIsLiked = !mIsLiked;
                        mRootView.showLikeSbtnChecked(mIsLiked, true);
                        mRootView.showLikeCount(String.format(mLikeCountFormat, mLikeCount));
                        Logger.d(mLikeCount);
                    }
                });
    }
}
