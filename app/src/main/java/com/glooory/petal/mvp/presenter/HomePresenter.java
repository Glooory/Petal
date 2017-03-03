package com.glooory.petal.mvp.presenter;

import android.app.Application;

import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.PinListBean;
import com.glooory.petal.mvp.ui.home.HomeContract;
import com.glooory.petal.mvp.ui.home.HomeFragment;
import com.glooory.petal.mvp.ui.home.HomePinsAdapter;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
        mRootView.setAdapter(mAdapter);
        Logger.d(mAdapter == null);
    }

    /**
     * 第一次请求数据
     * @param pinTypeIndex
     */
    public void requestPinsFirstTime(int pinTypeIndex) {
        getPinListObservable(pinTypeIndex)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(2, 2))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();
                    }
                })
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        return pinListBean.getPins();
                    }
                })
                .filter(new Func1<List<PinBean>, Boolean>() {
                    @Override
                    public Boolean call(List<PinBean> pinBeen) {
                        return pinBeen.size() > 0;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
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
     * @param pinTypeIndex
     */
    public void requestMorePins(int pinTypeIndex) {
        getPinListNextObservable(pinTypeIndex)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(2, 2))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        return pinListBean.getPins();
                    }
                })
                .filter(new Func1<List<PinBean>, Boolean>() {
                    @Override
                    public Boolean call(List<PinBean> pinBeen) {
                        return pinBeen.size() > 0;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<PinBean>>(mRxErrorHandler) {
                    @Override
                    public void onNext(List<PinBean> pinsList) {
                        mAdapter.addData(pinsList);
                        saveLastPinMaxId(pinsList);
                    }
                });
    }

    /**
     * 保存本次请求到的最后一张图片的 pinId，后湖加载更多数据时会用到
     * @param pinsList
     */
    private void saveLastPinMaxId(List<PinBean> pinsList) {
        if (pinsList == null && pinsList.size() > 0) {
            mLastMaxId = pinsList.get(pinsList.size() - 1).getPinId();
        }
    }

    private Observable<PinListBean> getPinListObservable(int pinTypeIndex) {
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

    private Observable<PinListBean> getPinListNextObservable(int pinTypeIndex) {
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
}
