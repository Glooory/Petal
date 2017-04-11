package com.glooory.petal.mvp.presenter;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.rx.RxBus;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.mvp.model.entity.BasicUserInfoBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.ui.home.HomeContract;
import com.glooory.petal.mvp.ui.home.HomeFragment;
import com.glooory.petal.mvp.ui.home.HomePinAdapter;
import com.glooory.petal.mvp.ui.pindetail.PinDetailActivity;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.RxUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import common.BasePetalPresenter;
import rx.Observable;
import rx.functions.Action0;

/**
 * Created by Glooory on 17/2/18.
 */
@FragmentScope
public class HomePresenter extends BasePetalPresenter<HomeContract.View, HomeContract.Model> {

    private int mLastMaxId;
    HomePinAdapter mAdapter;

    @Inject
    HomePresenter(HomeContract.View view, HomeContract.Model model, HomePinAdapter homePinAdapter) {
        super(view, model);
        mAdapter = homePinAdapter;
        initAdapter();
        mRootView.setAdapter(mAdapter);
    }

    private void initAdapter() {
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
     * 第一次请求数据
     * @param pinTypeIndex
     */
    public void requestPinsFirstTime(int pinTypeIndex) {
        getPinListObservable(pinTypeIndex)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();
                    }
                })
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();
                    }
                })
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinList) {
                        mAdapter.setNewData(pinList);
                        saveLastPinMaxId(pinList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Logger.d("requestPinsFirstTime() -> onError()");
                    }
                });
    }

    /**
     * 加载更多
     */
    public void requestMorePins(int pinTypeIndex) {
        getPinListNextObservable(pinTypeIndex)
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinList) {
                        mAdapter.addData(pinList);
                        saveLastPinMaxId(pinList);
                        mAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    /**
     * 保存本次请求到的最后一张图片的 pinId，后续加载更多数据时会用到
     * @param pinsList
     */
    private void saveLastPinMaxId(List<PinBean> pinsList) {
        if (pinsList != null && pinsList.size() > 0) {
            mLastMaxId = pinsList.get(pinsList.size() - 1).getPinId();
        }
    }

    private Observable<List<PinBean>> getPinListObservable(int pinTypeIndex) {
        switch (pinTypeIndex) {
            case HomeFragment.PIN_TYPE_LATEST:
                return mModel.getLatestAllPins();
            case HomeFragment.PIN_TYPE_FOLLOWING:
                return mModel.getLatestFollowingPins();
            case HomeFragment.PIN_TYPE_POPULAR:
                return mModel.getLatestPopularPins();
            default:
                return mModel.getLatestAllPins();
        }
    }

    private Observable<List<PinBean>> getPinListNextObservable(int pinTypeIndex) {
        switch (pinTypeIndex) {
            case HomeFragment.PIN_TYPE_LATEST:
                return mModel.getLatestAllPinsNext(mLastMaxId);
            case HomeFragment.PIN_TYPE_FOLLOWING:
                return mModel.getLatestFollowingPinsNext(mLastMaxId);
            case HomeFragment.PIN_TYPE_POPULAR:
                return mModel.getLatestPopularPinsNext(mLastMaxId);
            default:
                return mModel.getLatestAllPinsNext(mLastMaxId);
        }
    }

    /**
     * 获取侧滑菜单栏用户的相关信息
     */
    public void getBasicUserInfo() {
        if (!mModel.isLogin()) {
            return;
        }
        mModel.getMyselfBasicInfo()
                .compose(RxUtils.<BasicUserInfoBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<BasicUserInfoBean>() {
                    @Override
                    public void onNext(BasicUserInfoBean basicUserInfoBean) {
                        RxBus.getInstance().post(basicUserInfoBean);
                    }
                });
    }

    public void launchPinDetailActivity(Activity activity, View view, int position) {
        PinBean pinBean = mAdapter.getItem(position);
        float aspectRatio = pinBean.getFile().getWidth() /
                (float) pinBean.getFile().getHeight();
        if (aspectRatio < 0.3) {
            aspectRatio = 0.3F;
        }
        PinDetailActivity.launch(activity,
                pinBean.getPinId(),
                aspectRatio,
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin),
                pinBean.getFile().getKey());
    }

    public void launchUserActivity(Activity activity, View view, int position) {
        PinBean pinBean = mAdapter.getItem(position);
        String userId = String.valueOf(pinBean.getUserId());
        String userName = pinBean.getUser().getUsername();
        UserActivity.launch(activity, userId, userName,
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin_avatar),
                pinBean.getUser().getAvatar().getKey());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }
}
