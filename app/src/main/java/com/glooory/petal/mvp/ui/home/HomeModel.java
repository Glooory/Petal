package com.glooory.petal.mvp.ui.home;

import com.glooory.petal.app.Constants;
import com.glooory.petal.mvp.model.BasePEModel;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.PinListBean;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.Reply;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Glooory on 17/2/21.
 */
@ActivityScope
public class HomeModel extends BasePEModel<ServiceManager, CacheManager>
        implements HomeContract.Model {

    @Inject
    public HomeModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<PinsListBean> getLatestAllPins(boolean update) {
        Observable<PinsListBean> observablePinsList = mServiceManager.getHomeService()
                .getLatestAllPins(getAuthorization(), Constants.PER_PAGE_LIMIT);
        return mCacheManager.getHomeCache()
                .getLatestAllPins(observablePinsList, new EvictDynamicKey(update))
                .flatMap(new Func1<Reply<PinsListBean>, Observable<PinsListBean>>() {
                    @Override
                    public Observable<PinsListBean> call(Reply<PinsListBean> pinsListBeanReply) {
                        return Observable.just(pinsListBeanReply.getData());
                    }
                });
    }

    @Override
    public Observable<PinsListBean> getLatestAllPinsNext(int maxPinId, boolean update) {
        Observable<PinsListBean> observablePinsList = mServiceManager.getHomeService()
                .getLatestAllPinsNext(getAuthorization(), maxPinId, Constants.PER_PAGE_LIMIT);
        return mCacheManager.getHomeCache()
                .getLatestAllPinsNext(observablePinsList,
                        new DynamicKey(maxPinId),
                        new EvictDynamicKey(update))
                .flatMap(new Func1<Reply<PinsListBean>, Observable<PinsListBean>>() {
                    @Override
                    public Observable<PinsListBean> call(Reply<PinsListBean> pinsListBeanReply) {
                        return Observable.just(pinsListBeanReply.getData());
                    }
                });
    }

    @Override
    public Observable<PinListBean> getLatestFollowingPins(boolean update) {
        Observable<PinListBean> observableListPins = mServiceManager.getHomeService()
                .getLatestFollowingPins(getAuthorization(), Constants.PER_PAGE_LIMIT);
        return mCacheManager.getHomeCache()
                .getLatestFollowingPins(observableListPins, new EvictDynamicKey(update))
                .flatMap(new Func1<Reply<PinListBean>, Observable<PinListBean>>() {
                    @Override
                    public Observable<PinListBean> call(Reply<PinListBean> listPinsBeanReply) {
                        return Observable.just(listPinsBeanReply.getData());
                    }
                });
    }

    @Override
    public Observable<PinListBean> getLatestFollowingPinsNext(int maxPinId, boolean update) {
        Observable<PinListBean> observableListPins = mServiceManager.getHomeService()
                .getLatestFollowingPinsNext(getAuthorization(), maxPinId, Constants.PER_PAGE_LIMIT);
        return mCacheManager.getHomeCache()
                .getLatestFollowingPinsNext(observableListPins,
                        new DynamicKey(maxPinId),
                        new EvictDynamicKey(update))
                .flatMap(new Func1<Reply<PinListBean>, Observable<PinListBean>>() {
                    @Override
                    public Observable<PinListBean> call(Reply<PinListBean> listPinsBeanReply) {
                        return Observable.just(listPinsBeanReply.getData());
                    }
                });
    }

    @Override
    public Observable<PinListBean> getLatestPopularPins(boolean update) {
        Observable<PinListBean> observableListPins = mServiceManager.getHomeService()
                .getLatestPopularPins(getAuthorization(), Constants.PER_PAGE_LIMIT);
        return mCacheManager.getHomeCache()
                .getLatestPopularPins(observableListPins,
                        new EvictDynamicKey(update))
                .flatMap(new Func1<Reply<PinListBean>, Observable<PinListBean>>() {
                    @Override
                    public Observable<PinListBean> call(Reply<PinListBean> listPinsBeanReply) {
                        return Observable.just(listPinsBeanReply.getData());
                    }
                });
    }

    @Override
    public Observable<PinListBean> getLatestPopularPinsNext(int maxPinId, boolean update) {
        Observable<PinListBean> observableListPins = mServiceManager.getHomeService()
                .getLatestPopularPinsNext(getAuthorization(), maxPinId, Constants.PER_PAGE_LIMIT);
        return mCacheManager.getHomeCache()
                .getLatestPopularPinsNext(observableListPins,
                        new DynamicKey(maxPinId),
                        new EvictDynamicKey(update))
                .flatMap(new Func1<Reply<PinListBean>, Observable<PinListBean>>() {
                    @Override
                    public Observable<PinListBean> call(Reply<PinListBean> listPinsBeanReply) {
                        return Observable.just(listPinsBeanReply.getData());
                    }
                });
    }
}
