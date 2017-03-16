package com.glooory.petal.mvp.presenter;

import android.app.Application;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.glooory.petal.app.rx.RxBus;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.mvp.model.entity.BasicUserInfoBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.ui.home.HomeContract;
import com.glooory.petal.mvp.ui.home.HomeFragment;
import com.glooory.petal.mvp.ui.home.HomePinsAdapter;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Glooory on 17/2/18.
 */
@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.View, HomeContract.Model> {

    private RxErrorHandler mRxErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private int mLastMaxId;
    HomePinsAdapter mAdapter;

    @Inject
    HomePresenter(HomeContract.View view, HomeContract.Model model, RxErrorHandler handler,
            AppManager appManager, Application application, HomePinsAdapter homePinsAdapter) {
        super(view, model);
        mRxErrorHandler = handler;
        mAppManager = appManager;
        mApplication = application;
        mAdapter = homePinsAdapter;
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
                .subscribe(new ErrorHandleSubscriber<List<PinBean>>(mRxErrorHandler) {
                    @Override
                    public void onNext(List<PinBean> pinsList) {
                        mAdapter.setNewData(pinsList);
                        saveLastPinMaxId(pinsList);
                    }
                });
    }

    /**
     * 加载更多
     */
    public void requestMorePins(int pinTypeIndex) {
        getPinListNextObservable(pinTypeIndex)
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<PinBean>>(mRxErrorHandler) {
                    @Override
                    public void onNext(List<PinBean> pinsList) {
                        mAdapter.addData(pinsList);
                        saveLastPinMaxId(pinsList);
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
        mModel.getMyselfBasicInfo()
                .compose(RxUtils.<BasicUserInfoBean>bindToLifecycle(mRootView))
                .subscribe(new Subscriber<BasicUserInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BasicUserInfoBean basicUserInfoBean) {
                        RxBus.getInstance().post(basicUserInfoBean);
                    }
                });
    }
}
